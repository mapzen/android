package com.mapzen.android.graphics;

import com.mapzen.android.graphics.model.BubbleWrapStyle;
import com.mapzen.android.graphics.model.CameraType;
import com.mapzen.android.graphics.model.RefillStyle;
import com.mapzen.tangram.LngLat;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MapStateManagerTest {

  MapStateManager stateManager = new MapStateManager();

  @Test public void getPersistMapState_shouldDefaultToTrue() {
    assertThat(stateManager.getPersistMapState()).isTrue();
  }

  @Test public void setPersistMapState_shouldUpdateMapState() {
    stateManager.setPersistMapState(true);
    assertThat(stateManager.getPersistMapState()).isTrue();
  }

  @Test public void getPosition_shouldDefaultToNullIsland() {
    assertThat(stateManager.getPosition().latitude).isEqualTo(0);
    assertThat(stateManager.getPosition().longitude).isEqualTo(0);
  }

  @Test public void setPosition_shouldUpdatePosition() {
    stateManager.setPosition(new LngLat(70, 40));
    assertThat(stateManager.getPosition().latitude).isEqualTo(40);
    assertThat(stateManager.getPosition().longitude).isEqualTo(70);
  }

  @Test public void getMapStyle_shouldDefaultToBubbleWrap() {
    assertThat(stateManager.getMapStyle().getClass()).isEqualTo(BubbleWrapStyle.class);
  }

  @Test public void setMapStyle_shouldUpdateMapStyle() {
    stateManager.setMapStyle(new RefillStyle());
    assertThat(stateManager.getMapStyle().getClass()).isEqualTo(RefillStyle.class);
  }

  @Test public void getZoom_shouldDefaultToZero() {
    assertThat(stateManager.getZoom()).isEqualTo(0);
  }

  @Test public void setZoom_shouldUpdateZoom() {
    stateManager.setZoom(18);
    assertThat(stateManager.getZoom()).isEqualTo(18);
  }

  @Test public void getRotation_shouldDefaultToZero() {
    assertThat(stateManager.getRotation()).isEqualTo(0);
  }

  @Test public void setRotation_shouldUpdateZoom() {
    stateManager.setRotation(90);
    assertThat(stateManager.getRotation()).isEqualTo(90);
  }

  @Test public void getTilt_shouldDefaultToZero() {
    assertThat(stateManager.getTilt()).isEqualTo(0);
  }

  @Test public void setTilt_shouldUpdateZoom() {
    stateManager.setTilt(90);
    assertThat(stateManager.getTilt()).isEqualTo(90);
  }

  @Test public void getCameraType_shouldDefaultToIsometric() {
    assertThat(stateManager.getCameraType()).isEqualTo(CameraType.ISOMETRIC);
  }

  @Test public void setCameraType_shouldUpdateCameraType() {
    stateManager.setCameraType(CameraType.PERSPECTIVE);
    assertThat(stateManager.getCameraType()).isEqualTo(CameraType.PERSPECTIVE);
  }

  @Test public void isTransitOverlayEnabled_shouldDefaultToFalse() {
    assertThat(stateManager.isTransitOverlayEnabled()).isEqualTo(false);
  }

  @Test public void setTransitOverlayEnabled_shouldUpdateTransitOverlayEnabled() {
    stateManager.setTransitOverlayEnabled(true);
    assertThat(stateManager.isTransitOverlayEnabled()).isEqualTo(true);
  }

  @Test public void isBikeOverlayEnabled_shouldDefaultToFalse() {
    assertThat(stateManager.isBikeOverlayEnabled()).isEqualTo(false);
  }

  @Test public void setBikeOverlayEnabled_shouldUpdateBikeOverlayEnabled() {
    stateManager.setBikeOverlayEnabled(true);
    assertThat(stateManager.isBikeOverlayEnabled()).isEqualTo(true);
  }

  @Test public void isPathOverlayEnabled_shouldDefaultToTrue() {
    assertThat(stateManager.isPathOverlayEnabled()).isEqualTo(true);
  }

  @Test public void setPathOverlayEnabled_shouldUpdatePathOverlayEnabled() {
    stateManager.setPathOverlayEnabled(true);
    assertThat(stateManager.isPathOverlayEnabled()).isEqualTo(true);
  }
}
