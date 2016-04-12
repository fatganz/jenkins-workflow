#!groovy

stage "develop"
def branchName = env.BRANCH_NAME
String[] pipelineBranches = ['develop', 'master', 'qa']

node {
  checkout scm
  std = load 'ci/scripts/std.groovy'
  print "Current branch: " + std.normalizeBranchName(branchName)
  if(pipelineBranches.contains(branchName)) {
    print "doing production pipeline";
  } else {
    print "doing feature testing pipeline";
  }
}

stage "qa"

stage "production"
