#!/usr/bin/env bash
#
# Builds mapzen places api sample app and uploads APK to s3://android.mapzen.com/mapzen-places-api-sample-snapshots/.

./gradlew :samples_mapzen-place-api-sample:assembleDebug -PmapzenApiKey=$MAPZEN_API_KEY
s3cmd put samples/mapzen-places-api-sample/build/outputs/apk/samples_mapzen-places-api-sample-debug.apk s3://android.mapzen.com/mapzen-places-api-sample-latest.apk
s3cmd put samples/mapzen-places-api-sample/build/outputs/apk/samples_mapzen-places-api-sample-debug.apk s3://android.mapzen.com/places-api-sample-releases/mapzen-places-api-sample-$CIRCLE_TAG.apk
