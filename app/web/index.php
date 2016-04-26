<?php
require_once __DIR__.'/../vendor/autoload.php';
define("VERSION", getenv("BUILD_TAG"));

$app = new Silex\Application();

$app->get('/hello/{name}/{lastName}', function($name, $lastName) use($app) {
    $user = new Myapp\User($name, $lastName);
    return 'Hello 100500, '. $app->escape($user->getFullName()) . "! version: " . VERSION;
});

$app->run();
