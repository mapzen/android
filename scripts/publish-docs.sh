#!/usr/bin/env bash
#
# Triggers mapzen docs build to publish to https://mapzen.com/documentation/.

if [[ ${PERFORM_RELEASE} ]]
  then
    pip install 'Circle-Tickler == 1.0.1'
    tickle-circle mapzen documentation master $CIRCLE_TOKEN
fi
