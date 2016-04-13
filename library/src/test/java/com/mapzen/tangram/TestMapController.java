package com.mapzen.tangram;

import org.mockito.Mockito;

import android.content.Context;
import android.content.res.Resources;

import static org.mockito.Mockito.when;

public class TestMapController extends MapController {

    private double latitude = 0;
    private double longitude = 0;
    private float mapZoom = 0;
    private float mapRotation = 0;
    private float mapTilt = 0;
    private MapController.CameraType cameraType;

    public TestMapController() {
        super(getMockContext(), "");
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

    private static Context getMockContext() {
        final Context context = Mockito.mock(Context.class);
        when(context.getResources()).thenReturn(Mockito.mock(Resources.class));
        return context;
    }
}
