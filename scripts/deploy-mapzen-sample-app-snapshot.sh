#!/usr/bin/env bash
#
# Builds mapzen sdk sample app and uploads APK to s3://android.mapzen.com/mapzen-sdk-sample-snapshots/.

./gradlew :samples_mapzen-sample:assembleDebug -PmapzenApiKey=$MAPZEN_API_KEY
s3cmd put samples/mapzen-sample/build/outputs/apk/debug/samples_mapzen-sample-debug.apk s3://android.mapzen.com/mapzen-sdk-sample-latest.apk
s3cmd put samples/mapzen-sample/build/outputs/apk/debug/samples_mapzen-sample-debug.apk s3://android.mapzen.com/mapzen-sdk-sample-snapshots/mapzen-sdk-sample-$CIRCLE_BUILD_NUM.apk
