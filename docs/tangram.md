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

Use the `MapController` to set the position, rotation, and zoom level. You can set each of these values with optional animation or easing.

```java
MapFragment mapFragment;
MapController mapController;
OverlayManager overlayManager;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sample_mapzen);

    mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
    mapFragment.getMapAsync(new MapView.OnMapReadyCallback() {
        @Override public void onMapReady(MapController mapController) {
            BasicMapzenActivity.this.mapController = mapController;
            configureMap();
        }
    });
}

private void configureMap() {
    LngLat position = new LngLat(-73.9903, 40.74433);
    mapController.setPosition(position);
    mapController.setPosition(position, 300);
    mapController.setPosition(position, 300, MapController.EaseType.SINE);

    mapController.setRotation(0);
    mapController.setRotation(0, 300);
    mapController.setRotation(0, 300, MapController.EaseType.LINEAR);

    mapController.setZoom(17f);
    mapController.setZoom(17f, 300);
    mapController.setZoom(17f, 300, MapController.EaseType.CUBIC);
    
}
```


## Current location

`OverlayManager` is responsible for managing all interactions with the map where something is drawn over the map's content.

When `OverlayManager#setMyLocationEnabled(true)` is called, an icon is displayed to allow centering the map on the user's current location upon click. While enabled, the `OverlayManager` will continue to update the latest location but will only update the map when the button is clicked. When `OverlayManager#setMyLocationEnabled(false)` is called, this icon is hidden and location updates are stopped.

```java
mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
mapFragment.getMapAsync(new MapView.OnMapReadyCallback() {
    @Override public void onMapReady(MapController mapController) {
        OverlayManager mapManager = mapFragment.getOverlayManager();
        mapManager.setMyLocationEnabled(true);
    }
});
```

For advanced and more custom use of location services, refer to the [LOST](https://github.com/mapzen/LOST) documentation.

## Markers

To add a marker to the map, create a `Marker` object and call `OverlayManager#addMarker`. Keep a reference to the returned `MapData` object and remove your markers from the map in `Activity#onDestroy`.

```java
MapFragment mapFragment;
OverlayManager overlayManager;
MapData marker;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sample_mapzen);

    mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
    mapFragment.getMapAsync(new com.mapzen.android.MapView.OnMapReadyCallback() {
        @Override public void onMapReady(MapController mapController) {
            configureMap();
        }
    });
}

private void configureMap() {
    overlayManager = mapFragment.getOverlayManager();
    marker = overlayManager.addMarker(new Marker(-73.9903, 40.74433));
    overlayManager.addMarker(new Marker(-73.984770, 40.734807));
    overlayManager.addMarker(new Marker(-73.998674, 40.732172));
    overlayManager.addMarker(new Marker(-73.996142, 40.741050));
}

@Override protected void onDestroy() {
    super.onDestroy();
    marker.remove();
}

```

## Polylines

To add a polyline to the map, create a `Polyline` object and call `OverlayManager#addPolyline`. Keep a reference to the returned `MapData` object and remove the polyline from the map in `Activity#onDestroy`.

```java
MapFragment mapFragment;
OverlayManager overlayManager;
MapData polylineData;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sample_mapzen);

    mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
    mapFragment.getMapAsync(new com.mapzen.android.MapView.OnMapReadyCallback() {
        @Override public void onMapReady(MapController mapController) {
            configureMap();
        }
    });
}

private void configureMap() {
    overlayManager = mapFragment.getOverlayManager();
    Polyline polyline = new Polyline.Builder()
            .add(new LngLat(-73.9903, 40.74433))
            .add(new LngLat(-73.984770, 40.734807))
            .add(new LngLat(-73.998674, 40.732172))
            .add(new LngLat(-73.996142, 40.741050))
            .build();
    polylineData = overlayManager.addPolyline(polyline);
}

@Override protected void onDestroy() {
    super.onDestroy();
    polylineData.remove();
}
```

## Polygons

To add a polygon to the map, create a `Polygon` object and call `OverlayManager#addPolygon`. Keep a reference to the returned `MapData` object and remove the polyline from the map in `Activity#onDestroy`.

```java
MapFragment mapFragment;
OverlayManager overlayManager;
MapData polygonData;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sample_mapzen);

    mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
    mapFragment.getMapAsync(new com.mapzen.android.MapView.OnMapReadyCallback() {
        @Override public void onMapReady(MapController mapController) {
            configureMap();
        }
    });
}

private void configureMap() {
    overlayManager = mapFragment.getOverlayManager();
    Polygon polygon = new Polygon.Builder()
            .add(new LngLat(-73.9903, 40.74433))
            .add(new LngLat(-73.984770, 40.734807))
            .add(new LngLat(-73.998674, 40.732172))
            .add(new LngLat(-73.996142, 40.741050))
            .build();
    polygonData = overlayManager.addPolyline(polygon);
}

@Override protected void onDestroy() {
    super.onDestroy();
    polygonData.remove();
}
```

## Switching styles?

Coming soon.
