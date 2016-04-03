<?php
namespace App\Http\Controllers;


class Persistence{
	private $t;

	public function __construct($t){
		$this->t= $t;
	}

	public function remove($var){
		if(is_numeric($var)){
			$this->removeId($var);
		}
		else{
			$this->removeObject($var);
		}
	}

	public function removeObject($obj){
		$obj->delete();
	}

	public function removeId($id){
		$this->t::destroy($id);
	}

	public function create($obj){
		$obj->save();
	}	
	
	public function findAll(){
		return $this->t::all();
	}	

	
	public function findAllExcept($elementsToExclude){
		//TODO complete this method
	}

	public function isPersistent($obj){
		return isset($obj->id);
	}
	
	public function find($id){
		return $this->t::find($id);
	}

	public function save($obj){
		$obj->save();
	}

	public function findRange($rangeSize, $from){
	}
	
	public function count(){
		return $this->t::count();
	}

	public function addToCollection($container, $collection, $obj){
		//TODO complete this method
	}

	public function removeFromCollection($container, $collection, $obj){
		//TODO complete this method
	}

	public function assignToAttribute($container, $attribute, $obj){
		//TODO complete this method
	}

}

