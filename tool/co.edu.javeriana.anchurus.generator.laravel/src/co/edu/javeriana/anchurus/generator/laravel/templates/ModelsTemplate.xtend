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
	
	

	/**
	 * This method makes the PHP Model archive for a given ISML entity.
	 * @param e the Entity
	 * @return the PHP Model archive
	 * */
	override def CharSequence template(Entity e) '''
		<?php
		namespace App\co\edu\javeriana;
		use Illuminate\Database\Eloquent\Model;	
		
		class «e.name» extends Model{
			protected $guarded = [];
			
		}
		
		
	'''
	

}
