#  Mapzen SDK Release Checklist

Steps to build a new release version AAR and deploy to [Maven Central](http://search.maven.org/).

## 1. Build and deploy to staging repository

Run the gradle-release plugin to update version number, build locally, and push a new tag to GitHub.
```bash
$ ./gradlew clean release --refresh-dependencies
```

Wait for [Circle CI](https://circleci.com/gh/mapzen/android) to pick up the new tag, build and deploy the release artifacts to the Sonatype staging repository.

There should be three (3) bundles deployed: `mapzen-core`, `mapzen-android-sdk`, and `mapzen-places-api`.

## 2. Promote release artifact

Log into [Sonatype staging repository](https://oss.sonatype.org/#stagingRepositories) and promote artifact to production using "Close" then "Release".

## 3. Documentation

Add release name and notes to GitHub [releases page](https://github.com/mapzen/android/releases). Attach and upload AAR. Update
[installations page](https://github.com/mapzen/android/blob/master/docs/installation.md) to point to the new artifact.
