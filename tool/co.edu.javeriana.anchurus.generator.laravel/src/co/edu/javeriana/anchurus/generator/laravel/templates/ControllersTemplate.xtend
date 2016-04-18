 package co.edu.javeriana.anchurus.generator.laravel.templates

import co.edu.javeriana.anchurus.generator.laravel.utils.UtilsAnchurus
import co.edu.javeriana.isml.generator.common.SimpleTemplate
import co.edu.javeriana.isml.isml.Action
import co.edu.javeriana.isml.isml.Attribute
import co.edu.javeriana.isml.isml.Controller
import co.edu.javeriana.isml.isml.Expression
import co.edu.javeriana.isml.isml.MethodStatement
import co.edu.javeriana.isml.isml.Show
import co.edu.javeriana.isml.isml.ViewInstance
import co.edu.javeriana.isml.scoping.IsmlModelNavigation
import co.edu.javeriana.isml.validation.TypeChecker
import com.google.inject.Inject
import java.util.ArrayList
import java.util.List
import co.edu.javeriana.isml.isml.Entity
import co.edu.javeriana.isml.isml.TypedElement
import java.util.Set
import co.edu.javeriana.isml.isml.NamedElement
import java.util.LinkedHashSet
import co.edu.javeriana.isml.isml.Service
import co.edu.javeriana.isml.isml.ParameterizedType
import org.eclipse.xtext.naming.IQualifiedNameProvider
import co.edu.javeriana.isml.isml.Parameter

class ControllersTemplate extends SimpleTemplate<Controller> {
	@Inject extension TypeChecker
	@Inject extension IsmlModelNavigation
	@Inject extension UtilsAnchurus	
	@Inject extension IQualifiedNameProvider
	List<TypedElement> imports= new ArrayList<TypedElement>
	Set<Entity> entitySubGroup= new LinkedHashSet<Entity>
	Set<Service> controllerSG = new LinkedHashSet<Service>
	
	override preprocess(Controller c) {
		val descendants = c.eAllContents.filter(TypedElement).toList
		imports.addAll(descendants) 
		for(desc: descendants){
			val NamedElement e= desc.type?.typeSpecification
			if(e instanceof Entity){
				entitySubGroup.add(e)
			}
			else if (e instanceof Service){
				controllerSG.add(e)
			}
		}
	}
	
	

	override def CharSequence template(Controller c) '''
		<?php
		namespace App\Http\Controllers;

		use Input;
		use Redirect;
		use Illuminate\Http\Request;

		use App\Http\Requests;
		use App\Http\Controllers\Controller;
		«FOR elm: entitySubGroup»
		use App\co\edu\javeriana\«elm.name»;
		«ENDFOR»
		«FOR elm: controllerSG»
		use App\Http\Controllers\«elm.name»;
		«ENDFOR»
		class «c.name»Controller extends Controller{
			«FOR attr: c.body.filter(Attribute)»
				«generateAttributes(attr)»
			«ENDFOR»
			public function __construct(){
				«FOR attr: c.body.filter(Attribute)»
					«IF attr.type.typeSpecification.name.equalsIgnoreCase("persistence")»
						$this->«attr.name» = new «attr.type.typeSpecification.name»('App\«(attr.type.cast(ParameterizedType).typeParameters.get(0).typeSpecification.fullyQualifiedName.toString("\\"))»');
					«ELSE»
						$this->«attr.name» = new «attr.type.typeSpecification.name»;
					«ENDIF»
				«ENDFOR»
			}
			«FOR func: c.actions»
				«generateFunction(func)»
			«ENDFOR»
		}
	'''
	
	def generateAttributes(Attribute attribute)'''
		private $«attribute.name»;
	''' 
	
	def CharSequence generateFunction(Action action)'''
		public function «action.name»(Request $req «IF action.parameters.size >0», «getParameters(action)»«ENDIF»){
			«IF action.parameters.size !=0»
			«FOR param: action.parameters»
			$«param.name» = NULL;
			«ENDFOR»
			«FOR param: action.parameters»
			if(is_numeric($«param.name»_id)){
				$«param.name» = «param.type.typeSpecification.name»::find($«param.name»_id);
				$«param.name»->update($req->all());
			}
			«ENDFOR»
			«ENDIF»
			«FOR sentence: action.body»
				«generateBody(sentence)»
			«ENDFOR»
			
		}
	'''
	
	def CharSequence generateBody(MethodStatement statement) {
		switch(statement){
			Show: getPage(statement.expression)
			default: ''''''
		}
	}
	
	def CharSequence getPage(Expression expression) {
		switch(expression){
			ViewInstance:'''return view('co.edu.javeriana.«toSnakeCase(expression.type.typeSpecification.name)»', «generateArray(expression)»); '''
			default:''''''
		}
		
	}
	
	
	def CharSequence getParameters(Action action) '''«IF action.parameters.size !=0»«FOR param: action.parameters SEPARATOR ','»«generateParams(param)»«ENDFOR»«ELSE»«ENDIF»'''
	
	def CharSequence generateParams(Parameter p){
		if(p.type.typeSpecification instanceof Entity){
			return '''$«p.name»_id'''
		}
		else{
			return '''$«p.name»'''
		}
	}
	

}
