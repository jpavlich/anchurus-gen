package co.edu.javeriana.anchurus.generator.laravel.test

import co.edu.javeriana.isml.IsmlInjectorProvider
import co.edu.javeriana.isml.isml.InformationSystem
import co.edu.javeriana.isml.isml.Package
import co.edu.javeriana.isml.scoping.IsmlModelNavigation
import co.edu.javeriana.isml.tests.CommonTests
import com.google.inject.Inject
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.junit4.util.ParseHelper
import org.eclipse.xtext.junit4.validation.ValidationTestHelper
import org.junit.Test
import org.junit.runner.RunWith
import co.edu.javeriana.isml.isml.Entity
import co.edu.javeriana.isml.generator.test.TestGeneratorHelper
import co.edu.javeriana.anchurus.generator.laravel.templates.MigrationsTemplate

@InjectWith(IsmlInjectorProvider)
@RunWith(XtextRunner)
class MigrationGeneratorTest extends CommonTests {
	@Inject extension ParseHelper<InformationSystem>
	@Inject extension ValidationTestHelper
	@Inject extension TestGeneratorHelper
	@Inject extension IsmlModelNavigation
	@Inject MigrationsTemplate template
	
	@Test
	def entityGeneration() {
		val informationSystem = '''
			package test;
			entity Person extends Animal, Sentient {
				String name must be Size(1,10);
				Integer age;
			}
			
			entity Animal {
				
			}
			
			entity Sentient {
				
			}
		'''.parse(rs)
		informationSystem.assertNoErrors
		val pkg = informationSystem.body.head as Package
		val entity = pkg.body.head as Entity
		
		assertGenerates(template, entity, 
		'''
			<?php 
			use Illuminate\Database\Schema\Blueprint;
		'''
		)
		
		
	}
}