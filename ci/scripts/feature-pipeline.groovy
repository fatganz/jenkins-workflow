def scrpt = load 'ci/scripts/std.groovy'

def go(String branchName) {
  print "working with $branchName"
  print "doing feature testing pipeline";
  stage "testing"
  node {
      scrpt.runTests()
  }
  stage "preview"
  node {}
}

return this
