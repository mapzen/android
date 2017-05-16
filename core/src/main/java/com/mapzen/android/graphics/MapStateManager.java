package com.mapzen.android.graphics;

import com.mapzen.android.graphics.model.BubbleWrapStyle;
import com.mapzen.android.graphics.model.CameraType;
import com.mapzen.android.graphics.model.MapStyle;
import com.mapzen.tangram.LngLat;

/**
 * Manages parameters around {@link MapzenMap}'s state so that it can be restore upon orientation
 * change.
 */
class MapStateManager {

  private boolean persistMapState = true;
  private LngLat position = new LngLat(0, 0);
  private MapStyle mapStyle = new BubbleWrapStyle();
  private float zoom = 0;
  private float rotation = 0;
  private float tilt = 0;
  private CameraType cameraType = CameraType.ISOMETRIC;
  private boolean transitOverlayEnabled = false;
  private boolean bikeOverlayEnabled = false;

  public void setPersistMapState(boolean persistMapState) {
    this.persistMapState = persistMapState;
  }

  public boolean getPersistMapState() {
    return persistMapState;
  }

  public void setPosition(LngLat position) {
    this.position = position;
  }

  public LngLat getPosition() {
    return this.position;
  }

  public void setMapStyle(MapStyle mapStyle) {
    this.mapStyle = mapStyle;
  }

  public MapStyle getMapStyle() {
    return this.mapStyle;
  }

  public void setZoom(float zoom) {
    this.zoom = zoom;
  }

  public float getZoom() {
    return this.zoom;
  }

  public void setRotation(float rotation) {
    this.rotation = rotation;
  }

  public float getRotation() {
    return this.rotation;
  }

  public void setTilt(float tilt) {
    this.tilt = tilt;
  }

  public float getTilt() {
    return this.tilt;
  }

  public void setCameraType(CameraType cameraType) {
    this.cameraType = cameraType;
  }

  public CameraType getCameraType() {
    return this.cameraType;
  }

  public void setTransitOverlayEnabled(boolean transitOverlayEnabled) {
    this.transitOverlayEnabled = transitOverlayEnabled;
  }

  public boolean isTransitOverlayEnabled() {
    return this.transitOverlayEnabled;
  }

  public void setBikeOverlayEnabled(boolean bikeOverlayEnabled) {
    this.bikeOverlayEnabled = bikeOverlayEnabled;
  }

  public boolean isBikeOverlayEnabled() {
    return this.bikeOverlayEnabled;
  }
}
