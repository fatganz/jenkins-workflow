#!groovy

def normalizeBranchName(String name) {
    return name
      .tr('/_','-')
      .toLowerCase()​
}

return this
