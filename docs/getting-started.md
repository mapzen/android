# Getting started

## 1. Add a map to your layout

A map can be added to a layout using either `MapView` or `MapFragment`.

```xml
<fragment
    android:id="@+id/map_fragment"
    android:name="com.mapzen.android.MapFragment"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

## 2. Sign up for an API key

Sign up for a vector tiles API key from the [Mapzen developer portal](https://mapzen.com/developers). Add your vector tiles API key to your application as an XML string resource.

```xml
<resources>
    <string name="vector_tiles_key">[YOUR_VECTOR_TILES_KEY]</string>
</resources>
```

## 3. Initialize the map

Initialize the map using `getMapAsync(OnMapReadyCallback)`.

```java
MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
mapFragment.getMapAsync(new OnMapReadyCallback() {
  @Override public void onMapReady(MapzenMap map) {
    // Map is ready.
  }
});
```

Your map is now ready to use. `MapzenMap` is your main entry point to interact with the map.

For advanced use (animations, custom styles, etc.) please refer to the [Tangram ES](https://github.com/tangrams/tangram-es) documentation.
