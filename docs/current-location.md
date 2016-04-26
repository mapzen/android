# Current location

`OverlayManager` is responsible for managing all interactions with the map where something is drawn over the map's content.

When `OverlayManager#setMyLocationEnabled(true)` is called, an icon is displayed to allow centering the map on the user's current location upon click. While enabled, the `OverlayManager` will continue to update the latest location but will only update the map when the button is clicked. When `OverlayManager#setMyLocationEnabled(false)` is called, this icon is hidden and location updates are stopped.

```java
MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
mapFragment.getMapAsync(new OnMapReadyCallback() {
    @Override public void onMapReady(MapzenMap map) {
        map.setMyLocationEnabled(true);
    }
});
```

For advanced and more custom use of location services, refer to the [LOST](https://github.com/mapzen/LOST) documentation.
