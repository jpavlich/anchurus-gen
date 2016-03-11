<?php

Route::match(['GET', 'POST'], 'dieta-manager/init-test', 'DietaManager@initTest');
Route::match(['GET', 'POST'], 'dieta-manager/list-all', 'DietaManager@listAll');
Route::match(['GET', 'POST'], 'dieta-manager/list-dieta/{dietaList}', 'DietaManager@listDieta');
Route::match(['GET', 'POST'], 'dieta-manager/create-dieta-to-add/{container}/{collection}', 'DietaManager@createDietaToAdd');
Route::match(['GET', 'POST'], 'dieta-manager/view-dieta/{dieta}', 'DietaManager@viewDieta');
Route::match(['GET', 'POST'], 'dieta-manager/edit-dieta/{dieta}', 'DietaManager@editDieta');
Route::match(['GET', 'POST'], 'dieta-manager/create-dieta', 'DietaManager@createDieta');
Route::match(['GET', 'POST'], 'dieta-manager/save-dieta/{dieta}', 'DietaManager@saveDieta');
Route::match(['GET', 'POST'], 'dieta-manager/delete-dieta/{dieta}', 'DietaManager@deleteDieta');
Route::match(['GET', 'POST'], 'default-page-dispatcher/edit/{ent}', 'DefaultPageDispatcher@edit');
Route::match(['GET', 'POST'], 'default-page-dispatcher/view/{ent}', 'DefaultPageDispatcher@view');
Route::match(['GET', 'POST'], 'default-page-dispatcher/list/{list}', 'DefaultPageDispatcher@list');
