@extends('layouts.app')
@section('content')
<div id= "panel1" >
	{!! Form::label ('label2' ,Desayuno) !!}
	{!! Form::label ('label3' ,$dieta->$this->desayuno) !!}
	{!! Form::label ('label4' ,Almuerzo) !!}
	{!! Form::label ('label5' ,$dieta->$this->almuerzo) !!}
	{!! Form::label ('label6' ,Cena) !!}
	{!! Form::label ('label7' ,$dieta->$this->cena) !!}
	{!! Form::label ('label8' ,Merienda) !!}
	{!! Form::label ('label9' ,$dieta->$this->merienda) !!}
	{!! Form::label ('label10' ,Patologia) !!}
	{!! Form::label ('label11' ,$dieta->$this->patologia) !!}
	{!! Form::submit ('Ok') !!}
	
</div>
@endsection
