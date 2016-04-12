std = load 'ci/scripts/std.groovy'

def go(String branchName) {
  print "working with $branchName"
  print "doing feature testing pipeline";
  stage "testing"
  std.runTests()

  stage "preview"
  node {}
}

return this
