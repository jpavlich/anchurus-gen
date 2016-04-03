@extends('layouts.app')
@section('content')
{!! Form::open(array('url' => ' ')) !!}
<div id= "panel1" >
	{!! Form::submit ('Back') !!}
	
</div>
{!! Form::close() !!}
@endsection
