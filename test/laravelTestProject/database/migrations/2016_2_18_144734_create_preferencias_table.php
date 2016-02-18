<?php
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreatePreferenciasTable extends Migration{
	
	public function up(){
		Schema::create('preferencias', function(Blueprint $table){
			$table->increments('id');
			$table->string(preferencia)->default('');
			$table->timestamps();
		});
		
	}
	
	public function down(){
		DB::statement('SET FOREIGN_KEY_CHECKS = 0');
		Schema::drop('preferencias');
		DB::statement('SET FOREIGN_KEY_CHECKS = 1');
	}
	
}	
