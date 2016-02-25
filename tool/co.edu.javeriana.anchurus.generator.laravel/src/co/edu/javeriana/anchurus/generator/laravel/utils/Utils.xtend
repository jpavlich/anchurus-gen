package co.edu.javeriana.anchurus.generator.laravel.utils

import co.edu.javeriana.isml.isml.BoolValue
import co.edu.javeriana.isml.isml.Expression
import co.edu.javeriana.isml.isml.FloatValue
import co.edu.javeriana.isml.isml.IntValue
import co.edu.javeriana.isml.isml.NullValue
import co.edu.javeriana.isml.isml.StringValue
import co.edu.javeriana.isml.isml.VariableReference
import co.edu.javeriana.isml.isml.ViewInstance
import co.edu.javeriana.isml.scoping.IsmlModelNavigation
import com.google.common.base.CaseFormat
import com.google.inject.Inject
import java.util.Calendar

class Utils {
	@Inject extension IsmlModelNavigation
	
	def toSnakeCase(String string) {
		CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, string)
	}
	def fecha(){
		return Calendar.getInstance()
	}
	def CharSequence valueTemplate(Expression e){
		switch e{
			BoolValue: e.literal.toString
			FloatValue: e.literal.toString
			IntValue: e.literal.toString
			NullValue: '''NULL'''
			StringValue:''' "«e.literal.toString»" '''
			
			VariableReference: '''«e.tail.referencedElement.name»'''//««««e.referencedElement.name»»»»->'''
			ViewInstance: '''return view('co.edu.javeriana.«toSnakeCase(e.type.typeSpecification.name)»', «generateArray(e)») '''
			default: e.toString
		}
	}
	
	def CharSequence generateArray(ViewInstance instance){
		var cadena1 = "["
		var cadena2 = "]"
		var cadena3 = "=>"
		var acumula = ""
		var cadena= ""
		for(i : 0..<instance.parameters.size){
			acumula += instance.type.typeSpecification.parameters.get(i).name + cadena3 + valueTemplate(instance.parameters.get(i)) + "," 
		}
		acumula = acumula.substring(0, acumula.length()-1)
		cadena= cadena1 +  acumula + cadena2
		return cadena
	} 
	
}