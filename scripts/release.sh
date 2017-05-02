#!/usr/bin/env bash
#
# runs the gradle release plugin
#

if ([ -n ${RELEASE_VERSION_NUMBER} ] && [ -n ${NEW_VERSION_NUMBER} ]); then
  echo "Releasing"
  git config --global user.email "accounts+mapnerd@mapzen.com"
  git config --global user.name "Mapzen Developer"
  ./gradlew release -Prelease.useAutomaticVersion=true \
    -Prelease.releaseVersion=$RELEASE_VERSION_NUMBER \
    -Prelease.newVersion=$NEW_VERSION_NUMBER
else
  echo "RELEASE_VERSION_NUMBER & NEW_VERSION_NUMBER not specified, skipping release."
fi
