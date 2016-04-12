#!groovy

stage "develop"
def branchName = env.BRANCH_NAME
def pipeline
String[] pipelineBranches = ['develop', 'master', 'qa']

node {
  checkout scm
  std = load 'ci/scripts/std.groovy'
  print "Current branch: " + std.normalizeBranchName(branchName)
  if(pipelineBranches.contains(branchName)) {
    pipeline = load 'ci/scripts/production-pipeline.groovy'
  } else {
    pipeline = load 'ci/scripts/feature-pipeline.groovy'
  }
}

pipeline.go(branchName)
