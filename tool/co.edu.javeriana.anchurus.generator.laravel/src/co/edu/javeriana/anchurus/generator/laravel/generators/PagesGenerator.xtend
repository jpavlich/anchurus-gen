package co.edu.javeriana.anchurus.generator.laravel.generators

import co.edu.javeriana.anchurus.generator.laravel.AnchurusLaravelGenerator
import co.edu.javeriana.anchurus.generator.laravel.templates.PagesTemplate
import co.edu.javeriana.isml.generator.common.SimpleGenerator
import co.edu.javeriana.isml.isml.Page
import com.google.inject.Inject
import org.eclipse.xtext.naming.IQualifiedNameProvider
import co.edu.javeriana.anchurus.generator.laravel.utils.UtilsAnchurus

class PagesGenerator extends SimpleGenerator<Page> {

	@Inject extension IQualifiedNameProvider
	@Inject extension UtilsAnchurus

	override getOutputConfigurationName() {
		AnchurusLaravelGenerator.PAGES
	}

	override protected getFileName(Page e) {
		return e.eContainer?.fullyQualifiedName.toString("/").toLowerCase + "/" + toSnakeCase(e.name) + ".blade.php"
	}

	override getTemplate() {
		return new PagesTemplate

	}

}
