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
		var month= fecha.get(Calendar.MONTH)+1
		var day= fecha.get(Calendar.DAY_OF_MONTH)
		var hour= fecha.get(Calendar.HOUR_OF_DAY)
		var minute= fecha.get(Calendar.MINUTE)
		var second= fecha.get(Calendar.SECOND)
		
		var strmon = ""
		var strday = ""
		var strhr = ""
		var strmin = ""
		var strsec = ""
		
		if(month<10){ strmon = "0"+month }else {strmon = month+""}
		if(day<10) {strday = "0"+day} else {strday = day+""}
		if(hour<10) {strhr = "0"+hour} else {strhr = hour+""}	
		if(minute<10) {strmin = "0"+minute} else {strmin = minute+""}
		if(second<10) {strsec = "0"+second} else {strsec = second+""}		
		
		return fecha.get(Calendar.YEAR)+"_"+ strmon+ "_"+ strday +"_"+ strhr+ strmin+ strsec+ "_create_" +  e.name.toSnakeCase + "_table"  +".php"	
		
	}

}
