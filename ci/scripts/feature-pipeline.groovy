#!groovy

utils = load 'ci/scripts/utils.groovy'

def go(String branchName) {
  print "working with $branchName"
  print "doing feature testing pipeline";
  stage "testing"
  node {
      utils.runTests()
  }
  stage "preview"
  node {}
}

return this
