package com.mapzen.android;

import com.mapzen.tangram.LngLat;
import com.mapzen.tangram.MapController;
import com.mapzen.tangram.MapView;

import android.app.Activity;

public class TestMapController extends MapController {

    double latitude = 0;
    double longitude = 0;
    float mapZoom = 0;
    float mapRotation = 0;
    float mapTilt = 0;
    MapController.CameraType cameraType;

    public TestMapController(Activity mainApp, MapView view) {
        super(mainApp, view, null);
    }

    public TestMapController(Activity mainApp, MapView view, String sceneFilePath) {
        super(mainApp, view, sceneFilePath);
    }

    @Override
    public void setMapPosition(double lng, double lat, float duration) {
        longitude = lng;
        latitude = lat;
    }

    @Override
    public LngLat getMapPosition() {
        return new LngLat(longitude, latitude);
    }

    @Override
    public void setMapZoom(float zoom) {
        mapZoom = zoom;
    }

    @Override
    public float getMapZoom() {
        return mapZoom;
    }

    @Override
    public void setMapRotation(float radians) {
        mapRotation = radians;
    }

    @Override
    public float getMapRotation() {
        return mapRotation;
    }

    @Override
    public void setMapTilt(float radians) {
        mapTilt = radians;
    }

    @Override
    public float getMapTilt() {
        return mapTilt;
    }

    @Override
    public void setMapCameraType(CameraType mapCameraType) {
        cameraType = mapCameraType;
    }

    @Override
    public CameraType getMapCameraType() {
        return cameraType;
    }
}
