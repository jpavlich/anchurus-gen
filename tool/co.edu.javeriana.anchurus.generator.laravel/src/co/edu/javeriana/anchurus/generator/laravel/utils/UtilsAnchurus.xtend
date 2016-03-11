package co.edu.javeriana.anchurus.generator.laravel.utils

import co.edu.javeriana.isml.isml.BoolValue
import co.edu.javeriana.isml.isml.Expression
import co.edu.javeriana.isml.isml.FloatValue
import co.edu.javeriana.isml.isml.IntValue
import co.edu.javeriana.isml.isml.MethodCall
import co.edu.javeriana.isml.isml.NullValue
import co.edu.javeriana.isml.isml.StringValue
import co.edu.javeriana.isml.isml.VariableReference
import co.edu.javeriana.isml.isml.ViewInstance
import co.edu.javeriana.isml.scoping.IsmlModelNavigation
import com.google.common.base.CaseFormat
import com.google.inject.Inject
import java.util.Calendar
import co.edu.javeriana.isml.isml.Controller
import co.edu.javeriana.isml.isml.Attribute
import co.edu.javeriana.isml.isml.Variable
import co.edu.javeriana.isml.isml.Statement
import co.edu.javeriana.isml.isml.Parameter
import co.edu.javeriana.isml.isml.StructInstance

class UtilsAnchurus {
	@Inject extension IsmlModelNavigation
	
	def toSnakeCase(String string) {
		CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, string)
	}
	def toKebabCase(String string){
		CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, string)
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
			StringValue:'''«e.literal.toString»'''
			MethodCall: '''call'''
//			VariableReference: '''«IF hasQueue(e)»$«e.referencedElement.name»->«e.tail.referencedElement.name»«ELSE»«e.referencedElement.name»«ENDIF»'''
			VariableReference: '''«generateReferencedElement(e)»'''
			ViewInstance: '''return view('co.edu.javeriana.«toSnakeCase(e.type.typeSpecification.name)»', «generateArray(e)») '''
			StructInstance: '''new «e.type.typeSpecification.name»'''
			default: e.toString
		}
	}
	
	def CharSequence generateReferencedElement(VariableReference reference) {
		switch reference.referencedElement{
			Attribute:'''$this->«IF hasTail(reference)»«generateTailedElement(reference)»«ELSE»«reference.referencedElement.name»«ENDIF»'''
			Variable: '''$«IF hasTail(reference)»«generateTailedElement(reference)»«ELSE»«reference.referencedElement.name»«ENDIF»'''
			Parameter: '''$«IF hasTail(reference)»«generateTailedElement(reference)»«ELSE»«reference.referencedElement.name»«ENDIF»'''
			default: reference.toString
		}
	}
	
	def CharSequence generateTailedElement(VariableReference vr) {
		var str= vr.referencedElement.name
		var accumulate = str
		var current = vr.tail
		while(current!=null){
			accumulate += "->" + current.referencedElement.name
			current = current.tail
		}
		return accumulate
	}
	
	def CharSequence generateArray(ViewInstance instance){
		var acumula = ""
		var cadena= ""
		for(i : 0..<instance.parameters.size){
			acumula += "'"+ instance.type.typeSpecification.parameters.get(i).name +"'" + "=>" + valueTemplate(instance.parameters.get(i)) + ", " 
		}
		acumula = acumula.substring(0, acumula.length()-2)
		cadena= "[" +  acumula + "]"
		return cadena
	} 
	
	def boolean hasTail(VariableReference vr){
		if(vr.tail!=null)
			return true
		else
			return false
	}
	def String namedUrlForController(Controller c){
		var guia = "-controller"
		var nombreCompl= c.name
		var cadena= CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, nombreCompl)
		cadena= cadena.replaceFirst(guia, "")
		return cadena
	} 
}