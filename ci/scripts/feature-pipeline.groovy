#!groovy

utils = load 'ci/scripts/utils.groovy'

def go(String branchName) {
  def tstImg
  print "Faeture testing $branchName"
  stage "testing"
  node {
    checkout scm
    sh "docker run --rm -v ${pwd()}/app:/app composer/composer:latest install"
    sh "docker run -v ${pwd()}/app:/app phpunit/phpunit --bootstrap vendor/autoload.php tests/"
    withEnv(["COMPOSE_VIRTUAL_HOST=${branchName}.qa.toastme.internal", "BUILD_TAG=${env.BUILD_TAG}"]){
      sh "docker-compose -p $branchName -f docker-compose-feature.yml up -d --build"
    }
    // sh "docker build -f app/Dockerfile.preview -t toastme/app-test:$branchName-snapshot ."
    // tstImg = docker.image("toastme/app-test:$branchName-snapshot")
  }
  stage "preview"
  node {
    // try{
    //   print "Stopping ${branchName} container..."
    //   stopContainer(branchName)
    // } catch(e) {
    //   print "${branchName} container is not running!"
    // }
    // c = tstImg.run("--name ${branchName} -e VIRTUAL_HOST=${branchName}.qa.toastme.internal")
  }
  stage "complete"
  input message: "Feature complte?", ok: "Yes"

  // node {
  //   sshagent (credentials: ['5cfc7cca-6168-4848-b3ef-9aa628a780bd']) {
  //     sh 'ci/deployment/merge-feature.sh'
  //   }
  // }
  node {
    withEnv(["COMPOSE_VIRTUAL_HOST=${branchName}.qa.toastme.internal"]){
      sh "docker-compose -f docker-compose-feature.yml -p $branchName stop"
    }
    // stopContainer(branchName)
  }
}

def stopContainer(containerId) {
  sh "docker stop ${containerId} && docker rm -f ${containerId}"
}

def hostIp(container) {
  sh "docker inspect -f '{{ .NetworkSettings.IPAddress }}' ${container.id} > hostIp_${env.BUILD_TAG}"
  readFile("hostIp_${env.BUILD_TAG}").trim()
}

return this
