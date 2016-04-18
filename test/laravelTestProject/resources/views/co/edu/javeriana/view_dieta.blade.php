@extends('layouts.app')
@section('content')
<div id= "panel1" >
	{!! Form::label ('label2' , 'Desayuno' ) !!}
	{!! Form::label ('label3' ,$dieta->desayuno) !!}
	{!! Form::label ('label4' , 'Almuerzo' ) !!}
	{!! Form::label ('label5' ,$dieta->almuerzo) !!}
	{!! Form::label ('label6' , 'Cena' ) !!}
	{!! Form::label ('label7' ,$dieta->cena) !!}
	{!! Form::label ('label8' , 'Merienda' ) !!}
	{!! Form::label ('label9' ,$dieta->merienda) !!}
	{!! Form::label ('label10' , 'Patologia' ) !!}
	{!! Form::label ('label11' ,$dieta->patologia) !!}
	<input type="submit" value=" 'Ok' " onclick = 'this.form.action="/dieta-manager-controller/list-all/";'>
	
</div>
@endsection
