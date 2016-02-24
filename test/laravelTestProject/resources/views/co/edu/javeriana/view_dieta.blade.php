@extends('layouts.app')
@section('content')
<div id= "panel1" >
	<label>Desayuno</label>
	<label>{{ $dieta->desayuno }} </label>
	<label>Almuerzo</label>
	<label>{{ $dieta->almuerzo }} </label>
	<label>Cena</label>
	<label>{{ $dieta->cena }} </label>
	<label>Merienda</label>
	<label>{{ $dieta->merienda }} </label>
	<label>Patologia</label>
	<label>{{ $dieta->patologia }} </label>
	{!! Form::submit ('Ok') !!}
	
</div>
@endsection
