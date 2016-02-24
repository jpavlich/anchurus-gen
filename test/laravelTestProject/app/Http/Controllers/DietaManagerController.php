<?php
namespace App\Http\Controllers;

use Input;
use Redirect;
use Illuminate\Http\Request;

use App\Project;
use App\Http\Requests;
use App\Http\Controllers\Controller;

class DietaManagerController extends Controller{
	public function initTest(){
	}
	public function listAll(){
		return view('co.edu.javeriana.dieta_list', [ "" => $ ] );
	}
	public function listDieta($dietaList){
		return view('co.edu.javeriana.dieta_list', [ "" => $ ] );
	}
	public function createDietaToAdd($container,$collection){
		return view('co.edu.javeriana.create_dieta_to_add', [ "" => $ ] );
	}
	public function viewDieta($dieta){
		return view('co.edu.javeriana.view_dieta', [ "" => $ ] );
	}
	public function editDieta($dieta){
		return view('co.edu.javeriana.edit_dieta', [ "" => $ ] );
	}
	public function createDieta(){
		return view('co.edu.javeriana.edit_dieta', [ "" => $ ] );
	}
	public function saveDieta($dieta){
	}
	public function deleteDieta($dieta){
	}
}
