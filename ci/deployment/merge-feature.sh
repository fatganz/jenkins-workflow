#!/bin/bash

#BRANCH_NAME=`git rev-parse --abbrev-ref HEAD`

echo "Current branch is '$BRANCH_NAME'"

if [ "develop" == "$BRANCH_NAME" ]; then
    echo "You can't execute this script on develop branch!"
    exit 1;
fi

git checkout develop
git merge $BRANCH_NAME
git commit -m "Merging branch ${BRANCH_NAME}"
git tag -a some_tag -m "feature-${BUILD_NUMBER}"
git branch -d $BRANCH_NAME
git push origin :$BRANCH_NAME
