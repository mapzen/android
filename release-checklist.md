#  Mapzen SDK Release Checklist

Steps to build a new release version AAR and deploy to [Maven Central](http://search.maven.org/).

## 1. Build and deploy to staging repository

Ensure that you have set a CIRCLE_TOKEN environment variable.

Run the deploy script with the release and new version specified like so:
```bash
$ ./scripts/deploy.sh 1.3.3 1.3.4-SNAPSHOT
```

Wait for [Circle CI](https://circleci.com/gh/mapzen/android) build and deploy the release artifacts to the Sonatype staging repository.

There should be one bundle that contains (3) individual artifacts: `mapzen-core`, `mapzen-android-sdk`, and `mapzen-places-api`.

## 2. Promote release artifact

Log into [Sonatype staging repository](https://oss.sonatype.org/#stagingRepositories) and promote artifact to production using "Close" then "Release".

As a side-effect of the automated release process two artifacts will be uploaded to the Sonatype staging repository. One which is properly signed using the GPG key and one which is not.

The unsigned artifact will fail signature verification and should be dropped. The signed artifact should be released to Maven Central.

## 3. Documentation

Add release name and notes to GitHub [releases page](https://github.com/mapzen/android/releases). Update
[installations page](https://github.com/mapzen/android/blob/master/docs/installation.md) to point to the new artifact.

Download AARs and Javadoc JARs from Maven Central for `mapzen-core`, `mapzen-android-sdk`, and `mapzen-places-api` modules. Attach to release notes.

Unzip Javadoc JARs in the proper folders of the `gh-pages` branch. Commit and push to GitHub to publish to https://mapzen.github.io/android/.
