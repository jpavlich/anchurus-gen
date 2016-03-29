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

class RoutesTemplate extends SimpleTemplate<List<Controller>> {
	
	@Inject extension UtilsAnchurus
	@Inject extension IsmlModelNavigation
	
	List<TypedElement> imports= new ArrayList<TypedElement>();
	Set<Entity> entitySubGroup= new LinkedHashSet<Entity>();
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
	
	override protected  template(List<Controller> e) '''
	<?php
	Route::get('/', function () {
			    return view('welcome');
			});
	«FOR ctrl: e»
		«FOR act: ctrl.body.filter(Action)»
		Route::match(['GET', 'POST'], '«namedUrlForController(ctrl)»/«toKebabCase(act.name)»«generateParameters(act)»', '«ctrl.name»@«act.name»');
		«ENDFOR»
	«ENDFOR»
	'''
	
	def CharSequence generateParameters(Action action) {
		var CharSequence cs= ''''''
		if(action.parameters.size>0){
			cs='''«FOR param: action.parameters»/{«param.name»}«ENDFOR»'''
		}
		else
			return cs
	}
	
}