#!/bin/bash

branch_name=`git rev-parse --abbrev-ref HEAD`

echo "Current branch is '$branch_name'"

if [ "develop" == "$branch_name" ]; then
    echo "You can't execute this script on develop branch!"
    exit 1;
fi

git checkout develop
git merge --no-ff $branch_name
git branch -d $branch_name
git push origin :$branch_name
