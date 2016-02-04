package co.edu.javeriana.anchurus.generator.laravel

import co.edu.javeriana.isml.generator.common.GeneratorSuite
import co.edu.javeriana.isml.generator.common.OutputConfiguration
import co.edu.javeriana.anchurus.generator.laravel.generators.PagesGenerator

class AnchurusLaravelGenerator extends GeneratorSuite{
	
    @OutputConfiguration
	public static final String PAGES = "pages";

	@OutputConfiguration
	public static final String CONTROLLERS = "controllers"

	@OutputConfiguration
	public static final String SERVICE_INTERFACE = "service.interface"
	
	@OutputConfiguration
	public static final String SERVICE_IMPL = "service.impl"
		
	override getGenerators() {
		#{
			new PagesGenerator
		}
	}
	
}