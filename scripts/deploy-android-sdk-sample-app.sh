#!/usr/bin/env bash
#
# Builds mapzen android sdk sample app and uploads APK to s3://android.mapzen.com/mapzen-android-sdk-sample-snapshots/.

./gradlew :samples_mapzen-android-sdk-sample:assembleDebug -PmapzenApiKey=$MAPZEN_API_KEY
s3cmd put samples/mapzen-android-sdk-sample/build/outputs/apk/samples_mapzen-android-sdk-sample-debug.apk s3://android.mapzen.com/mapzen-android-sdk-sample-latest.apk
s3cmd put samples/mapzen-android-sdk-sample/build/outputs/apk/samples_mapzen-android-sdk-sample-debug.apk s3://android.mapzen.com/mapzen-android-sdk-sample-releases/mapzen-android-sdk-sample-$CIRCLE_TAG.apk
