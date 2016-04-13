#!groovy

def branchName = env.BRANCH_NAME
def pipeline
String[] pipelineBranches = ['master', 'qa']

node {
  checkout scm
  utils = load 'ci/scripts/utils.groovy'
  print "Current branch: " + utils.normalizeBranchName(branchName)
  if('develop' == branchName) {
    pipeline = load 'ci/scripts/production-pipeline.groovy'
  } else if (!pipelineBranches.contains(branchName)) {
    pipeline = load 'ci/scripts/feature-pipeline.groovy'
  }
}

if(pipeline){
  pipeline.go(branchName)
} else {
  print "skipping build for this branch"
}
