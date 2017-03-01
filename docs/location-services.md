# Location Services

Mapzen offers a basic location option for recentering the map on the user's current location, as described below. More advanced location services such as getting the last known location, receiving updates, creating geofences and more are also available for use. These features are powered by the [Location Open Source Tracker (Lost)](https://github.com/mapzen/lost) project.

That means you can use our SDK on any Android device regardless of whether it supports Google Play services. And we never track a user's personal location data. Ever. Learn more about Lost and its features [here](http://mapzen.github.io/lost/).

## Current location

When `MapzenMap#setMyLocationEnabled(true)` is called, an icon is displayed to allow centering the map on the user's current location upon click. While enabled, the map will continue to fetch the latest location but will only update the map when the button is clicked. When `MapzenMap#setMyLocationEnabled(false)` is called, this icon is hidden and location updates are stopped. To conserve resources, `MapzenMap#setMyLocationEnabled(false)` should be called in the activity or fragment's `onPause()` method and should be re-enabled in `onResume()`.

```java

private MapzenMap map;
private boolean enableLocationOnResume = false;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sample_mapzen);

    final MapFragment mapFragment =
        (MapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
    mapFragment.getMapAsync(new OnMapReadyCallback() {
        @Override public void onMapReady(MapzenMap map) {
            BasicMapzenActivity.this.map = map;
            map.setMyLocationEnabled(true);
        }
    });
}

@Override protected void onPause() {
    super.onPause();
    if (map.isMyLocationEnabled()) {
        map.setMyLocationEnabled(false);
        enableLocationOnResume = true;
    }
}

@Override protected void onResume() {
    super.onResume();
    if (enableLocationOnResume) {
        map.setMyLocationEnabled(true);
    }
}
```

For advanced and more custom use of location services, refer to the [LOST](https://github.com/mapzen/LOST) documentation.
