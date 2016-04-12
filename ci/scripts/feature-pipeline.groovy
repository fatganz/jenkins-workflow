def go(String branchName) {
  print "working with $branchName"
  print "doing feature testing pipeline";
  stage "preview"
  node {}
}

return this
