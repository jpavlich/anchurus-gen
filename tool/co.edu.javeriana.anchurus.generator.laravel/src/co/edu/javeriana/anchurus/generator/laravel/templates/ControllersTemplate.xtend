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

class ControllersTemplate extends SimpleTemplate<Controller> {
	@Inject extension TypeChecker
	@Inject extension IsmlModelNavigation
	@Inject extension UtilsAnchurus	
	List<TypedElement> imports= new ArrayList<TypedElement>();
	Set<Entity> entitySubGroup= new LinkedHashSet<Entity>();
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
		use App\«elm.name»;
		«ENDFOR»
		«FOR elm: controllerSG»
		use App\Http\Controllers\«elm.name»;
		«ENDFOR»
		class «c.name»Controller extends Controller{
			«FOR attr: c.body.filter(Attribute)»
				«generateAttributes(attr)»
			«ENDFOR»
			
			«FOR func: c.actions»
				«generateFunction(func)»
			«ENDFOR»
		}
	'''
	
	def generateAttributes(Attribute attribute)'''
		private $«attribute.name» = new «attribute.type.typeSpecification.name»;
	''' 
	
	def CharSequence generateFunction(Action action)'''
		public function «action.name»(«getParameters(action)»){
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
	
	
	def CharSequence getParameters(Action action) '''«IF action.parameters !=0»«FOR param: action.parameters SEPARATOR ','»$«param.name»«ENDFOR»«ELSE»«ENDIF»'''
	
	
	

}
