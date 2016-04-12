#!groovy
utils = load 'ci/scripts/utils.groovy'

def go(String branchName) {
  stage "testing"
  node {
    utils.runTests()
  }

  stage "qa"
  node {
    sh "ci/deployment/deploy-qa.sh"
  }

  stage "production"
  node {
    sh "ci/deployment/deploy-prod.sh"
  }

}

return this
