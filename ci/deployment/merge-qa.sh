#!/bin/bash

#BRANCH_NAME=`git rev-parse --abbrev-ref HEAD`
git config user.email "rkinash+jenkins@rebbix.com"
git config user.name "Jenkins server"

echo "Current branch is '$BRANCH_NAME'"

if [ "develop" == "$BRANCH_NAME" ]; then
    echo "You can't execute this script on develop branch!"
    exit 1;
fi
git fetch --all
git checkout $BRANCH_NAME
git checkout qa
git merge $BRANCH_NAME
git commit -m "Merging branch ${BRANCH_NAME} into QA"
git tag -a some_tag -m "feature-${BUILD_NUMBER}"
git push origin qa
