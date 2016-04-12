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
      sh "git pull origin"
      checkout([
        $class: 'GitSCM',
        branches: [[name: env.BRANCH_NAME]],
        doGenerateSubmoduleConfigurations: false,
        extensions: [],
        submoduleCfg: [],
        userRemoteConfigs:
        [[url: 'git@github.com:fatganz/jenkins-workflow.git']]]
      )
      sh 'ci/deployment/merge-feature.sh'
    }
  }
}

return this
