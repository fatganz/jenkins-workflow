#!/bin/bash

#BRANCH_NAME=`git rev-parse --abbrev-ref HEAD`
git config user.email "rkinash+jenkins@rebbix.com"
git config user.name "Jenkins server"

echo "Current branch is '$BRANCH_NAME'"

if [ "qa" == "$BRANCH_NAME" ]; then
    echo "You can't execute this script on QA branch!"
    exit 1;
fi
git fetch --all
git checkout $BRANCH_NAME
git pull
git tag -a "feature-${BUILD_NUMBER}" -m "feature-${BUILD_NUMBER}"
git checkout qa
git merge $BRANCH_NAME
git commit -m "Merging branch ${BRANCH_NAME} into QA"
git push origin qa
git push origin "feature-${BUILD_NUMBER}"
