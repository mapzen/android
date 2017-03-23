package com.mapzen.android.graphics;

import com.mapzen.tangram.LngLat;
import com.mapzen.tangram.MapController;
import com.mapzen.tangram.MapData;
import com.mapzen.tangram.Marker;
import com.mapzen.tangram.MarkerPickResult;
import com.mapzen.tangram.SceneUpdate;
import com.mapzen.tangram.TestMapData;

import android.content.Context;
import android.content.res.Resources;
import android.opengl.GLSurfaceView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestMapController extends MapController {

  private double latitude = 0;
  private double longitude = 0;
  private float mapZoom = 0;
  private float mapRotation = 0;
  private float mapTilt = 0;
  private FeaturePickListener featurePickListener;
  private MarkerPickListener markerPickListener;
  private SceneUpdate sceneUpdate;

  public TestMapController() {
    super(new GLSurfaceView(getMockContext()));
  }

  @Override public void setPosition(LngLat position) {
    longitude = position.longitude;
    latitude = position.latitude;
  }

  @Override public void setPositionEased(LngLat lngLat, int duration) {
    longitude = lngLat.longitude;
    latitude = lngLat.latitude;
  }

  @Override public LngLat getPosition() {
    return new LngLat(longitude, latitude);
  }

  @Override public void setZoom(float zoom) {
    mapZoom = zoom;
  }

  @Override public float getZoom() {
    return mapZoom;
  }

  @Override public void setRotation(float radians) {
    mapRotation = radians;
  }

  @Override public float getRotation() {
    return mapRotation;
  }

  @Override public void setTilt(float radians) {
    mapTilt = radians;
  }

  @Override public float getTilt() {
    return mapTilt;
  }

  @Override public MapData addDataLayer(String name) {
    return new TestMapData(name);
  }

  @Override public void requestRender() {
  }

  @Override public void setFeaturePickListener(FeaturePickListener listener) {
    featurePickListener = listener;
  }

  @Override public void pickFeature(float posX, float posY) {
    featurePickListener.onFeaturePick(null, posX, posY);
  }

  @Override public void setMarkerPickListener(MarkerPickListener listener) {
    markerPickListener = listener;
  }

  @Override public void pickMarker(float posX, float posY) {
    MarkerPickResult markerPickResult = mock(MarkerPickResult.class);
    when(markerPickResult.getMarker()).thenReturn(mock(Marker.class));
    if (markerPickListener != null) {
      markerPickListener.onMarkerPick(markerPickResult, posX, posY);
    }
  }

  @Override public void queueSceneUpdate(SceneUpdate sceneUpdate) {
    this.sceneUpdate = sceneUpdate;
  }

  public SceneUpdate getSceneUpdate() {
    return sceneUpdate;
  }

  private static Context getMockContext() {
    final Context context = mock(Context.class);
    when(context.getResources()).thenReturn(mock(Resources.class));
    return context;
  }
}
