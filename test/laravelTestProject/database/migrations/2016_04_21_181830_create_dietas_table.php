<?php
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateDietasTable extends Migration{
	
	public function up(){
		Schema::create('dietas', function(Blueprint $table){
			$table->increments('id');
			$table->string('desayuno')->default('');
			$table->string('almuerzo')->default('');
			$table->string('cena')->default('');
			$table->string('merienda')->default('');
			$table->bigInteger('patologia')->default(0);
			$table->timestamps();
		});
		
	}
	
	public function down(){
		DB::statement('SET FOREIGN_KEY_CHECKS = 0');
		Schema::drop('dietas');
		DB::statement('SET FOREIGN_KEY_CHECKS = 1');
	}
	
}	
