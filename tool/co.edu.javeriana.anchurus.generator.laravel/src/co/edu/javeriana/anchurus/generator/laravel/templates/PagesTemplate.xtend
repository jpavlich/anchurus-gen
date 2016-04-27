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
	}
	
	
	/**
	 * This method generates the whole PHP page archive from a given ISML page.
	 * @param page the Page
	 * @return the page archive
	 * */
	override def CharSequence template(Page page) '''
		@extends('layouts.app')
		@section('content')
		«IF page.body != null»
			«pageParts(page.body)»
		«ENDIF»
		@endsection
	'''
	
	/**
	 * This method delegates to other method the widget generation of each element passed<br>
	 * in the parts list.
	 * @param partsList the page's part list 
	 * @return generated string with the part list parsed
	 * */
	def CharSequence pageParts(EList<ViewStatement> partsList) '''
		«IF partsList != null»
			«FOR parte: partsList»
				«plantillaParte(parte)»
			«ENDFOR»
		«ENDIF»
	'''
	/**
	 * This method dispatches to specific methods for the parts generation of the page.
	 * @param vi the ISML Page widget  
	 * @return generated string with the specific part parsed
	 * */
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
	
	/**
	 * This method takes an ISML ViewInstance Label and returns a Blade-formatted label element 
	 * @param part the ISML ViewInstance element
	 * @return Label element with Blade format
	 * */
	def CharSequence label(ViewInstance part) '''
		«IF part.actionCall==null»
		<p>{!! Form::label ('«part.id»' ,«part.parameters.get(0).valueTemplate») !!}</p>
		«ELSE»
		<p>Esto no est&iacute; generado</p>
		«ENDIF»
	'''
	
	/**
	 * This method takes an ISML ViewInstance and returns an unique ID for that element. 
	 * @param part the ISML ViewInstance element
	 * @return ID for the supplied ViewInstance
	 * */
	def getId(ViewInstance part){
		if(part.view.name.equals("Text"))
			return part.parameters.get(1).cast(VariableReference).tail.referencedElement.name
		else
			return part.view.name.toFirstLower + i++
		
	}
	/**
	 * This method takes an ISML ViewInstance Text and returns a Blade-formatted input text element 
	 * @param part the ISML ViewInstance element
	 * @return InputText element with Blade format
	 * */
	def CharSequence inputText(ViewInstance part) '''
		«IF part.rows <= 1»
		<p><label>«part.parameters.get(0).valueTemplate»</label>{!!Form::text('«part.id»', «part.parameters.get(1).valueTemplate» ,array('size' => '«part.parameters.get(2).valueTemplate»')) !!}</p>
		«ELSE»
		«ENDIF»
	'''
	/**
	 * This method takes an ISML ViewInstance Button and returns a Blade-formatted Button element 
	 * @param part the ISML ViewInstance element
	 * @return Button element with Blade format
	 * */
	def CharSequence button(ViewInstance parte) '''
		<input type="submit" value="«parte.parameters.get(0).valueTemplate»" onclick = 'this.form.action="«namedUrlForActionCall(parte.actionCall)»";'>
	'''
	
	/**
	 * This method takes an ISML ViewInstance Form and returns a Blade-formatted form element, with its elements inside. 
	 * @param part the ISML ViewInstance element
	 * @return Form element with Blade format
	 * */
	def CharSequence form(ViewInstance parte) '''
		{!! Form::open(array('url' => ' ')) !!}
		«FOR bloque: parte.getBody»
			«plantillaParte(bloque)»
		«ENDFOR»
		{!! Form::close() !!}
	'''
	/**
	 * This method takes an ISML ViewInstance Label and returns a HTML-formatted label element, with its elements inside 
	 * @param part the ISML ViewInstance element
	 * @return Panel element with HTML format
	 * */
	def CharSequence panel(ViewInstance parte) '''
	<div id= "«parte.id»" >
		«FOR partBlock : parte.body»
			«plantillaParte(partBlock)»
		«ENDFOR»
		
	</div>
	'''
	/**
	 * This method must take an ISML ViewInstance PanelButton and returns a Blade-formatted panel button element 
	 * @param part the ISML ViewInstance element
	 * @return Panel Button element with Blade format
	 * */
	def CharSequence panelButton(ViewInstance parte) '''
	'''
	/**
	 * This method takes an ISML ViewInstance DataTable and returns a HTML-formatted table element 
	 * @param part the ISML ViewInstance element
	 * @return Table element with HTML format
	 * */
	def CharSequence dataTable(ViewInstance parte) '''
	<table class="table table-bordered">
			«parte.tableHeader.generateHead»
			«parte.tableBody.generateBody»
	</table>
	'''
	
	/**
	 * This method delegates to other method the table body generation  
	 * @param block the table body
	 * @return Body table
	 * */
	def CharSequence generateBody(NamedViewBlock block)'''
	«templateForLoopTables(block.body.head.cast(ForView))»
	'''
	
	/**
	 * This method generates each table cell (table body) 
	 * @param fv the ForView used to show in ISML each table cell
	 * @return the built body table
	 * */
	def templateForLoopTables(ForView fv) '''
	@foreach («fv.collection.valueTemplate» as $«fv.variable.name»)
	<tr>
		«FOR cell : fv.body»<td>«cell.plantillaParte»</td>«ENDFOR»
	</tr>
	@endforeach
	'''
	
	/**
	 * This method generates the first row of the table. 
	 * @param block the table head
	 * @return First row of the table
	 * */
	def CharSequence generateHead(NamedViewBlock block)'''
	<tr>
		«FOR part: block.body»<th>«plantillaParte(part)»</th>«ENDFOR»
	</tr>
	'''
	
	
	/**
	 * This method takes an ISML ViewInstance Password and returns a Blade-formatted password element 
	 * @param part the ISML ViewInstance element
	 * @return Password element with Blade format
	 * */
	def CharSequence password(ViewInstance parte)'''
		{!! Form::password('«parte.id»') !!}
	'''
	/**
	 * This method takes an ISML ViewInstance CheckBox and returns a Blade-formatted check box element 
	 * @param part the ISML ViewInstance element
	 * @return CheckBox element with Blade format
	 * */
	def CharSequence checkBox(ViewInstance parte) '''
		{!! Form::checkbox('«parte.id»', '«parte.parameters.get(0).valueTemplate»') !!}
	'''
	/**
	 * This method must take an ISML ViewInstance Link and must return a Blade/HTML-formatted link element 
	 * @param part the ISML ViewInstance element
	 * @return Link element with Blade/HTML format
	 * */
	def CharSequence link(ViewInstance parte)'''
	'''
	
	/**
	 * This method must take an ISML ViewInstance ComboChooser and must return a Blade/HTML-formatted combo box element 
	 * @param part the ISML ViewInstance element
	 * @return ComboChooser element with Blade/HTML format
	 * */
	def CharSequence comboChooser(ViewInstance parte) '''
	'''
	
	/**
	 * This method takes an ISML ViewInstance Image and returns a Blade-formatted image element 
	 * @param part the ISML ViewInstance element
	 * @return Image element with Blade format
	 * */
	def CharSequence image(ViewInstance parte) '''
		{!! HTML::image('«parte.parameters.get(0).valueTemplate»') !!}
	'''
	
	/**
	 * This method must take an ISML ViewInstance IfView and must return a Blade-formatted "if" element 
	 * @param ivtable the ISML IfView
	 * @return If element with Blade format
	 * */
	def dispatch CharSequence plantillaParte(IfView ivtable)'''
	'''
	
	/**
	 * This method must take an ISML ViewInstance ForView and must return a Blade-formatted "for" element 
	 * @param fvtable the ISML ForView
	 * @return For element with Blade format
	 * */
	def dispatch CharSequence plantillaParte(ForView fvtable)'''
	'''
	
	/**
	 * This method must take an ISML Reference and must return a Blade-formatted Reference element 
	 * @param rtable the ISML Reference
	 * @return Reference element with Blade format
	 * */
	def dispatch CharSequence plantillaParte(Reference rtable)'''
	'''	

}
