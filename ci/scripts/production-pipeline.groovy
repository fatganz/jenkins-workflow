#!groovy
utils = load 'ci/scripts/utils.groovy'

def go(String branchName) {
  stage "testing"
  node {
    utils.runTests()
  }

  stage "qa"
  node {
    sh "deployment/deploy-qa.sh"
  }

  stage "production"
  node {
    sh "deployment/deploy-prod.sh"
  }

}

return this
