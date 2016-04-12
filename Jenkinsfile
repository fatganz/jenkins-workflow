#!groovy

stage "develop"

node {
  checkout scm
  std = load 'ci/scripts/std.groovy'
  print "Current branch: " + std.normalizeBranchName(env.BRANCH_NAME)
}

stage "qa"

stage "production"
