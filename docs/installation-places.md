# Installation

The Mapzen SDK can be included in any Android app via download, Maven, or Gradle. It includes everything you need to get started. No other mapping or location services dependencies are required (really).

## Download

Download the [latest AAR](http://search.maven.org/remotecontent?filepath=com/mapzen/mapzen-places-api/0.0.1/mapzen-places-api-0.0.1.aar).

## Maven

Include dependency using Maven.

```xml
<dependency>
  <groupId>com.mapzen</groupId>
  <artifactId>mapzen-places-api</artifactId>
  <version>0.0.1</version>
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
  compile 'com.mapzen:mapzen-places-api:0.0.1'
  ...
}
```
