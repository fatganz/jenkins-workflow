std = load 'ci/scripts/std.groovy'

def go(String branchName) {
  print "working with $branchName"
  print "doing production pipeline";
  
  stage "testing"
  std.runTests()

  stage "qa"
  node {}

  stage "production"
  node {}
}

return this
