# Mapzen Places

Mapzen Places allows users to select a place on a map via [autocomplete](autocomplete-ui.md) using the [GeoDataApi](geodata-api.md) or by selecting a point of interest (POI) from the map.

## Installation

The Mapzen Places API for Android can be included in any Android app via download, Maven, or Gradle. To get started, download the [latest AAR](http://search.maven.org/remotecontent?filepath=com/mapzen/mapzen-places-api/1.3.0/mapzen-places-api-1.3.0.aar).

### Maven

Include dependency using Maven.

```xml
<dependency>
  <groupId>com.mapzen</groupId>
  <artifactId>mapzen-places-api</artifactId>
  <version>1.3.0</version>
  <type>aar</type>
</dependency>
```

### Gradle

Include dependency using Gradle.

```groovy
repositories {
  mavenCentral()
}

dependencies {
  compile 'com.mapzen:mapzen-places-api:1.3.0'
  ...
}
```

## PlacePicker UI

The Mapzen Places PlacePicker UI combines the [Autocomplete UI](autocomplete-ui.md) with a map to allow selecting a place either via autocomplete or by picking a POI from the map. You can integrate it into your application
using `Intent`s.

<img src="https://s3.amazonaws.com/mapzen-assets/images/mapzen-places-api-android/place_picker.png" width="883" height="776" />


## PlacePicker.IntentBuilder

The PlacePicker UI requires your application to request location permissions so that it can allow
the user to center the map on his/her current location. Also, in the case where a bounds bias is not
specified, the PlacePicker UI will use the device's current location to configure the map's initial
viewport.

Request Location Permissions:
```xml
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
```

If your app targets API 23 or higher you will also need to request and handle runtime permissions.
You can read more about how to set that up [here](https://developer.android.com/training/permissions/requesting.html).

With permissions properly granted you can now begin to construct your `Intent` to launch the PlacePicker UI.

Configure `Intent`:
```java
private static final int PLACE_PICKER_REQUEST = 1;

LatLng southwest = new LatLng(42.80749, -73.14697);
LatLng northeast = new LatLng(44.98423, -71.58691);
LatLngBounds bounds = new LatLngBounds(southwest, northeast);

Intent intent = new PlacePicker.IntentBuilder()
    .setLatLngBounds(bounds)
    .build(this);
startActivityForResult(intent, PLACE_PICKER_REQUEST);
```

Because the intent is started with `Activity#startActivityForResult`, your activity will receive a
callback to `Activity#onActivityResult` when the user has selected a `Place`. To handle what should
happen when a `Place` is selected, implement this method and use the `PlacePicker` static methods to
retrieve information about the selected `Place`.

```java
@Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
  super.onActivityResult(requestCode, resultCode, data);
  if (requestCode == PLACE_PICKER_REQUEST && resultCode == RESULT_OK) {
    Place place = PlacePicker.getPlace(this, data);
    // Update UI with place information

    LatLngBounds bounds = PlacePicker.getLatLngBounds(data);
    // Update UI with bounds
  }
}
```
