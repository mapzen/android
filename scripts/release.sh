#!/usr/bin/env bash
#
# runs the gradle release plugin
#

if [ -z ${RELEASE_VERSION_NUMBER} ]
  then
    echo "[ERROR] RELEASE_VERSION_NUMBER not set"
    exit 1
fi

if [ -z ${$NEW_VERSION_NUMBER} ]
  then
    echo "[ERROR] $NEW_VERSION_NUMBER not set"
    exit 1
fi

./gradle release -Prelease.useAutomaticVersion=true \
  -Prelease.releaseVersion=$RELEASE_VERSION_NUMBER \
  -Prelease.newVersion=$NEW_VERSION_NUMBER
