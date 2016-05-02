Mapzen SDK Release Checklist
============================

1. Remove `-SNAPSHOT` tag from version name in `gradle.properties` and commit changes.
```bash
VERSION_NAME=1.0.1
```

2. Tag release and push tag to GitHub (ex. v1.0.1).
```bash
$ git tag v1.0.1
$ git push origin v1.0.1
```

3. Build release and deploy to Sonatype staging repository.
```bash
$ ./gradlew clean verify uploadArchives --refresh-dependencies
```

4. Log into [Sonatype staging repository](https://oss.sonatype.org/#stagingRepositories) and promote artifact to production using "Close" then "Release".

5. Update version name and add `-SNAPSHOT` tag for next development cycle. Push changes to GitHub (master).
```bash
VERSION_NAME=1.0.2-SNAPSHOT
```

6. Add release name and notes to GitHub [releases page](https://github.com/mapzen/mapzen-android-sdk/releases). Attach and upload AAR.
