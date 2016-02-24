package co.edu.javeriana.anchurus.generator.laravel

import co.edu.javeriana.anchurus.generator.laravel.generators.ControllersGenerator
import co.edu.javeriana.anchurus.generator.laravel.generators.MigrationsGenerator
import co.edu.javeriana.anchurus.generator.laravel.generators.ModelsGenerator
import co.edu.javeriana.anchurus.generator.laravel.generators.PagesGenerator
import co.edu.javeriana.isml.generator.common.GeneratorSuite
import co.edu.javeriana.isml.generator.common.OutputConfiguration

class AnchurusLaravelGenerator extends GeneratorSuite{
	
    @OutputConfiguration
	public static final String PAGES = "pages";

	@OutputConfiguration
	public static final String CONTROLLERS = "controllers"

	@OutputConfiguration
	public static final String SERVICE_INTERFACE = "service.interface"
	
	@OutputConfiguration
	public static final String SERVICE_IMPL = "service.impl"
	
	@OutputConfiguration
	public static final String MIGRATIONS = "migrations"
	
	@OutputConfiguration
	public static final String MODELS = "models"
		
	override getGenerators() {
		#{
			new PagesGenerator,
			new MigrationsGenerator,
			new ModelsGenerator,
			new ControllersGenerator
		}
	}
	
}