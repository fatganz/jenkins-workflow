
def go(String branchName) {
  print "working with $branchName"
  print "doing feature testing pipeline";
  stage "testing"
  node {
      std.runTests()
  }
  stage "preview"
  node {}
}

return this
