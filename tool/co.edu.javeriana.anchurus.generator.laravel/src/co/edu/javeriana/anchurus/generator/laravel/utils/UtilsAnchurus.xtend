package co.edu.javeriana.anchurus.generator.laravel.utils

import co.edu.javeriana.isml.isml.Attribute
import co.edu.javeriana.isml.isml.BoolValue
import co.edu.javeriana.isml.isml.Controller
import co.edu.javeriana.isml.isml.Expression
import co.edu.javeriana.isml.isml.FloatValue
import co.edu.javeriana.isml.isml.Function
import co.edu.javeriana.isml.isml.IntValue
import co.edu.javeriana.isml.isml.MethodCall
import co.edu.javeriana.isml.isml.NullValue
import co.edu.javeriana.isml.isml.Parameter
import co.edu.javeriana.isml.isml.ParameterizedReference
import co.edu.javeriana.isml.isml.Reference
import co.edu.javeriana.isml.isml.StringValue
import co.edu.javeriana.isml.isml.StructInstance
import co.edu.javeriana.isml.isml.Variable
import co.edu.javeriana.isml.isml.VariableReference
import co.edu.javeriana.isml.isml.ViewInstance
import co.edu.javeriana.isml.scoping.IsmlModelNavigation
import com.google.common.base.CaseFormat
import com.google.inject.Inject
import java.util.Calendar
import co.edu.javeriana.isml.isml.Method
import co.edu.javeriana.isml.isml.ActionCall
import co.edu.javeriana.isml.isml.Entity

class UtilsAnchurus {
	@Inject extension IsmlModelNavigation
	
	
	/**
	 * This method takes a CamelCased string and converts it to its<br>
	 * SnakeCased form. 
	 * 
	 * @param string the CamelCased string
	 * @return a SnakeCased string
	 * */
	def toSnakeCase(String string) {
		CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, string)
	}
	
	/**
	 * This method takes a CamelCased string and converts it to its<br>
	 * KebabCased form. 
	 * 
	 * @param string the CamelCased string
	 * @return a KebabCased string
	 * */
	def toKebabCase(String string){
		CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, string)
	}
	
	/**
	 * This method returns the current date.
	 * @return the current date
	 * */
	def fecha(){
		return Calendar.getInstance()
	}
	
	/**
	 * This method takes an ISML expression and, according with the <br>
	 * Expression type, converts it to a different formatted string.
	 * @param e expression to convert  
	 * @return formatted string 
	 * */
	def CharSequence valueTemplate(Expression e){
		
		switch e{
			BoolValue: e.literal.toString
			FloatValue: e.literal.toString
			IntValue: e.literal.toString
			NullValue: '''NULL'''
			StringValue:''' '«e.literal.toString»' '''
			MethodCall: '''call'''
			VariableReference: '''«IF hasTail(e)»«generateTailedElement(e)»«ELSE»$«e.referencedElement.name»«ENDIF»'''
			ViewInstance: '''return view('co.edu.javeriana.«toSnakeCase(e.type.typeSpecification.name)»', «generateArray(e)») '''
			StructInstance: '''new «e.type.typeSpecification.name»'''
			default: e.toString
		}
	}
	
	
	/**
	 * This method takes a VariableReference which is tailed and generates all the element, tail included.
	 * 
	 * @param vr VariableReference with tail
	 * @return formatted string
	 * */
	def CharSequence generateTailedElement(VariableReference vr) {
		var accumulate= ''''''
		if(vr.referencedElement instanceof Attribute || vr.referencedElement instanceof Method){
			accumulate+='''$this->'''
			
		}
		else{
			accumulate+='''$'''
		}
		var str= generateReferencedElement(vr).toString
		accumulate += str
		var current = vr.tail
		while(current!=null){
			accumulate += "->" + generateReferencedElement(current)
			current = current.tail
		}
		return accumulate
	}
	
	/** 
	 * This method receives a reference and transforms it to a formatted String. 
	 * 
	 * @param reference Referenced element to convert
	 * @return referenced element converted to a formatted string
	 */
	def CharSequence generateReferencedElement(Reference reference) {
		switch reference.referencedElement{
			Attribute:'''«reference.referencedElement.name»''' 
			Variable:'''«reference.referencedElement.name»'''
			Parameter:'''«reference.referencedElement.name»'''
			Method:'''«reference.referencedElement.name»(«getParameters(reference as ParameterizedReference)»)'''
			default: reference.toString 
		}
	}
	
	/**
	 * This method receives a ParameterizedReference and gets the parameters <br>
	 * of that, separated by comma.
	 * 
	 * @param reference ParameterizedReference from the parameters will be obtained
	 * @return parameters separated by comma
	 * */
	
	def CharSequence getParameters(ParameterizedReference<?> reference) '''«IF reference.parameters.size!=0»«FOR param: reference.parameters SEPARATOR ','»«param.valueTemplate»«ENDFOR»«ENDIF»'''
	
	/**
	 * This method asks if the given Reference has or no a tail.
	 * @param r Reference to confirm
	 * @return confirmation if the Reference has or no a tail
	 * */
	def boolean hasTail(Reference r){
		if(r.tail!=null) return true
		else return false
	}
	
	
	/**
	 * This method generates a PHP-formatted array to pass to an auto-generated method <br>
	 * in the page template.
	 * @param instance the ViewInstance which has the auto-generated method
	 * @return the PHP-formatted array
	 * */
	def CharSequence generateArray(ViewInstance instance){
		var accumulate= ""
		var string= ""
		for(i : 0..<instance.parameters.size){
			accumulate += "'"+ instance.type.typeSpecification.parameters.get(i).name +"'" + "=>" + valueTemplate(instance.parameters.get(i)) + ", " 
		}
		accumulate = accumulate.substring(0, accumulate.length()-2)
		string= "[" +  accumulate + "]"
		return string
	} 
	
	/**
	 * This method returns an URL-suitable string for the controller given.
	 * @param c a controller
	 * @return controller string
	 * */
	def String namedUrlForController(Controller c){
		var guia = "-controller"
		var nombreCompl= c.name
		var cadena= CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, nombreCompl)
		cadena+=guia
		return cadena
	} 
	
	/**
	 * This method makes a relative URL for a given ActionCall.
	 * @param ac an action call
	 * @return relative URL
	 * */
	def String namedUrlForActionCall(ActionCall ac){
		"/"+namedUrlForController(ac.action.eContainer.cast(Controller))+"/"+toKebabCase(ac.action.name)+"/"+generateParametersActionCall(ac);
	}
	
	/**
	 * This method generates the parameters for a given ActionCall, using the Blade format.
	 * @param call an action call
	 * @return formatted parameters
	 * */
	def CharSequence generateParametersActionCall(ActionCall call) {
		var generatedParameters= ''''''
		if(call.parameters.size>0){
			generatedParameters='''«FOR param: call.parameters»{{«valueTemplateForEntities(param)»}}«ENDFOR»'''
		}
		return generatedParameters
	}
	/**
	 * Enhanced version of the valueTemplate method with a somewhat<br>
	 * different format if the Expression is an ReferencedElement Entity.
	 * @return the current date
	 * */
	def CharSequence valueTemplateForEntities(Expression e){
		if(e instanceof VariableReference){
			if(e.referencedElement.type.typeSpecification instanceof Entity){
				return '''«IF hasTail(e)»«generateTailedElement(e)»«ELSE»$«e.referencedElement.name»«ENDIF»->id'''
			}
		}
		return valueTemplate(e)
	}
}