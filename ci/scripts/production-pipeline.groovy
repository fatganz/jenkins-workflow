#!groovy
utils = load 'ci/scripts/utils.groovy'

def go(String branchName) {
  stage "testing"
  node {
    utils.runTests()
  }

  stage "qa"
  input message: "Okay to merge into QA?", ok: "Yes"
  node {
    docker.image('php:5.6-cli').inside {
      sh 'php app/app.php'
    }
    sshagent (credentials: ['5cfc7cca-6168-4848-b3ef-9aa628a780bd']) {
      sh 'ci/deployment/merge-qa.sh'
      sh "ci/deployment/deploy-qa.sh"
    }
  }

  stage "production"
  input message: "Deploy QA to production?", ok: "Yes"
  node {
    sshagent (credentials: ['5cfc7cca-6168-4848-b3ef-9aa628a780bd']) {
      sh "ci/deployment/deploy-prod.sh"
    }
  }

}

return this
