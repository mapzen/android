# Mapzen Android SDK
[![Circle CI](https://circleci.com/gh/mapzen/android.svg?style=svg&circle-token=3191e9499a851a9a3869a72ee3c55d4e29133ebc)](https://circleci.com/gh/mapzen/android)

The Mapzen Android SDK is a thin wrapper that packages up everything you need to use Mapzen services in your Android applications. It also simplifies setup, installation, API key management and generally makes your life better.

## Usage
Everything you need to get going using the Mapzen SDK

### Set up
- [Installation](https://github.com/mapzen/android/blob/master/docs/installation.md)
- [Getting started](https://github.com/mapzen/android/blob/master/docs/getting-started.md)

### Interacting with the map
- [Position, rotation, and zoom](https://github.com/mapzen/android/blob/master/docs/basic-functions.md)
- [Current location](https://github.com/mapzen/android/blob/master/docs/current-location.md)
- [Markers](https://github.com/mapzen/android/blob/master/docs/markers.md)
- [Polylines](https://github.com/mapzen/android/blob/master/docs/polyline.md)
- [Polygons](https://github.com/mapzen/android/blob/master/docs/polygon.md)
- [Switching styles](https://github.com/mapzen/android/blob/master/docs/styles.md)
- [Gesture responders](https://github.com/mapzen/android/blob/master/docs/gesture-responders.md)

### Search, routing, and location services
- [Search](https://github.com/mapzen/android/blob/master/docs/search.md)
- [Routing](https://github.com/mapzen/android/blob/master/docs/turn-by-turn.md)
- [Location services](https://github.com/mapzen/android/blob/master/docs/location-services.md)

## Greater than the sum of its parts
The Mapzen Android SDK incorporates several stand-alone libraries for map rendering, location tracking, routing, and search through the following projects:

- [Tangram ES](https://github.com/tangrams/tangram-es/)- 2D and 3D map renderer using OpenGL ES
- [Pelias](https://github.com/pelias/pelias-android-sdk)- Mapzen Search client side wrapper and Android UI components
- [On the Road](https://github.com/mapzen/on-the-road)- Mapzen Turn-by-Turn wrapper and other navigation utilities
- [Lost](https://github.com/mapzen/lost)- Drop-in replacement for Google Play services Location APIs

## Sample
For a working example please refer to the SDK [sample app](https://github.com/mapzen/android/tree/master/samples/mapzen-android-sdk-sample).

# Mapzen Places API
[![Circle CI](https://circleci.com/gh/mapzen/android.svg?style=svg&circle-token=3191e9499a851a9a3869a72ee3c55d4e29133ebc)](https://circleci.com/gh/mapzen/android)

The Mapzen Places API is a drop in replacement for the Google Places API.

## Usage
Everything you need to get going using the Mapzen Places API

### Set up
- [Installation](https://github.com/mapzen/android/blob/master/docs/installation-places.md)
- [Getting started](https://github.com/mapzen/android/blob/master/docs/getting-started-places.md)

### UI Components
- [Autocomplete UI](https://github.com/mapzen/android/blob/master/docs/autocomplete-ui.md)
- [PlacePicker UI](https://github.com/mapzen/android/blob/master/docs/place-picker-ui.md)

### Data Components
- [GeoDataApi](https://github.com/mapzen/android/blob/master/docs/geodata-api.md)
- [PlaceDetectionApi](https://github.com/mapzen/android/blob/master/docs/place-detection-api.md)

## Sample
For a working example please refer to the Places [sample app](https://github.com/mapzen/android/tree/master/samples/mapzen-places-api-sample).

# Eraser Map

An [open source](https://github.com/mapzen/eraser-map) privacy-focused reference application built entirely using Mapzen services. With Eraser Map you can see a canonical implementation of mapping, search, and turn-by-turn navigation.

Beta builds of Eraser Map (plus the SDK demo apps and other science projects) are available on the [Mapzen Android download page](http://android.mapzen.com/).
