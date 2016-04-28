 package co.edu.javeriana.anchurus.generator.laravel.templates

import co.edu.javeriana.anchurus.generator.laravel.utils.UtilsAnchurus
import co.edu.javeriana.isml.generator.common.SimpleTemplate
import co.edu.javeriana.isml.isml.Action
import co.edu.javeriana.isml.isml.ActionCall
import co.edu.javeriana.isml.isml.Assignment
import co.edu.javeriana.isml.isml.Attribute
import co.edu.javeriana.isml.isml.Controller
import co.edu.javeriana.isml.isml.Entity
import co.edu.javeriana.isml.isml.Expression
import co.edu.javeriana.isml.isml.For
import co.edu.javeriana.isml.isml.If
import co.edu.javeriana.isml.isml.MethodCall
import co.edu.javeriana.isml.isml.NamedElement
import co.edu.javeriana.isml.isml.Parameter
import co.edu.javeriana.isml.isml.ParameterizedType
import co.edu.javeriana.isml.isml.ResourceReference
import co.edu.javeriana.isml.isml.Return
import co.edu.javeriana.isml.isml.Service
import co.edu.javeriana.isml.isml.Show
import co.edu.javeriana.isml.isml.Type
import co.edu.javeriana.isml.isml.TypedElement
import co.edu.javeriana.isml.isml.Variable
import co.edu.javeriana.isml.isml.VariableReference
import co.edu.javeriana.isml.isml.ViewInstance
import co.edu.javeriana.isml.isml.While
import co.edu.javeriana.isml.scoping.IsmlModelNavigation
import co.edu.javeriana.isml.validation.TypeChecker
import com.google.inject.Inject
import java.util.ArrayList
import java.util.LinkedHashSet
import java.util.List
import java.util.Set
import org.eclipse.xtext.naming.IQualifiedNameProvider
import co.edu.javeriana.isml.isml.MethodStatement
import org.eclipse.emf.common.util.EList

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
	
	
	/**
	 * This method generates the whole PHP Controller archive from a given ISML controller.
	 * @param c the Controller
	 * @return the Controller archive
	 * */
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
	/**
	 * This method generates an Attribute declaration in PHP.
	 * @param attribute attribute in ISML
	 * @return attribute in PHP format
	 * */
	def generateAttributes(Attribute attribute)'''
		private $«attribute.name»;
	''' 
	/**
	 * This method generates the PHP code for a function, from the given ISML action.
	 * @param action the ISML action
	 * @return the PHP function
	 * */
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
			«generateBody(action.body)»
		}
	'''
	
	/**
	 * This method generates the body of a PHP function, given a ISML MethodStatement list.
	 * @param lms the ISML action list
	 * @return PHP function body
	 * */
	def CharSequence generateBody(List<MethodStatement> lms)'''
		«FOR sentence: lms»«IF sentence instanceof If || sentence instanceof For»«generateMethodStatement(sentence)»«ELSE»«generateMethodStatement(sentence)»; 
		
		«ENDIF»«ENDFOR»
	'''
	
	/**
	 * This method generates the PHP "if" statement, given a ISML If statement.
	 * @param ifst the ISML If statement
	 * @return PHP if statement
	 * */
	def dispatch CharSequence generateMethodStatement(If ifst)'''
		if(«ifst.condition.valueTemplate»){
			«generateBody(ifst.body)»
		}
		«IF ifst.elseBody?.size>0»
		else{
			«ifst.elseBody.generateBody»
		}
		«ENDIF»
	'''

	/**
	 * This method must generate the PHP Assignment statement, given the ISML assignment statement.
	 * @param assign the ISML assignment
	 * @return the PHP Assignment statement
	 * */
	def dispatch CharSequence generateMethodStatement(Assignment assign){
		// TODO Generate PHP assignment statement
		return ''''''
	}
	
	/**
	 * This method must generate the PHP Return statement, given the ISML return statement.
	 * @param returnst the ISML return
	 * @return the PHP Return statement
	 * */
	def dispatch CharSequence generateMethodStatement(Return returnst){
		return ''''''
	}
	
	/**
	 * This method generates the PHP "for" statement, given a ISML For statement.
	 * @param forst the ISML For statement
	 * @return PHP for statement
	 * */
	def dispatch CharSequence generateMethodStatement(For forst)'''
		foreach(«forst.collection.valueTemplate» as &«forst.variable.generateMethodStatement»){
			«generateBody(forst.body)»
		}
	'''
	
	/**
	 * This method must generate the PHP "while" statement, given a ISML While statement.
	 * @param whilest the ISML While statement
	 * @return PHP while statement
	 * */
	def dispatch CharSequence generateMethodStatement(While whilest){
		return ''''''
	}
	/**
	 * This method generates the PHP action calling, given a ISML ActionCall statement.
	 * @param acst the ISML ActionCall statement
	 * @return PHP action calling
	 * */
	def dispatch CharSequence generateMethodStatement(ActionCall acst)'''
	return $this->«acst.action.name»($req«IF acst.action.parameters.size !=0», «getParameters(acst.action)»«ENDIF»)'''
	
	/**
	 * This method must generate the PHP method calling, given a ISML MethodCall statement.
	 * @param mcst the ISML MethodCall statement
	 * @return PHP for statement
	 * */
	def dispatch CharSequence generateMethodStatement(MethodCall mcst){
		return ''''''
	}
	
	/**
	 * This method must generate the PHP resource referencing, given a ISML<br> 
	 * ResourceReference statement.
	 * @param resref the ISML ResourceReference statement
	 * @return PHP resource referencing
	 * */
	def dispatch CharSequence generateMethodStatement(ResourceReference resref){
		return ''''''
	}
	
	/**
	 * This method must generate the PHP type, given a ISML Type statement.
	 * @param type the ISML Type statement
	 * @return PHP type statement
	 * */
	def dispatch CharSequence generateMethodStatement(Type type){
		return ''''''
	}
	
	/**
	 * This method generates the PHP variable reference, given a ISML VariableReference<br> 
	 * statement.
	 * @param vr the ISML VariableReference statement
	 * @return PHP variable reference
	 * */
	def dispatch CharSequence generateMethodStatement(VariableReference vr)'''
		«vr.valueTemplate»'''
		
	/**
	 * This method generates the PHP variable declaration, given a ISML Variable<br> 
	 * statement.
	 * @param variable the ISML Variable statement
	 * @return PHP variable
	 * */
	def dispatch CharSequence generateMethodStatement(Variable variable)'''
		$«variable.name»
	'''
	
	/**
	 * This method generates a return method for show views in PHP, given a ISML Show<br> 
	 * statement.
	 * @param show the ISML Show statement
	 * @return PHP return method for show
	 * */
	def dispatch generateMethodStatement(Show show){
		val expression = show.expression
		switch(expression){
			ViewInstance:'''return view('co.edu.javeriana.«toSnakeCase(expression.type.typeSpecification.name)»', «generateArray(expression)»)'''
			default:''''''
		}
	}
	
	/**
	 * This method generates formatted parameters for an action, given a ISML Action<br> 
	 * statement.
	 * @param action the ISML Action statement
	 * @return formatted parameters, separated by commas
	 * */
	def CharSequence getParameters(Action action) '''«IF action.parameters.size !=0»«FOR param: action.parameters SEPARATOR ','»«generateParams(param)»«ENDFOR»«ELSE»«ENDIF»'''
	
	
	/**
	 * This method generates format for the parameter given, distinguishing between Entity parameters <br>
	 * and other parameter types.
	 *  
	 * @param action the ISML Action statement
	 * @return formatted parameters, separated by commas
	 * */
	def CharSequence generateParams(Parameter p){
		if(p.type.typeSpecification instanceof Entity){
			return '''$«p.name»_id'''
		}
		else{
			return '''$«p.name»'''
		}
	}
	

}
