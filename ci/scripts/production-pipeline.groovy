#!groovy
utils = load 'ci/scripts/utils.groovy'

def go(String branchName) {
  print "working with $branchName"
  print "doing production pipeline";

  stage "testing"
  utils.runTests()

  stage "qa"
  node {}

  stage "production"
  node {}
}

return this
