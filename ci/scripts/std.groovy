#!groovy

def normalizeBranchName(String name) {
    return name.replace("/","-").replace("_","-")
}

return this
