#!groovy

utils = load 'ci/scripts/utils.groovy'
branchName = utils.normalizeBranchName(env.BRANCH_NAME)

def go(String branchName) {
  print "working with $branchName"
  print "doing feature testing pipeline";
  stage "testing"

  node {
    sh "docker build -f app/Dockerfile.preview -t toastme/app-test:snapshot ."
  }

  def tstImg = docker.image("toastme/app-test:snapshot")

  stage "preview"
  node {
    try{
      tstImg.stop()
    } catch(e) {
    }
    tstImg.run("--name ${branchName} -v ${pwd()}/app:/opt/app")
  }
  stage "complete"
  input message: "Feature complte?", ok: "Yes"

  // node {
  //   sshagent (credentials: ['5cfc7cca-6168-4848-b3ef-9aa628a780bd']) {
  //     sh 'ci/deployment/merge-feature.sh'
  //   }
  // }
  node {
    tstImg.stop();
  }
}

def hostIp(container) {
  sh "docker inspect -f '{{ .NetworkSettings.IPAddress }}' ${container.id} > hostIp_${env.BUILD_TAG}"
  readFile("hostIp_${env.BUILD_TAG}").trim()
}

return this
