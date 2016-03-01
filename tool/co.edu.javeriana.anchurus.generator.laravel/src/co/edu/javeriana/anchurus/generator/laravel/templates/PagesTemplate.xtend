 package co.edu.javeriana.anchurus.generator.laravel.templates

import co.edu.javeriana.isml.generator.common.SimpleTemplate
import co.edu.javeriana.isml.isml.Expression
import co.edu.javeriana.isml.isml.ForView
import co.edu.javeriana.isml.isml.IfView
import co.edu.javeriana.isml.isml.LiteralValue
import co.edu.javeriana.isml.isml.Page
import co.edu.javeriana.isml.isml.Reference
import co.edu.javeriana.isml.isml.ViewInstance
import co.edu.javeriana.isml.isml.ViewStatement
import co.edu.javeriana.isml.scoping.IsmlModelNavigation
import co.edu.javeriana.isml.validation.TypeChecker
import com.google.inject.Inject
import org.eclipse.emf.common.util.EList
import co.edu.javeriana.isml.isml.VariableReference

class PagesTemplate extends SimpleTemplate<Page> {
	@Inject extension TypeChecker
	@Inject extension IsmlModelNavigation	
	int i

	override preprocess(Page e) {
		i= 1;	
	}
	
	

	override def CharSequence template(Page page) '''
		@extends('layouts.app')
		@section('content')
		«IF page.body != null»
			«partesPagina(page.body)»
		«ENDIF»
		@endsection
	'''
	
	def CharSequence partesPagina(EList<ViewStatement> listaPartes) '''
		«IF listaPartes != null»
			«FOR parte: listaPartes»
				«plantillaParte(parte)»
			«ENDFOR»
		«ENDIF»
	'''
	
	def dispatch CharSequence plantillaParte(ViewInstance vi) {
		switch(vi.type.typeSpecification.typeSpecificationString){
			case "Label" : label(vi)
			case "Text" : inputText(vi)
			case "Button": button(vi)
			case "Form": form(vi)
			case "Panel": panel(vi)
			
			case "PanelButton": panelButton(vi)
			case "DataTable": dataTable(vi)
			case "Password": password(vi)
			case "CheckBox": checkBox(vi)
			case "Link": link(vi)
			case "ComboChooser": comboChooser(vi)
			case "Image": image(vi)
			
		}
	}
	
	def CharSequence label(ViewInstance parte) '''
		«IF parte.actionCall==null»
		<label>«parte.parameters.get(0).valueTemplate»</label>
		«ELSE»
		<p>Esto no est&iacute; generado</p>
		«ENDIF»
	'''
	
	def CharSequence valueTemplate(Expression e){
		switch e{
			LiteralValue: e.literal.toString
			VariableReference: '''{{ $«e.referencedElement.name»->«e.tail.referencedElement.name» }} '''
			default: e.toString  
		}
	}
	
	def getId(ViewInstance parte){
		if(parte.name!=null){
			return parte.name
		}
		else{
			return parte.view.name.toFirstLower + i++
		}
	}
	
	def CharSequence inputText(ViewInstance parte) '''
		«IF parte.rows <= 1»
		<label>«parte.parameters.get(0).valueTemplate»</label>{!!Form::text('«parte.id»', array('size' => '«parte.parameters.get(2).valueTemplate»')) !!}
		«ELSE»
		«ENDIF»
	'''
	
	def CharSequence button(ViewInstance parte) '''
		{!! Form::submit ('«parte.parameters.get(0).valueTemplate»') !!}
	'''
	
	def CharSequence form(ViewInstance parte) '''
		{!! Form::open(array('url' => ' ')) !!}
		«FOR bloque: parte.getBody»
			«plantillaParte(bloque)»
		«ENDFOR»
		{!! Form::close() !!}
	'''
	
	def CharSequence panel(ViewInstance parte) '''
	<div id= "«parte.id»" >
		«FOR partBlock : parte.body»
			«plantillaParte(partBlock)»
		«ENDFOR»
		
	</div>
	'''
	
	def CharSequence panelButton(ViewInstance parte) '''
	'''
	
	def CharSequence dataTable(ViewInstance parte) '''
	'''
	
	def CharSequence password(ViewInstance parte)'''
		{{ Form::password('«parte.id»') }}
	'''
	
	def CharSequence checkBox(ViewInstance parte) '''
		{!! Form::checkbox('«parte.id»', '«parte.parameters.get(0).valueTemplate»') !!}
	'''
	
	def CharSequence link(ViewInstance parte)'''
	'''
	
	def CharSequence comboChooser(ViewInstance parte) '''
	'''
	
	def CharSequence image(ViewInstance parte) '''
	'''
	
	def dispatch CharSequence plantillaParte(IfView ivtable)'''
	'''
	
	
	def dispatch CharSequence plantillaParte(ForView fvtable)'''
	'''
	
	def dispatch CharSequence plantillaParte(Reference rtable)'''
	'''
		

}
