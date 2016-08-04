# Installation

The Mapzen SDK can be included in any Android app via download, Maven, or Gradle. It includes everything you need to get started. No other mapping or location services dependencies are required (really).

If you would like to add search or routing components check out the [Pelias Android SDK](https://github.com/pelias/pelias-android-sdk) and [On the Road](https://github.com/mapzen/on-the-road) stand-alone libraries.

## Download

Download the [latest AAR](http://search.maven.org/remotecontent?filepath=com/mapzen/mapzen-android-sdk/1.0.7/mapzen-android-sdk-1.0.7.aar).

## Maven

Include dependency using Maven.

```xml
<dependency>
  <groupId>com.mapzen</groupId>
  <artifactId>mapzen-android-sdk</artifactId>
  <version>1.0.7</version>
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
  compile 'com.mapzen:mapzen-android-sdk:1.0.7'
  ...
}
```
