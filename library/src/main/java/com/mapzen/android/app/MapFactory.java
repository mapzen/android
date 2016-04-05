package com.mapzen.android.app;

import com.mapzen.tangram.MapController;
import com.mapzen.tangram.MapView;

import android.app.Activity;

class MapFactory {
    MapController getMap(Activity activity, MapView mapView) {
        return new MapController(activity, mapView);
    }
}
