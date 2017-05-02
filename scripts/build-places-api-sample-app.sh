#!/usr/bin/env bash
#
# Builds mapzen places api sample app

./gradlew :samples_mapzen-place-api-sample:assembleDebug -PmapzenApiKey=$MAPZEN_API_KEY