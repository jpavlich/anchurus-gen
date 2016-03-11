package co.edu.javeriana.anchurus.generator.laravel.templates

import co.edu.javeriana.isml.generator.common.SimpleTemplate
import co.edu.javeriana.isml.isml.Controller
import java.util.List
import co.edu.javeriana.isml.isml.Action
import co.edu.javeriana.anchurus.generator.laravel.utils.UtilsAnchurus
import com.google.inject.Inject

class RoutesTemplate extends SimpleTemplate<List<Controller>> {
	
	@Inject extension UtilsAnchurus
	
	override preprocess(List<Controller> lc){
		println("funciona esto?")
	}
	
	override protected  template(List<Controller> e) '''
	<?php
	
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