 package co.edu.javeriana.anchurus.generator.laravel.templates

import co.edu.javeriana.anchurus.generator.laravel.utils.UtilsAnchurus
import co.edu.javeriana.isml.generator.common.SimpleTemplate
import co.edu.javeriana.isml.isml.Action
import co.edu.javeriana.isml.isml.Controller
import co.edu.javeriana.isml.isml.Expression
import co.edu.javeriana.isml.isml.MethodStatement
import co.edu.javeriana.isml.isml.Show
import co.edu.javeriana.isml.isml.ViewInstance
import co.edu.javeriana.isml.scoping.IsmlModelNavigation
import co.edu.javeriana.isml.validation.TypeChecker
import com.google.inject.Inject

class ControllersTemplate extends SimpleTemplate<Controller> {
	@Inject extension TypeChecker
	@Inject extension IsmlModelNavigation
	@Inject extension UtilsAnchurus	
	int i

	override preprocess(Controller c) {
		i= 1;	
	}
	
	

	override def CharSequence template(Controller c) '''
		<?php
		namespace App\Http\Controllers;

		use Input;
		use Redirect;
		use Illuminate\Http\Request;

		use App\Project;
		use App\Http\Requests;
		use App\Http\Controllers\Controller;
		
		class «c.name»Controller extends Controller{
			
			«FOR func: c.actions»
				«generarFuncionalidad(func)»
			«ENDFOR»
		}
	'''
	
	def CharSequence generarFuncionalidad(Action action)'''
		public function «action.name»(«obtenerParametros(action)»){
			«FOR sentencia: action.body»
				«generarCuerpo(sentencia)»
			«ENDFOR»
		}
	'''
	
	def CharSequence generarCuerpo(MethodStatement statement) {
		switch(statement){
			Show: obtenerPagina(statement.expression)
			default: ''''''
		}
	}
	
	def CharSequence obtenerPagina(Expression expression) {
		switch(expression){
			ViewInstance:'''return view('co.edu.javeriana.«toSnakeCase(expression.type.typeSpecification.name)»', «generateArray(expression)»); '''
			default:''''''
		}
		
	}
	
	
	def CharSequence obtenerParametros(Action action) '''«IF action.parameters !=0»«FOR param: action.parameters SEPARATOR ','»$«param.name»«ENDFOR»«ELSE»«ENDIF»'''
	
	
	

}
