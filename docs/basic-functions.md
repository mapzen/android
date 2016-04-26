# Position, rotation, and zoom

Use the `MapzenMap` instance returned by `getMapAsync(OnMapReadyCallback)` to set the position, rotation, and zoom level.

```java
MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
mapFragment.getMapAsync(new OnMapReadyCallback() {
    @Override public void onMapReady(MapzenMap map) {
        map.setPosition(new LngLat(-73.9903, 40.74433));
        map.setRotation(0f);
        map.setZoom(17f); 
    }
});
```

For advanced map controls (animation, etc.) you can subclass `MapzenMap` and access the Tangram `MapController` directly.
