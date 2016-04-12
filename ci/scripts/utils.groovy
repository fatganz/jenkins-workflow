#!groovy

def normalizeBranchName(String name) {
    return name
      .tr('/_','-')
      .toLowerCase()
}

def runTests() {
  sh "tests/run.sh"
}

return this
