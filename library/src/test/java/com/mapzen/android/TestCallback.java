package com.mapzen.android;

import com.mapzen.tangram.MapController;

public class TestCallback implements MapView.OnMapReadyCallback {
    MapController map;

    @Override public void onMapReady(MapController mapController) {
        map = mapController;
    }
}
