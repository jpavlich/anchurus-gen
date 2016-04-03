<?php
namespace App\Http\Controllers;

use Input;
use Redirect;
use Illuminate\Http\Request;

use App\Http\Requests;
use App\Http\Controllers\Controller;
use App\Dieta;
use App\Http\Controllers\Persistence;
class DietaManagerController extends Controller{
	private $persistence = new Persistence;
	
	public function initTest(){
	}
	public function listAll(){
		return view('co.edu.javeriana.dieta_list', ['dietas'=>$this->persistence->findAll()]); 
	}
	public function listDieta($dietaList){
		return view('co.edu.javeriana.dieta_list', ['dietas'=>$dietaList]); 
	}
	public function createDietaToAdd($container,$collection){
		return view('co.edu.javeriana.create_dieta_to_add', ['container'=>$container, 'collection'=>$collection, 'dieta'=>new Dieta]); 
	}
	public function viewDieta($dieta){
		return view('co.edu.javeriana.view_dieta', ['dieta'=>$dieta]); 
	}
	public function editDieta($dieta){
		return view('co.edu.javeriana.edit_dieta', ['dieta'=>$dieta]); 
	}
	public function createDieta(){
		return view('co.edu.javeriana.edit_dieta', ['dieta'=>new Dieta]); 
	}
	public function saveDieta($dieta){
	}
	public function deleteDieta($dieta){
	}
}
