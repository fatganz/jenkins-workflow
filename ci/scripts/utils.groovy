#!groovy

def githubCredentialId = "13867ba0-8cea-46d9-aeb3-9f9bee66a1a8";

def normalizeBranchName(String name) {
    return name
      .tr('/_','-')
      .toLowerCase()
}

def runTests() {
  sh "tests/run.sh"
}

return this
