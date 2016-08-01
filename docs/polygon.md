# Polygons

To add a polygon to the map, create a `Polygon` object and call `MapzenMap#addPolygon(Polygon)`.

```java
MapFragment mapFragment;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sample_mapzen);

    MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
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
    map.addPolyline(polygon);
}

```
