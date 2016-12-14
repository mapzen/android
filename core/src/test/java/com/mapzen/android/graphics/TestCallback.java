package com.mapzen.android.graphics;

public class TestCallback implements OnMapReadyCallback {
  MapzenMap map;

  @Override public void onMapReady(MapzenMap map) {
    this.map = map;
  }
}
