package com.mapzen.android;

import com.mapzen.tangram.MapController;
import com.mapzen.tangram.MapView;

import android.app.Activity;

/**
 * Class responsible for constructing new {@link MapView} components.
 */
class MapFactory {
    /**
     * Gets the underlying {@link MapController} that is tied to the specified {@link MapView}.
     * @param activity context for the controlling {@link MapView}.
     * @param mapView UI component that will contain the map.
     * @return a new {@link MapController} tied to the given view.
     */
    MapController getMap(Activity activity, MapView mapView) {
        return new MapController(activity, mapView);
    }
}
