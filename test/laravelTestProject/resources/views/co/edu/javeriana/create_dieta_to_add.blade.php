@extends('layouts.app')
@section('content')
{!! Form::open(array('url' => ' ')) !!}
<label> 'Desayuno' </label>{!!Form::text('desayuno', $dieta->desayuno ,array('size' => '25')) !!}
<label> 'Almuerzo' </label>{!!Form::text('almuerzo', $dieta->almuerzo ,array('size' => '25')) !!}
<label> 'Cena' </label>{!!Form::text('cena', $dieta->cena ,array('size' => '25')) !!}
<label> 'Merienda' </label>{!!Form::text('merienda', $dieta->merienda ,array('size' => '25')) !!}
<label> 'Patologia' </label>{!!Form::text('patologia', $dieta->patologia ,array('size' => '10')) !!}
<input type="submit" value=" 'Save' " onclick = 'this.form.action="/dieta-manager-controller/save-dieta/{{$dieta->id}}";'>
{!! Form::close() !!}
@endsection
