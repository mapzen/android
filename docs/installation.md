# Installation

The Mapzen SDK can be included in any Android app via download, Maven, or Gradle. It includes everything you need to get started. No other mapping or location services dependencies are required (really).

## Download

Download the [latest AAR](http://search.maven.org/remotecontent?filepath=com/mapzen/mapzen-android-sdk/1.2.1/mapzen-android-sdk-1.2.1.aar).

## Maven

Include dependency using Maven.

```xml
<dependency>
  <groupId>com.mapzen</groupId>
  <artifactId>mapzen-android-sdk</artifactId>
  <version>1.2.1</version>
  <type>aar</type>
</dependency>
```

## Gradle

Include dependency using Gradle.

```groovy
repositories {
  mavenCentral()
}

dependencies {
  compile 'com.mapzen:mapzen-android-sdk:1.2.1'
  ...
}
```
