package co.edu.javeriana.anchurus.generator.laravel.generators

import co.edu.javeriana.anchurus.generator.laravel.AnchurusLaravelGenerator
import co.edu.javeriana.anchurus.generator.laravel.templates.MigrationsTemplate
import co.edu.javeriana.isml.generator.common.SimpleGenerator
import co.edu.javeriana.isml.isml.Entity
import com.google.inject.Inject
import org.eclipse.xtext.naming.IQualifiedNameProvider
import java.util.Calendar
import co.edu.javeriana.anchurus.generator.laravel.utils.UtilsAnchurus

class MigrationsGenerator extends SimpleGenerator<Entity> {

	@Inject extension IQualifiedNameProvider
	@Inject extension UtilsAnchurus

	override getOutputConfigurationName() {
		AnchurusLaravelGenerator.MIGRATIONS
	}

	

	override getTemplate() {
		return new MigrationsTemplate

	}
	
	override protected getFileName(Entity e) {
		return fecha.get(Calendar.YEAR)+"_"+ (fecha.get(Calendar.MONTH)+1)+ "_"+ fecha.get(Calendar.DAY_OF_MONTH)+"_"+
				fecha.get(Calendar.HOUR_OF_DAY)+ fecha.get(Calendar.MINUTE)+ fecha.get(Calendar.SECOND) + "_create_" +  e.name.toSnakeCase + "_table"  +".php"
	}

}
