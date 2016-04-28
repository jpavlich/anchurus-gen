@extends('layouts.app')
@section('content')
{!! Form::open(array('url' => ' ')) !!}
<div id= "panel0" >
	<table class="table table-bordered">
			<tr>
				<th><p>{!! Form::label ('label1' , 'Desayuno' ) !!}</p>
				</th><th><p>{!! Form::label ('label2' , 'Almuerzo' ) !!}</p>
				</th><th><p>{!! Form::label ('label3' , 'Cena' ) !!}</p>
				</th><th><p>{!! Form::label ('label4' , 'Merienda' ) !!}</p>
				</th><th><p>{!! Form::label ('label5' , 'Patologia' ) !!}</p>
				</th><th><p>{!! Form::label ('label6' , 'View' ) !!}</p>
				</th><th><p>{!! Form::label ('label7' , 'Edit' ) !!}</p>
				</th><th><p>{!! Form::label ('label8' , 'Delete' ) !!}</p>
				</th>
			</tr>
			@foreach ($dietas as $dieta)
			<tr>
				<td><p>{!! Form::label ('label9' ,$dieta->desayuno) !!}</p>
				</td><td><p>{!! Form::label ('label10' ,$dieta->almuerzo) !!}</p>
				</td><td><p>{!! Form::label ('label11' ,$dieta->cena) !!}</p>
				</td><td><p>{!! Form::label ('label12' ,$dieta->merienda) !!}</p>
				</td><td><p>{!! Form::label ('label13' ,$dieta->patologia) !!}</p>
				</td><td><input type="submit" value=" 'View' " onclick = 'this.form.action="/dieta-manager-controller/view-dieta/{{$dieta->id}}";'>
				</td><td><input type="submit" value=" 'Edit' " onclick = 'this.form.action="/dieta-manager-controller/edit-dieta/{{$dieta->id}}";'>
				</td><td><input type="submit" value=" 'Delete' " onclick = 'this.form.action="/dieta-manager-controller/delete-dieta/{{$dieta->id}}";'>
				</td>
			</tr>
			@endforeach
	</table>
	
</div>
{!! Form::close() !!}
@endsection
