#!/usr/bin/env bash
#
# Builds mapzen android sdk sample app

./gradlew :samples_mapzen-sample:assembleDebug -PmapzenApiKey=$MAPZEN_API_KEY
