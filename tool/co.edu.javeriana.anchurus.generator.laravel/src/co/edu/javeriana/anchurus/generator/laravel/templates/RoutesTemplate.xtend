package co.edu.javeriana.anchurus.generator.laravel.templates

import co.edu.javeriana.anchurus.generator.laravel.utils.UtilsAnchurus
import co.edu.javeriana.isml.generator.common.SimpleTemplate
import co.edu.javeriana.isml.isml.Action
import co.edu.javeriana.isml.isml.Controller
import co.edu.javeriana.isml.isml.Entity
import co.edu.javeriana.isml.isml.NamedElement
import co.edu.javeriana.isml.isml.TypedElement
import co.edu.javeriana.isml.scoping.IsmlModelNavigation
import com.google.inject.Inject
import java.util.ArrayList
import java.util.LinkedHashSet
import java.util.List
import java.util.Set
import org.eclipse.xtext.naming.IQualifiedNameProvider

class RoutesTemplate extends SimpleTemplate<List<Controller>> {
	
	@Inject extension UtilsAnchurus
	@Inject extension IsmlModelNavigation
	@Inject extension IQualifiedNameProvider
	
	List<TypedElement> imports= new ArrayList<TypedElement>();
	Set<Entity> entitySubGroup= new LinkedHashSet<Entity>();

	/**
	 * This method fills the List&ltTypedElement> imports (where are the typed elements to<br>
	 * go in the routes) and the Set&ltEntity> entitySubGroup (where are the referenced entities<br>
	 * in the routes).
	 * @param lc Controller list to make the Routes archive
	 * */
	override preprocess(List<Controller> lc){
		for(ctrl: lc){
			var descendants= ctrl.eAllContents.filter(TypedElement).toList
			imports.addAll(descendants)
			for(desc: descendants){
				val NamedElement e= desc.type?.typeSpecification
				if(e instanceof Entity) entitySubGroup.add(e)
			}
		}
	}
	
	/**
	 * This method makes the PHP Routes archive for a given list of ISML controllers.
	 * @param e the controller list
	 * @return the PHP Routes archive
	 * */
	override protected  template(List<Controller> e) '''
	<?php
	Route::get('/', function () {
			    return view('welcome');
			});
	«FOR ctrl: e»
		«IF !ctrl.name.equals("DefaultPageDispatcher")»
		«FOR act: ctrl.body.filter(Action)»
		Route::match(['GET', 'POST'], '«namedUrlForController(ctrl)»/«toKebabCase(act.name)»«generateParameters(act)»', '«ctrl.name»Controller@«act.name»');
		«ENDFOR»
		«ENDIF»
	«ENDFOR»
	'''
	/**
	 * This method formats a parameter list from an Action for put them in the action's relative URL.
	 * @param action a Controller Action
	 * @return formatted string for the parameters
	 * */
	def CharSequence generateParameters(Action action) {
		var CharSequence cs= ''''''
		if(action.parameters.size>0){
			cs='''«FOR param: action.parameters»/{«param.name»}«ENDFOR»'''
		}
		else
			return cs
	}
	
}