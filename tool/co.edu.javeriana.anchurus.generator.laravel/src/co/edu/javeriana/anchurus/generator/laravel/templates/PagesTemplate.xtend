 package co.edu.javeriana.anchurus.generator.laravel.templates

import co.edu.javeriana.anchurus.generator.laravel.utils.UtilsAnchurus
import co.edu.javeriana.isml.generator.common.SimpleTemplate
import co.edu.javeriana.isml.isml.ForView
import co.edu.javeriana.isml.isml.IfView
import co.edu.javeriana.isml.isml.Page
import co.edu.javeriana.isml.isml.Reference
import co.edu.javeriana.isml.isml.ViewInstance
import co.edu.javeriana.isml.isml.ViewStatement
import co.edu.javeriana.isml.scoping.IsmlModelNavigation
import co.edu.javeriana.isml.validation.TypeChecker
import com.google.inject.Inject
import org.eclipse.emf.common.util.EList
import co.edu.javeriana.isml.isml.Controller
import co.edu.javeriana.isml.isml.Action
import co.edu.javeriana.isml.isml.ActionCall
import co.edu.javeriana.isml.isml.VariableReference
import co.edu.javeriana.isml.isml.NamedViewBlock

class PagesTemplate extends SimpleTemplate<Page> {
	@Inject extension TypeChecker
	@Inject extension IsmlModelNavigation
	@Inject extension UtilsAnchurus	
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
		<p>{!! Form::label ('«parte.id»' ,«parte.parameters.get(0).valueTemplate») !!}</p>
		«ELSE»
		<p>Esto no est&iacute; generado</p>
		«ENDIF»
	'''
	
	def getId(ViewInstance parte){
		if(parte.view.name.equals("Text"))
			return parte.parameters.get(1).cast(VariableReference).tail.referencedElement.name
		else
			return parte.view.name.toFirstLower + i++
		
	}
	
	def CharSequence inputText(ViewInstance parte) '''
		«IF parte.rows <= 1»
		<p><label>«parte.parameters.get(0).valueTemplate»</label>{!!Form::text('«parte.id»', «parte.parameters.get(1).valueTemplate» ,array('size' => '«parte.parameters.get(2).valueTemplate»')) !!}</p>
		«ELSE»
		«ENDIF»
	'''
	
	def CharSequence button(ViewInstance parte) '''
		<input type="submit" value="«parte.parameters.get(0).valueTemplate»" onclick = 'this.form.action="«namedUrlForActionCall(parte.actionCall)»";'>
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
	<table class="table table-bordered">
			«parte.tableHeader.generateHead»
			«parte.tableBody.generateBody»
	</table>
	'''
	
	def CharSequence generateBody(NamedViewBlock block)'''
	«templateForLoopTables(block.body.head.cast(ForView))»
	'''
	
	def templateForLoopTables(ForView fv) '''
	@foreach («fv.collection.valueTemplate» as $«fv.variable.name»)
	<tr>
		«FOR cell : fv.body»<td>«cell.plantillaParte»</td>«ENDFOR»
	</tr>
	@endforeach
	'''
	
	def CharSequence generateHead(NamedViewBlock block)'''
	<tr>
		«FOR part: block.body»<th>«plantillaParte(part)»</th>«ENDFOR»
	</tr>
	'''
	
	
	def CharSequence password(ViewInstance parte)'''
		{!! Form::password('«parte.id»') !!}
	'''
	
	def CharSequence checkBox(ViewInstance parte) '''
		{!! Form::checkbox('«parte.id»', '«parte.parameters.get(0).valueTemplate»') !!}
	'''
	
	def CharSequence link(ViewInstance parte)'''
	'''
	
	def CharSequence comboChooser(ViewInstance parte) '''
	'''
	
	def CharSequence image(ViewInstance parte) '''
		{!! HTML::image('«parte.parameters.get(0).valueTemplate»') !!}
	'''
	
	def dispatch CharSequence plantillaParte(IfView ivtable)'''
	'''
	
	
	def dispatch CharSequence plantillaParte(ForView fvtable)'''
	'''
	
	def dispatch CharSequence plantillaParte(Reference rtable)'''
	'''	

}
