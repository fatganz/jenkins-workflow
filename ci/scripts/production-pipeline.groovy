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
    utils.writeVersionPhpFile('app/web', env.BUILD_TAG);
    def pcImg = docker.build("toastme/app:${env.BUILD_TAG}", 'app')
    pcImg.withRun(){ c ->
      sh "curl http://${hostIp(c)}:8080/hello/test/user"
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

def hostIp(container) {
  sh "docker inspect -f '{{ .NetworkSettings.IPAddress }}' ${container.id} > hostIp_${env.BUILD_TAG}"
  readFile("hostIp_${env.BUILD_TAG}").trim()
}

return this
