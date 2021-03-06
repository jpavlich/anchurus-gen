<?php

use Illuminate\Database\Seeder;

class DatabaseSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::table('dietas')->insert([
           'desayuno' => 'huevos con patacón',
           'almuerzo' => 'bandeja paisa',
           'cena' => 'sopa de arroz',
	   'merienda' => 'manzana',
           'patologia' => 0		

       ]);

	DB::table('dietas')->insert([
           'desayuno' => 'chocolate con pan',
           'almuerzo' => 'lentejas',
           'cena' => 'ensalada',
	   'merienda' => 'sandwich',
           'patologia' => 2		

       ]);
	
	DB::table('dietas')->insert([
           'desayuno' => 'caldo de pescado',
           'almuerzo' => 'garbanzos',
           'cena' => 'salchipapa',
	   'merienda' => 'durazno',
           'patologia' => 1		

       ]);

	DB::table('dietas')->insert([
           'desayuno' => 'carne asada',
           'almuerzo' => 'frijoles',
           'cena' => 'pizza',
	   'merienda' => 'yogur',
           'patologia' => 3		

       ]);

    }
}
