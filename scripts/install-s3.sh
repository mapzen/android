#!/usr/bin/env bash
#
# Builds and uploads sample apps to S3 on release build only.

sudo apt-get update; sudo apt-get install s3cmd
printf "[default]\naccess_key = $S3_ACCESS_KEY\n secret_key = $S3_SECRET_KEY" > ~/.s3cfg