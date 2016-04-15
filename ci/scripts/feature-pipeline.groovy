#!groovy

utils = load 'ci/scripts/utils.groovy'
def c;

def go(String branchName) {
  print "Faeture testing $branchName"
  stage "testing"
  node {
    sh "docker build -f app/Dockerfile.preview -t toastme/app-test:snapshot ."
  }

  def tstImg = docker.image("toastme/app-test:snapshot")

  stage "preview"
  node {
    try{
      print "Stopping ${branchName} container..."
      tstImg.stop()
    } catch(e) {
      print "${branchName} container is not running!"
    }
    c = tstImg.run("--name ${branchName} -v ${pwd()}/app:/opt/app")
  }
  stage "complete"
  input message: "Feature complte?", ok: "Yes"

  // node {
  //   sshagent (credentials: ['5cfc7cca-6168-4848-b3ef-9aa628a780bd']) {
  //     sh 'ci/deployment/merge-feature.sh'
  //   }
  // }
  node {
    c.stop();
  }
}

def hostIp(container) {
  sh "docker inspect -f '{{ .NetworkSettings.IPAddress }}' ${container.id} > hostIp_${env.BUILD_TAG}"
  readFile("hostIp_${env.BUILD_TAG}").trim()
}

return this
