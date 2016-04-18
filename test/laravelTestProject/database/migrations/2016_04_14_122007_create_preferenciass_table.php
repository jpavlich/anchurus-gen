<?php
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreatePreferenciassTable extends Migration{
	
	public function up(){
		Schema::create('preferenciass', function(Blueprint $table){
			$table->increments('id');
			$table->string('preferencia')->default('');
			$table->timestamps();
		});
		
	}
	
	public function down(){
		DB::statement('SET FOREIGN_KEY_CHECKS = 0');
		Schema::drop('preferenciass');
		DB::statement('SET FOREIGN_KEY_CHECKS = 1');
	}
	
}	
