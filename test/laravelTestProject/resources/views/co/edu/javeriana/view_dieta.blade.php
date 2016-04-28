@extends('layouts.app')
@section('content')
{!! Form::open(array('url' => ' ')) !!}
<div id= "panel0" >
	<p>{!! Form::label ('label1' , 'Desayuno' ) !!}</p>
	<p>{!! Form::label ('label2' ,$dieta->desayuno) !!}</p>
	<p>{!! Form::label ('label3' , 'Almuerzo' ) !!}</p>
	<p>{!! Form::label ('label4' ,$dieta->almuerzo) !!}</p>
	<p>{!! Form::label ('label5' , 'Cena' ) !!}</p>
	<p>{!! Form::label ('label6' ,$dieta->cena) !!}</p>
	<p>{!! Form::label ('label7' , 'Merienda' ) !!}</p>
	<p>{!! Form::label ('label8' ,$dieta->merienda) !!}</p>
	<p>{!! Form::label ('label9' , 'Patologia' ) !!}</p>
	<p>{!! Form::label ('label10' ,$dieta->patologia) !!}</p>
	<input type="submit" value=" 'Ok' " onclick = 'this.form.action="/dieta-manager-controller/list-all/";'>
	
</div>
{!! Form::close() !!}
@endsection
