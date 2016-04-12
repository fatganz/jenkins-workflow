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
    checkout([
      $class: 'GitSCM',
      branches: [[name: env.BRANCH_NAME]],
      doGenerateSubmoduleConfigurations: false,
      extensions: [],
      submoduleCfg: [],
      userRemoteConfigs:
      [[url: 'https://github.com/fatganz/jenkins-workflow.git']]]
    )
    withCredentials([[$class: 'FileBinding', credentialsId: utils.githubCredentialId, variable: 'GIT_SSH']]) {
      //merges feature and deletes feature branch
      sh "env"
    }
  }
}

return this
