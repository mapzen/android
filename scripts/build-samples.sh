#!/usr/bin/env bash
#
# Builds and uploads sample apps to S3 on release build only.

scripts/build-android-sdk-sample-app.sh
scripts/build-places-api-sample-app.sh
scripts/build-mapzen-sample-app.sh