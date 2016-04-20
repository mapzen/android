package com.mapzen.android;

import com.mapzen.tangram.MapController;

/**
 * This is the main class of the Mapzen Android API and is the entry point for all methods related
 * to the map. You cannot instantiate a {@link MapzenMap} object directly. Rather you must obtain
 * one from {@link MapFragment#getMapAsync(MapView.OnMapReadyCallback)} or
 * {@link MapView#getMapAsync(MapView.OnMapReadyCallback)}.
 */
public class MapzenMap {
    private final MapController mapController;

    /**
     * Creates a new map based on the given {@link MapController}.
     */
    MapzenMap(MapController mapController) {
        this.mapController = mapController;
    }

    /**
     * Provides access to the underlying Tangram {@link MapController}.
     */
    public MapController getMapController() {
        return mapController;
    }
}
