# Mapzen Android SDK
[![Circle CI](https://circleci.com/gh/mapzen/android.svg?style=svg&circle-token=3191e9499a851a9a3869a72ee3c55d4e29133ebc)](https://circleci.com/gh/mapzen/android)

The Mapzen Android SDK is a thin wrapper that packages up everything you need to use Mapzen services in your Android applications. It also handles API key management, interaction between the components, UI elemets, and assorted other things

## Current functionality:
Currently the Mapzen SDK supports map rendering and location tracking through the following projects:

- [L.O.S.T](https://github.com/mapzen/lost)- Our drop-in replacement for Google Play Services Location APIs
- [Tangram](https://github.com/tangrams/tangram-es/)- Our 2D and 3D map renderer using OpenGL ES

## What's coming up:
We will be incorporating all of Mapzen’s services into the SDK through the following projects:

- [On the road](https://github.com/mapzen/on-the-road)- The Mapzen Turn-by-Turn wrapper and other routing utilities.
- [Pelias](https://github.com/pelias/pelias-android-sdk)- The Mapzen Search wrapper

# Usage

## Getting started

### 1. Add a map layout

A map can be added to a layout using either `MapView` or `MapFragment`.

```xml
<fragment
    android:id="@+id/map_fragment"
    android:name="com.mapzen.android.MapFragment"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

### 2. Register an API key

Sign up for a vector tiles API key from the [Mapzen developer portal](https://mapzen.com/developers). Add your vector tiles API key to your application as an XML string resource.

```xml
<resources>
    <string name="vector_tiles_key">[YOUR_VECTOR_TILES_KEY]</string>
</resources>
```

### 3. Initialize the map

Initialize the map using `getMapAsync(MapView.OnMapReadyCallback)`.

```java
mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
mapFragment.getMapAsync(new MapView.OnMapReadyCallback() {
  @Override public void onMapReady(MapController mapController) {
    // Map is ready.
  }
});
```

Your map is now ready to use. `MapContoller` is your main entry point to interact with the map. See below for some common use cases like adding a marker or drawing a line.

For advanced use (animations, custom styles, etc.) please refer to the [Tangram ES](https://github.com/tangrams/tangram-es) documentation.

## Set position, rotation, and zoom level

Coming soon.

## Current location

Coming soon.

## Markers

Coming soon.

## Polylines

Coming soon.

# Install

## Download

Download the [latest AAR](https://oss.sonatype.org/content/repositories/snapshots/com/mapzen/mapzen-android-sdk/0.1.0-SNAPSHOT/).

## Maven

Include dependency using Maven.

```xml
<dependency>
  <groupId>com.mapzen</groupId>
  <artifactId>mapzen-android-sdk</artifactId>
  <version>0.1.0-SNAPSHOT</version>
</dependency>
```

## Gradle

Include dependency using Gradle.

```groovy
compile 'com.mapzen:mapzen-android-sdk:0.1.0-SNAPSHOT'
```
