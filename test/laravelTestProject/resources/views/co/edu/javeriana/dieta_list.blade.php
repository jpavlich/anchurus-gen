@extends('layouts.app')
@section('content')
{!! Form::open(array('url' => ' ')) !!}
<div id= "panel1" >
	<table class="table table-bordered">
			<tr>
				<th>{!! Form::label ('label2' , 'Desayuno' ) !!}
				</th><th>{!! Form::label ('label3' , 'Almuerzo' ) !!}
				</th><th>{!! Form::label ('label4' , 'Cena' ) !!}
				</th><th>{!! Form::label ('label5' , 'Merienda' ) !!}
				</th><th>{!! Form::label ('label6' , 'Patologia' ) !!}
				</th><th>{!! Form::label ('label7' , 'View' ) !!}
				</th><th>{!! Form::label ('label8' , 'Edit' ) !!}
				</th><th>{!! Form::label ('label9' , 'Delete' ) !!}
				</th>
			</tr>
			@foreach ($dietas as $dieta)
			<tr>
				<td>{!! Form::label ('label10' ,$dieta->desayuno) !!}
				</td><td>{!! Form::label ('label11' ,$dieta->almuerzo) !!}
				</td><td>{!! Form::label ('label12' ,$dieta->cena) !!}
				</td><td>{!! Form::label ('label13' ,$dieta->merienda) !!}
				</td><td>{!! Form::label ('label14' ,$dieta->patologia) !!}
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
