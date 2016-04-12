#!groovy

utils = load 'ci/scripts/utils.groovy'

def go(String branchName) {
  print "working with $branchName"
  print "doing feature testing pipeline";
  stage "testing"
  node {
      utils.runTests()
  }
  stage "preview"
  node {
    sh "ci/deployment/deploy-ft.sh"
  }

  stage "complete"
  input message: "Feature complte?", ok: "Yes"

  node {
    sshagent (credentials: ['5cfc7cca-6168-4848-b3ef-9aa628a780bd']) {
      sh "git fetch --all"
      sh "git checkout ${env.BRANCH_NAME}"
      sh 'ci/deployment/merge-feature.sh'
      sh 'ci/deployment/merge-feature.sh'
    }
  }
}

return this
