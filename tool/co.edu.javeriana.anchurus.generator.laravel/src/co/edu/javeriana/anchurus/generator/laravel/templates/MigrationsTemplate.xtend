 package co.edu.javeriana.anchurus.generator.laravel.templates

import co.edu.javeriana.isml.generator.common.SimpleTemplate
import co.edu.javeriana.isml.isml.Entity
import co.edu.javeriana.isml.scoping.IsmlModelNavigation
import co.edu.javeriana.isml.validation.TypeChecker
import com.google.inject.Inject
import co.edu.javeriana.isml.isml.Attribute
import org.eclipse.xtext.naming.IQualifiedNameProvider
import co.edu.javeriana.anchurus.generator.laravel.utils.Utils

class MigrationsTemplate extends SimpleTemplate<Entity> {
	@Inject extension TypeChecker
	@Inject extension IsmlModelNavigation	
	@Inject extension IQualifiedNameProvider
	@Inject extension Utils
	int i

	override preprocess(Entity e) {
		i= 1;	
	}
	
	

	override def CharSequence template(Entity e) '''
	<?php
	use Illuminate\Database\Schema\Blueprint;
	use Illuminate\Database\Migrations\Migration;
	
	class Create«e.name.toFirstUpper»Table extends Migration{
		
		public function up(){
			Schema::create('«toSnakeCase(e.name)»', function(Blueprint $table){
				$table->increments('id');
				«FOR attr: e.body»
					«generarTipo(attr)»
				«ENDFOR»
				$table->timestamps();
			});
			
		}
		
		public function down(){
			DB::statement('SET FOREIGN_KEY_CHECKS = 0');
			Schema::drop('«toSnakeCase(e.name)»');
			DB::statement('SET FOREIGN_KEY_CHECKS = 1');
		}
		
	}	
	'''
	
	def CharSequence generarTipo(Attribute attribute) {
		switch(attribute.type.typeSpecification.typeSpecificationString){
			case "String": '''
				$table->string(«toSnakeCase(attribute.name)»)->default('');
			'''
			case "Integer": '''
				$table->integer(«toSnakeCase(attribute.name)»)->default(0);
			'''
			case "Boolean": '''
				$table->boolean(«toSnakeCase(attribute.name)»)->default(false);
			'''
			case "Float": '''
				$table->float(«toSnakeCase(attribute.name)»)->default(0.0);
			'''
			case "Long": '''
				$table->bigInteger(«toSnakeCase(attribute.name)»)->default(0);
			'''
			case "ByteArray": '''
				$table->binary(«toSnakeCase(attribute.name)»);
			'''
		}
	}
	
	
	

}
