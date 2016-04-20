package com.mapzen.android;

public class TestCallback implements OnMapReadyCallback {
    MapzenMap map;

    @Override public void onMapReady(MapzenMap map) {
        this.map = map;
    }
}
