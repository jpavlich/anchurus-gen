@extends('layouts.app')
@section('content')
<div id= "panel1" >
	<p>{!! Form::label ('label2' , 'Desayuno' ) !!}</p>
	<p>{!! Form::label ('label3' ,$dieta->desayuno) !!}</p>
	<p>{!! Form::label ('label4' , 'Almuerzo' ) !!}</p>
	<p>{!! Form::label ('label5' ,$dieta->almuerzo) !!}</p>
	<p>{!! Form::label ('label6' , 'Cena' ) !!}</p>
	<p>{!! Form::label ('label7' ,$dieta->cena) !!}</p>
	<p>{!! Form::label ('label8' , 'Merienda' ) !!}</p>
	<p>{!! Form::label ('label9' ,$dieta->merienda) !!}</p>
	<p>{!! Form::label ('label10' , 'Patologia' ) !!}</p>
	<p>{!! Form::label ('label11' ,$dieta->patologia) !!}</p>
	<input type="submit" value=" 'Ok' " onclick = 'this.form.action="/dieta-manager-controller/list-all/";'>
	
</div>
@endsection
