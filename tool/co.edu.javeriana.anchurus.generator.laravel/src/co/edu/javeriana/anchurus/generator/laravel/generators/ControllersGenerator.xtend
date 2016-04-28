package co.edu.javeriana.anchurus.generator.laravel.generators

import co.edu.javeriana.anchurus.generator.laravel.AnchurusLaravelGenerator
import co.edu.javeriana.anchurus.generator.laravel.templates.ModelsTemplate
import co.edu.javeriana.isml.generator.common.SimpleGenerator
import co.edu.javeriana.isml.isml.Entity
import com.google.inject.Inject
import org.eclipse.xtext.naming.IQualifiedNameProvider
import co.edu.javeriana.isml.isml.Controller
import co.edu.javeriana.anchurus.generator.laravel.templates.ControllersTemplate


//For further information, see the class SimpleGenerator<T>
class ControllersGenerator extends SimpleGenerator<Controller> {

	@Inject extension IQualifiedNameProvider

	override getOutputConfigurationName() {
		AnchurusLaravelGenerator.CONTROLLERS
	}

	

	override getTemplate() {
		return new ControllersTemplate

	}
	
	override protected getFileName(Controller c) {
		return c.name + "Controller" + ".php"
	}

}
