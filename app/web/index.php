<?php
require_once __DIR__.'/../vendor/autoload.php';
require_once 'version.php';

$app = new Silex\Application();

$app->get('/hello/{name}/{lastName}', function($name, $lastName) use($app) {
    $user = new Myapp\User($name, $lastName);
    return 'Hello 3, '. $app->escape($user->getFullName()) . "! version: " . VERSION;
});

$app->run();
