#!/usr/bin/env bash
#
# Builds and uploads sample apps to S3 on release build only.

if [[ ${PERFORM_RELEASE} ]]
  then
    sudo apt-get update; sudo apt-get install s3cmd
    printf "[default]\naccess_key = $S3_ACCESS_KEY\n secret_key = $S3_SECRET_KEY" > ~/.s3cfg
    scripts/deploy-android-sdk-sample-app.sh
    scripts/deploy-places-api-sample-app.sh
fi
