# Getting started

## 1. Add a map to your layout

A map can be added to a layout using either `MapView` or `MapFragment`.

```xml
<fragment
    android:id="@+id/map_fragment"
    android:name="com.mapzen.android.graphics.MapFragment"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

## 2. Sign up for a Mapzen API key

Sign up for an API key from the [Mapzen developer portal](https://mapzen.com/developers).

### Set API key using a string resource

You can set your Mapzen API key via an XML string resource. Add a file `app/src/main/res/values/mapzen.xml` and copy the following code but replace `[YOUR_MAPZEN_API_KEY]` with your real Mapzen API key from the developer portal.

```xml
<resources>
    <string name="mapzen_api_key">[YOUR_MAPZEN_API_KEY]</string>
</resources>
```

### Set API key using `MapzenManager` class

Alternatively you can set your Mapzen API key via the `MapzenManager` class. Just make sure you call the following method prior to calling MapView#getMapAsyc(...) or creating an instance of `MapzenSearch` or `MapzenRouter`.

Of course you'll want to replace `[YOUR_MAPZEN_API_KEY]` with your real key just like above.

```java
MapzenManager.instance(context).setApiKey("[YOUR_MAPZEN_API_KEY]");
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
