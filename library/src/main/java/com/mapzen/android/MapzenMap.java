package com.mapzen.android;

import com.mapzen.android.model.Marker;
import com.mapzen.android.model.Polygon;
import com.mapzen.android.model.Polyline;
import com.mapzen.tangram.LngLat;
import com.mapzen.tangram.MapController;
import com.mapzen.tangram.MapData;

/**
 * This is the main class of the Mapzen Android API and is the entry point for all methods related
 * to the map. You cannot instantiate a {@link MapzenMap} object directly. Rather you must obtain
 * one from {@link MapFragment#getMapAsync(OnMapReadyCallback)} or
 * {@link MapView#getMapAsync(OnMapReadyCallback)}.
 */
public class MapzenMap {
    private final MapController mapController;
    private final OverlayManager overlayManager;

    /**
     * Creates a new map based on the given {@link MapView} and {@link MapController}.
     */
    MapzenMap(MapController mapController, OverlayManager overlayManager) {
        this.mapController = mapController;
        this.overlayManager = overlayManager;
    }

    /**
     * Provides access to the underlying Tangram {@link MapController}.
     */
    protected MapController getMapController() {
        return mapController;
    }

    /**
     * Provides access to the underlying {@link OverlayManager}.
     */
    protected OverlayManager getOverlayManager() {
        return overlayManager;
    }

    /**
     * Sets map zoom.
     */
    public void setZoom(float zoom) {
        mapController.setZoom(zoom);
    }

    /**
     * Returns map zoom.
     */
    public float getZoom() {
        return mapController.getZoom();
    }

    /**
     * Set map position.
     */
    public void setPosition(LngLat lngLat) {
        mapController.setPosition(lngLat);
    }

    /**
     * Returns map position.
     */
    public LngLat getPosition() {
        return mapController.getPosition();
    }

    /**
     * Enable/disable location tracking layer.
     */
    public void setMyLocationEnabled(boolean enabled) {
        overlayManager.setMyLocationEnabled(enabled);
    }

    /**
     * Adds marker overlay to map.
     */
    public MapData addMarker(Marker marker) {
        return overlayManager.addMarker(marker);
    }

    /**
     * Adds polyline overlay to map.
     */
    public MapData addPolyline(Polyline polyline) {
        return overlayManager.addPolyline(polyline);
    }

    /**
     * Adds polygon overlay to map.
     */
    public MapData addPolygon(Polygon polygon) {
        return overlayManager.addPolygon(polygon);
    }
}
