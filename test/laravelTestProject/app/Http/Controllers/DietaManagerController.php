<?php
namespace App\Http\Controllers;

use Input;
use Redirect;
use Illuminate\Http\Request;

use App\Http\Requests;
use App\Http\Controllers\Controller;
use App\co\edu\javeriana\Dieta;
use App\Http\Controllers\Persistence;
class DietaManagerController extends Controller{
	private $persistence;
	public function __construct(){
		$this->persistence = new Persistence('App\co\edu\javeriana\Dieta');
	}
	public function initTest(Request $req ){
		
	}
	public function listAll(Request $req ){
		return view('co.edu.javeriana.dieta_list', ['dietas'=>$this->persistence->findAll()]); 
		
	}
	public function listDieta(Request $req , $dietaList){
		$dietaList = NULL;
		if(is_numeric($dietaList_id)){
			$dietaList = Collection::find($dietaList_id);
			$dietaList->update($req->all());
		}
		return view('co.edu.javeriana.dieta_list', ['dietas'=>$dietaList]); 
		
	}
	public function createDietaToAdd(Request $req , $container,$collection){
		$container = NULL;
		$collection = NULL;
		if(is_numeric($container_id)){
			$container = Any::find($container_id);
			$container->update($req->all());
		}
		if(is_numeric($collection_id)){
			$collection = Collection::find($collection_id);
			$collection->update($req->all());
		}
		return view('co.edu.javeriana.create_dieta_to_add', ['container'=>$container, 'collection'=>$collection, 'dieta'=>new Dieta]); 
		
	}
	public function viewDieta(Request $req , $dieta_id){
		$dieta = NULL;
		if(is_numeric($dieta_id)){
			$dieta = Dieta::find($dieta_id);
			$dieta->update($req->all());
		}
		return view('co.edu.javeriana.view_dieta', ['dieta'=>$dieta]); 
		
	}
	public function editDieta(Request $req , $dieta_id){
		$dieta = NULL;
		if(is_numeric($dieta_id)){
			$dieta = Dieta::find($dieta_id);
			$dieta->update($req->all());
		}
		return view('co.edu.javeriana.edit_dieta', ['dieta'=>$dieta]); 
		
	}
	public function createDieta(Request $req ){
		return view('co.edu.javeriana.edit_dieta', ['dieta'=>new Dieta]); 
		
	}
	public function saveDieta(Request $req , $dieta_id){
		$dieta = NULL;
		if(is_numeric($dieta_id)){
			$dieta = Dieta::find($dieta_id);
			$dieta->update($req->all());
			$dieta->save();
		}
		
	}
	public function deleteDieta(Request $req , $dieta_id){
		$dieta = NULL;
		if(is_numeric($dieta_id)){
			$dieta = Dieta::find($dieta_id);
			$dieta->update($req->all());
		}
		
	}
}
