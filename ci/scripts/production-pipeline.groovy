def go(String branchName) {
  print "working with $branchName"
  print "doing production pipeline";
  stage "qa"
  node {}

  stage "production"
  node {}
}

return this
