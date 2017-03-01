# Mapzen Android SDK Sample Application

The Mapzen Android SDK sample application provides working examples of many features of the SDK including map rendering, search, routing, and location services.

## Get a Mapzen API key
Before using the sample app, sign up for a Mapzen API key on the [Mapzen developer portal](https://mapzen.com/developers).

## Add your Mapzen API key to mapzen.xml
Add your Mapzen API key to the sample app by replacing the dummy text value of `mapzen_api_key` with your API key in [mapzen.xml](https://github.com/mapzen/android/blob/master/samples/mapzen-android-sdk-sample/src/main/res/values/mapzen.xml).

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
  <string name="mapzen_api_key">[YOUR_MAPZEN_API_KEY]</string>
</resources>
```

## Build and install

Run the following commands from the root folder of the project to build and install the app.

```bash
git submodule update --init --recursive
./gradlew installSdkDemo
```
