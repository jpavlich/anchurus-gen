@extends('layouts.app')
@section('content')
{!! Form::open(array('url' => ' ')) !!}
<label>Desayuno</label>{!!Form::text('text1', '' ,array('size' => '25')) !!}
<label>Almuerzo</label>{!!Form::text('text2', '' ,array('size' => '25')) !!}
<label>Cena</label>{!!Form::text('text3', '' ,array('size' => '25')) !!}
<label>Merienda</label>{!!Form::text('text4', '' ,array('size' => '25')) !!}
<label>Patologia</label>{!!Form::text('text5', '' ,array('size' => '10')) !!}
{!! Form::submit ('Save') !!}
{!! Form::submit ('Cancel') !!}
{!! Form::close() !!}
@endsection
