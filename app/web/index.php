<?php
require_once __DIR__.'/../vendor/autoload.php';

$app = new Silex\Application();

$app->get('/hello/{name}/{lastName}', function($name, $lastName) use($app) {
    $user = new Myapp\User($name, $lastName);
    return 'Hello ,'. $app->escape($user->getFullName());
});

$app->run();
