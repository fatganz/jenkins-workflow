<?php
class UserTest extends PHPUnit_Framework_TestCase
{
  public function testCanGetFullName()
  {
    $user = new Myapp\User("Test", "User");
    $this->assertEquals($user->getFullName(), "Test User");
  }
}
