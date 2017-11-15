#!/usr/bin/env bash
#
# Builds and uploads sample apps to S3 on release build only.

scripts/install-s3.sh
scripts/deploy-android-sdk-sample-app.sh
scripts/deploy-places-api-sample-app.sh
scripts/deploy-mapzen-sample-app.sh
