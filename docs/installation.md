# Installation

The Mapzen Android SDK and Mapzen Places API can be included in any Android app via download, Maven, or Gradle. It includes everything you need to get started. No other mapping or location services dependencies are required.

## Download

- [Mapzen SDK](http://search.maven.org/remotecontent?filepath=com/mapzen/mapzen-android-sdk/1.3.1/mapzen-android-sdk-1.3.1.aar)
- [Mapzen Places SDK](http://search.maven.org/remotecontent?filepath=com/mapzen/mapzen-places-api/1.3.1/mapzen-places-api-1.3.1.aar), more on installing the Places SDK is [here](places.md)

## Maven

Include dependency using Maven.

```xml
<dependency>
  <groupId>com.mapzen</groupId>
  <artifactId>mapzen-android-sdk</artifactId>
  <version>1.3.1</version>
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
  compile 'com.mapzen:mapzen-android-sdk:1.3.1'
  ...
}
```
