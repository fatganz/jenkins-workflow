#!groovy
utils = load 'ci/scripts/utils.groovy'

def go(String branchName) {
  stage "testing"
  node {
    utils.writeVersionPhpFile('app/web', env.BUILD_TAG);
    sh "docker build -f app/Dockerfile.preview -t toastme/app-test:develop-snapshot ."
    def tstImg = docker.image("toastme/app-test:develop-snapshot")
    tstImg.inside(){
      sh "cd app/ && bin/phpunit tests/"
    }
    tstImg.withRun(){ c ->
      sh "curl http://${branchName}.qa.toastme.internal:8080/hello/test/user"
    }
  }

  stage "qa"
  input message: "Okay to merge into QA?", ok: "Yes"
  node {
    def pcImg = docker.build("toastme/app:${env.BUILD_TAG}", 'app')
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
