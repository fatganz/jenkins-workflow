#!groovy

def branchName = env.BRANCH_NAME
def pipeline
String[] pipelineBranches = ['develop', 'master', 'qa']

node {
  checkout scm
  utils = load 'ci/scripts/utils.groovy'
  print "Current branch: " + utils.normalizeBranchName(branchName)
  if(pipelineBranches.contains(branchName)) {
    pipeline = load 'ci/scripts/production-pipeline.groovy'
  } else {
    pipeline = load 'ci/scripts/feature-pipeline.groovy'
  }
}

pipeline.go(branchName)
