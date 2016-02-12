package co.edu.javeriana.anchurus.generator.laravel.generators

import co.edu.javeriana.anchurus.generator.laravel.AnchurusLaravelGenerator
import co.edu.javeriana.anchurus.generator.laravel.templates.ModelsTemplate
import co.edu.javeriana.isml.generator.common.SimpleGenerator
import co.edu.javeriana.isml.isml.Entity
import com.google.inject.Inject
import org.eclipse.xtext.naming.IQualifiedNameProvider

class ModelsGenerator extends SimpleGenerator<Entity> {

	@Inject extension IQualifiedNameProvider

	override getOutputConfigurationName() {
		AnchurusLaravelGenerator.PAGES
	}

	

	override getTemplate() {
		return new ModelsTemplate

	}
	
	override protected getFileName(Entity e) {
		return e.name + ".php"
	}

}
