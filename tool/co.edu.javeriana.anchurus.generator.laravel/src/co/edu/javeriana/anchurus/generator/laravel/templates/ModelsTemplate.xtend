 package co.edu.javeriana.anchurus.generator.laravel.templates

import co.edu.javeriana.isml.generator.common.SimpleTemplate
import co.edu.javeriana.isml.isml.Entity
import co.edu.javeriana.isml.scoping.IsmlModelNavigation
import co.edu.javeriana.isml.validation.TypeChecker
import com.google.inject.Inject

class ModelsTemplate extends SimpleTemplate<Entity> {
	@Inject extension TypeChecker
	@Inject extension IsmlModelNavigation	
	int i

	override preprocess(Entity e) {
		i= 1;	
	}
	
	

	override def CharSequence template(Entity e) '''
		
	'''
	

}
