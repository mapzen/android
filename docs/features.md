# Add features to a map

The Android SDK offers ways to add various feature overlays to a map as markers (points), polygons, and polylines.

## Markers

To add a marker to the map, create a `Marker` object and call `MapzenMap#addMarker(Marker)`.

```java
@Override protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_sample_mapzen);

  MapFragment mapFragment = (MapFragment) getSupportFragmentManager()
      .findFragmentById(R.id.map_fragment);
  mapFragment.getMapAsync(new OnMapReadyCallback() {
    @Override public void onMapReady(MapzenMap map) {
      configureMap(map);
    }
  });
}

private void configureMap(MapzenMap map) {
  map.addMarker(new Marker(-73.9903, 40.74433));
  map.addMarker(new Marker(-73.984770, 40.734807));
  map.addMarker(new Marker(-73.998674, 40.732172));
  map.addMarker(new Marker(-73.996142, 40.741050));
}
```

## Polygons

To add a polygon to the map, create a `Polygon` object and call `MapzenMap#addPolygon(Polygon)`.

```java
@Override protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_sample_mapzen);

  MapFragment mapFragment = (MapFragment) getSupportFragmentManager()
      .findFragmentById(R.id.map_fragment);
  mapFragment.getMapAsync(OnMapReadyCallback() {
    @Override public void onMapReady(MapzenMap map) {
      configureMap(map);
    }
  });
}

private void configureMap(MapzenMap map) {
  Polygon polygon = new Polygon.Builder()
      .add(new LngLat(-73.9903, 40.74433))
      .add(new LngLat(-73.984770, 40.734807))
      .add(new LngLat(-73.998674, 40.732172))
      .add(new LngLat(-73.996142, 40.741050))
      .build();
  map.addPolygon(polygon);
}
```

## Polylines

To add a polyline to the map, create a `Polyline` object and call `MapzenMap#addPolyline(Polyline)`.

```java
@Override protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_sample_mapzen);

  MapFragment mapFragment = (MapFragment) getSupportFragmentManager()
      .findFragmentById(R.id.map_fragment);
  mapFragment.getMapAsync(new OnMapReadyCallback() {
    @Override public void onMapReady(MapzenMap map) {
      configureMap(map);
    }
  });
}

private void configureMap(MapzenMap map) {
  Polyline polyline = new Polyline.Builder()
      .add(new LngLat(-73.9903, 40.74433))
      .add(new LngLat(-73.984770, 40.734807))
      .add(new LngLat(-73.998674, 40.732172))
      .add(new LngLat(-73.996142, 40.741050))
      .build();
  map.addPolyline(polyline);
}
```
