# Polylines

To add a polyline to the map, create a `Polyline` object and call `MapzenMap#addPolyline(Polyline)`. Keep a reference to the returned `MapData` object and remove the polyline from the map in `Activity#onDestroy()`.

```java
MapFragment mapFragment;
MapData polylineData;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sample_mapzen);

    MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
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
    polylineData = map.addPolyline(polyline);
}

@Override protected void onDestroy() {
    super.onDestroy();
    polylineData.remove();
}
```
