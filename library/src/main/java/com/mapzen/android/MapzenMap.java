package com.mapzen.android;

import com.mapzen.android.model.EaseType;
import com.mapzen.android.model.Marker;
import com.mapzen.android.model.Polygon;
import com.mapzen.android.model.Polyline;
import com.mapzen.tangram.LngLat;
import com.mapzen.android.model.MapStyle;
import com.mapzen.tangram.MapController;
import com.mapzen.tangram.MapData;
import com.mapzen.tangram.TouchInput;

import java.util.HashMap;

/**
 * This is the main class of the Mapzen Android API and is the entry point for all methods related
 * to the map. You cannot instantiate a {@link MapzenMap} object directly. Rather you must obtain
 * one from {@link MapFragment#getMapAsync(OnMapReadyCallback)} or
 * {@link MapView#getMapAsync(OnMapReadyCallback)}.
 */
public class MapzenMap {
    private final MapController mapController;
    private final OverlayManager overlayManager;

    private TouchInput.TapResponder tapResponder;
    private TouchInput.DoubleTapResponder doubleTapResponder;
    private TouchInput.LongPressResponder longPressResponder;
    private TouchInput.PanResponder panResponder;
    private TouchInput.RotateResponder rotateResponder;
    private TouchInput.ScaleResponder scaleResponder;
    private TouchInput.ShoveResponder shoveResponder;

    private static final HashMap<EaseType, MapController.EaseType>
        EASE_TYPE_TO_MAP_CONTROLLER_EASE_TYPE = new HashMap();
    static {
        EASE_TYPE_TO_MAP_CONTROLLER_EASE_TYPE.put(EaseType.LINEAR, MapController.EaseType.LINEAR);
        EASE_TYPE_TO_MAP_CONTROLLER_EASE_TYPE.put(EaseType.CUBIC, MapController.EaseType.CUBIC);
        EASE_TYPE_TO_MAP_CONTROLLER_EASE_TYPE.put(EaseType.QUINT, MapController.EaseType.QUINT);
        EASE_TYPE_TO_MAP_CONTROLLER_EASE_TYPE.put(EaseType.SINE, MapController.EaseType.SINE);
    }

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
    public MapController getMapController() {
        return mapController;
    }

    /**
     * Provides access to the underlying {@link OverlayManager}.
     */
    public OverlayManager getOverlayManager() {
        return overlayManager;
    }

    /**
     * Sets the map's underlying stylesheet.
     * @param mapStyle
     */
    public void setStyle(MapStyle mapStyle) {
        mapController.loadSceneFile(MapStyleToSceneFile.MAP_STYLE_TO_SCENE_FILE.get(mapStyle));
    }

    /**
     * Sets map zoom without animation.
     */
    public void setZoom(float zoom) {
        mapController.setZoom(zoom);
    }

    /**
     * Sets map zoom with animation and default, {@code EaseType.CUBIC} easing.
     * @param zoom map zoom level
     * @param duration animation duration in millis
     */
    public void setZoom(float zoom, int duration) {
        mapController.setZoom(zoom, duration);
    }

    /**
     * Sets map zoom with animation and easing.
     * @param zoom map zoom level
     * @param duration animation duration in millis
     * @param easeType map ease type
     */
    public void setZoom(float zoom, int duration, EaseType easeType) {
        mapController.setZoom(zoom, duration, EASE_TYPE_TO_MAP_CONTROLLER_EASE_TYPE.get(easeType));
    }


    /**
     * Returns map zoom.
     */
    public float getZoom() {
        return mapController.getZoom();
    }

    /**
     * Set map position without animation.
     */
    public void setPosition(LngLat lngLat) {
        mapController.setPosition(lngLat);
    }

    /**
     * Set map position with animation and default {@code EaseType.CUBIC} easing.
     * @param lngLat position to center map on
     * @param duration animation duration in millis
     */
    public void setPosition(LngLat lngLat, int duration) {
        mapController.setPosition(lngLat, duration);
    }

    /**
     * Set map position with animation and easing.
     * @param lngLat position to center map on
     * @param duration animation duration in millis
     * @param easeType map ease type
     */
    public void setPosition(LngLat lngLat, int duration, EaseType easeType) {
        mapController.setPosition(lngLat, duration, EASE_TYPE_TO_MAP_CONTROLLER_EASE_TYPE.get(
                easeType));
    }

    /**
     * Returns map position.
     */
    public LngLat getPosition() {
        return mapController.getPosition();
    }

    /**
     * Set map rotation in radians without animation.
     */
    public void setRotation(float radians) {
        mapController.setRotation(radians);
    }

    /**
     * Set map rotation in radians with animation and default {@code EaseType.CUBIC} easing.
     * @param radians map rotation
     * @param duration animation duration in millis
     */
    public void setRotation(float radians, int duration) {
        mapController.setRotation(radians, duration);
    }

    /**
     * Set map rotation in radians with animation and custom easing.
     * @param radians map rotation
     * @param duration animation duration in millis
     * @param easeType map ease type
     */
    public void setRotation(float radians, int duration, EaseType easeType) {
        mapController.setRotation(radians, duration, EASE_TYPE_TO_MAP_CONTROLLER_EASE_TYPE.get(
                easeType));
    }

    /**
     * Get map rotation in radians.
     */
    public float getRotation() {
        return mapController.getRotation();
    }

    /**
     * Set map tilt in radians without animation.
     * @param radians tilt in radians
     */
    public void setTilt(float radians) {
        mapController.setTilt(radians);
    }

    /**
     * Set map tilt in radians with animation and default {@code EaseType.CUBIC} easing.
     * @param radians tilt in radians
     * @param duration duration in millis
     */
    public void setTilt(float radians, int duration) {
        mapController.setTilt(radians, duration);
    }

    /**
     * Set map tilt in radians with animation and custom easing.
     * @param radians tilt in radians
     * @param duration duration in millis
     * @param easeType map ease type
     */
    public void setTilt(float radians, int duration, EaseType easeType) {
        mapController.setTilt(radians, duration, EASE_TYPE_TO_MAP_CONTROLLER_EASE_TYPE.get(
                easeType));
    }

    /**
     * Get map tilt in radians.
     */
    public float getTilt() {
        return mapController.getTilt();
    }

    /**
     * When enabled, shows icon on map to allow centering map on current location. While
     * enabled, the user's current location will be updated in the background. When disabled, the
     * icon is hidden from the map.
     */
    public void setMyLocationEnabled(boolean enabled) {
        overlayManager.setMyLocationEnabled(enabled);
    }

    /**
     * Adds marker overlay to map. Returned {@link MapData} should be removed from map
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

    /**
     * Find the geographic coordinates corresponding to the given position on screen
     * @param screenX Pixels from the left edge of the screen
     * @param screenY Pixels from the top edge of the screen
     * @return LngLat corresponding to the given point
     */
    public LngLat coordinatesAtScreenPosition(double screenX, double screenY) {
        return mapController.coordinatesAtScreenPosition(screenX, screenY);
    }

    /**
     * Set tap responder for tap gestures on map.
     */
    public void setTapResponder(TouchInput.TapResponder tapResponder) {
        this.tapResponder = tapResponder;
        mapController.setTapResponder(this.tapResponder);
    }

    /**
     * Get the map's tap responder
     */
    public TouchInput.TapResponder getTapResponder() {
        return tapResponder;
    }

    public void setDoubleTapResponder(TouchInput.DoubleTapResponder doubleTapResponder) {
        this.doubleTapResponder = doubleTapResponder;
        mapController.setDoubleTapResponder(this.doubleTapResponder);
    }

    public TouchInput.DoubleTapResponder getDoubleTapResponder() {
        return doubleTapResponder;
    }


    public void setLongPressResponder(TouchInput.LongPressResponder longPressResponder) {
        this.longPressResponder = longPressResponder;
        mapController.setLongPressResponder(this.longPressResponder);
    }

    public TouchInput.LongPressResponder getLongPressResponder() {
        return longPressResponder;
    }

    public void setPanResponder(TouchInput.PanResponder panResponder) {
        this.panResponder = panResponder;
        mapController.setPanResponder(this.panResponder);
    }

    public TouchInput.PanResponder getPanResponder() {
        return panResponder;
    }

    public void setRotateResponder(TouchInput.RotateResponder rotateResponder) {
        this.rotateResponder = rotateResponder;
        mapController.setRotateResponder(this.rotateResponder);
    }

    public TouchInput.RotateResponder getRotateResponder() {
        return rotateResponder;
    }

    public void setScaleResponder(TouchInput.ScaleResponder scaleResponder) {
        this.scaleResponder = scaleResponder;
        mapController.setScaleResponder(scaleResponder);
    }

    public TouchInput.ScaleResponder getScaleResponder() {
        return scaleResponder;
    }

    public void setShoveResponder(TouchInput.ShoveResponder shoveResponder) {
        this.shoveResponder = shoveResponder;
        mapController.setShoveResponder(this.shoveResponder);
    }

    public TouchInput.ShoveResponder getShoveResponder() {
        return shoveResponder;
    }

}
