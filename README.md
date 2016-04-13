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
