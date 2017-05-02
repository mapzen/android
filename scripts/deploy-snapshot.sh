#!/usr/bin/env bash
#
# Builds and uploads snapshot AARs to https://oss.sonatype.org/#stagingRepositories.
#

./gradlew uploadArchives -PsonatypeUsername=$SONATYPE_NEXUS_SNAPSHOTS_USERNAME  \
    -PsonatypePassword=$SONATYPE_NEXUS_SNAPSHOTS_PASSWORD
    