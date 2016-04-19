# Getting started with the map

## 1. Add a map layout

A map can be added to a layout using either `MapView` or `MapFragment`.

```xml
<fragment
    android:id="@+id/map_fragment"
    android:name="com.mapzen.android.MapFragment"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

## 2. Register an API key

Sign up for a vector tiles API key from the [Mapzen developer portal](https://mapzen.com/developers). Add your vector tiles API key to your application as an XML string resource.

```xml
<resources>
    <string name="vector_tiles_key">[YOUR_VECTOR_TILES_KEY]</string>
</resources>
```

## 3. Initialize the map

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