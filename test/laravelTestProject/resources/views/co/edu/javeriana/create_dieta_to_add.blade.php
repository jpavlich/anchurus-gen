@extends('layouts.app')
@section('content')
{!! Form::open(array('url' => ' ')) !!}
<p><label> 'Desayuno' </label>{!!Form::text('desayuno', $dieta->desayuno ,array('size' => '25')) !!}</p>
<p><label> 'Almuerzo' </label>{!!Form::text('almuerzo', $dieta->almuerzo ,array('size' => '25')) !!}</p>
<p><label> 'Cena' </label>{!!Form::text('cena', $dieta->cena ,array('size' => '25')) !!}</p>
<p><label> 'Merienda' </label>{!!Form::text('merienda', $dieta->merienda ,array('size' => '25')) !!}</p>
<p><label> 'Patologia' </label>{!!Form::text('patologia', $dieta->patologia ,array('size' => '10')) !!}</p>
<input type="submit" value=" 'Save' " onclick = 'this.form.action="/dieta-manager-controller/save-dieta/{{$dieta->id}}";'>
{!! Form::close() !!}
@endsection
