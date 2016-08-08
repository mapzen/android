#  Mapzen SDK Release Checklist

Steps to build a new release version AAR and deploy to [Maven Central](http://search.maven.org/).

## 1. Build and deploy to staging repository

Build release and deploy to the Sonatype staging repository.
```bash
$ ./gradlew clean release --refresh-dependencies
```

## 2. Promote release artifact

Log into [Sonatype staging repository](https://oss.sonatype.org/#stagingRepositories) and promote artifact to production using "Close" then "Release".

## 3. Documentation

Add release name and notes to GitHub [releases page](https://github.com/mapzen/android/releases). Attach and upload AAR. Update
[installations page](https://github.com/mapzen/android/blob/master/docs/installation.md) to point to the new artifact.
