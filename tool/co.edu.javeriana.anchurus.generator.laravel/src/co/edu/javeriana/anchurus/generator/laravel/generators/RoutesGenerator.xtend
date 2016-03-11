package co.edu.javeriana.anchurus.generator.laravel.generators

import co.edu.javeriana.isml.isml.Controller
import java.util.List
import co.edu.javeriana.isml.generator.common.SimpleGenerator
import co.edu.javeriana.anchurus.generator.laravel.AnchurusLaravelGenerator
import co.edu.javeriana.anchurus.generator.laravel.templates.RoutesTemplate

class RoutesGenerator extends SimpleGenerator<List<Controller>> {
	
	override protected getFileName(List<Controller> e) {
		return "routes.php"
	}
	
	override protected getOutputConfigurationName() {
		return AnchurusLaravelGenerator.ROUTES
	}
	
	override getTemplate() {
		return new RoutesTemplate
	}
	
}