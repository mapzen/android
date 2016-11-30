#!/usr/bin/env bash
#
# Builds sample app and uploads APK to s3://android.mapzen.com/sample-snapshots/.

./gradlew assembleDebug -PapiKey=$API_KEY
s3cmd put sample/build/outputs/apk/mapzen-android-sdk-sample-debug.apk s3://android.mapzen.com/mapzen-android-sdk-sample-latest.apk
s3cmd put sample/build/outputs/apk/mapzen-android-sdk-sample-debug.apk s3://android.mapzen.com/sample-snapshots/mapzen-android-sdk-sample-$CIRCLE_BUILD_NUM.apk
