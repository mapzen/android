package com.mapzen.android.graphics;

import com.mapzen.android.graphics.model.CameraType;
import com.mapzen.android.graphics.model.EaseType;
import com.mapzen.android.graphics.model.Marker;
import com.mapzen.android.graphics.model.Polygon;
import com.mapzen.android.graphics.model.Polyline;
import com.mapzen.tangram.LngLat;
import com.mapzen.tangram.MapController;
import com.mapzen.tangram.TouchInput;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class) @SuppressStaticInitializationFor("com.mapzen.tangram.MapController")
public class MapzenMapTest {

  private TestMapView mapView;
  private MapzenMap map;
  private MapController mapController;
  private OverlayManager overlayManager;

  @Before public void setUp() throws Exception {
    mapView = new TestMapView();
    mapController = Mockito.mock(TestMapController.class);
    Mockito.doCallRealMethod().when(mapController)
        .setFeaturePickListener(any(MapController.FeaturePickListener.class));
    Mockito.doCallRealMethod().when(mapController).pickFeature(anyFloat(), anyFloat());
    overlayManager = mock(OverlayManager.class);
    MapStateManager mapStateManager = new MapStateManager();
    map = new MapzenMap(mapView, mapController, overlayManager, mapStateManager);
  }

  @Test public void shouldNotBeNull() throws Exception {
    assertThat(map).isNotNull();
  }

  @Test public void getMapController_shouldNotBeNull() throws Exception {
    assertThat(map.getMapController()).isNotNull();
  }

  @Test public void getOverlayManager_shouldNotBeNull() throws Exception {
    assertThat(map.getOverlayManager()).isNotNull();
  }

  @Test public void setZoom_shouldInvokeMapController() throws Exception {
    map.setZoom(15);
    verify(mapController).setZoom(15);
  }

  @Test public void setZoomAnimated_shouldInvokeMapControllerAnimated() throws Exception {
    map.setZoom(15, 300);
    verify(mapController).setZoomEased(15, 300, MapController.EaseType.CUBIC);
  }

  @Test public void setZoomAnimatedEased_shouldInvokedMapControllerAnimatedEased()
      throws Exception {
    map.setZoom(15, 300, EaseType.CUBIC);
    verify(mapController).setZoomEased(15, 300, MapController.EaseType.CUBIC);
  }

  @Test public void getZoom_shouldInvokeMapController() throws Exception {
    when(mapController.getZoom()).thenReturn(15f);
    assertThat(map.getZoom()).isEqualTo(15);
  }

  @Test public void setPosition_shouldInvokeMapController() throws Exception {
    LngLat lngLat = new LngLat(70, 40);
    map.setPosition(lngLat);
    verify(mapController).setPosition(lngLat);
  }

  @Test public void setPositionAnimated_shouldInvokeMapControllerAnimated() throws Exception {
    LngLat lngLat = new LngLat(70, 40);
    map.setPosition(lngLat, 300);
    verify(mapController).setPositionEased(lngLat, 300, MapController.EaseType.CUBIC);
  }

  @Test public void setPositionAnimatedEased_shouldInvokeMapControllerAnimatedEased()
      throws Exception {
    LngLat lngLat = new LngLat(70, 40);
    map.setPosition(lngLat, 300, EaseType.CUBIC);
    verify(mapController).setPositionEased(lngLat, 300, MapController.EaseType.CUBIC);
  }

  @Test public void getPosition_shouldInvokeMapController() throws Exception {
    LngLat lngLat = new LngLat(70, 40);
    when(mapController.getPosition()).thenReturn(lngLat);
    assertThat(map.getPosition()).isEqualTo(lngLat);
  }

  @Test public void setRotation_shouldInvokeMapController() throws Exception {
    map.setRotation(3.14f);
    verify(mapController).setRotation(3.14f);
  }

  @Test public void setRotationAnimated_shouldInvokeMapControllerAnimated() throws Exception {
    map.setRotation(3.14f, 300);
    verify(mapController).setRotationEased(3.14f, 300, MapController.EaseType.CUBIC);
  }

  @Test public void setRotationAnimatedEased_shouldInvokeMapControllerAnimatedEased()
      throws Exception {
    map.setRotation(3.14f, 300, EaseType.CUBIC);
    verify(mapController).setRotationEased(3.14f, 300, MapController.EaseType.CUBIC);
  }

  @Test public void getRotation_shouldInvokeMapController() throws Exception {
    when(mapController.getRotation()).thenReturn(3.14f);
    assertThat(map.getRotation()).isEqualTo(3.14f);
  }

  @Test public void setTilt_shouldInvokeMapController() throws Exception {
    map.setTilt(3.14f);
    verify(mapController).setTilt(3.14f);
  }

  @Test public void setTiltAnimated_shouldInvokeMapControllerAnimated() throws Exception {
    map.setTilt(3.14f, 300);
    verify(mapController).setTiltEased(3.14f, 300, MapController.EaseType.CUBIC);
  }

  @Test public void setTiltAnimatedEased_shouldInvokeMapControllerAnimatedEased() throws Exception {
    map.setTilt(3.14f, 300, EaseType.CUBIC);
    verify(mapController).setTiltEased(3.14f, 300, MapController.EaseType.CUBIC);
  }

  @Test public void getTilt_shouldInvokeMapController() throws Exception {
    when(mapController.getTilt()).thenReturn(3.14f);
    assertThat(map.getTilt()).isEqualTo(3.14f);
  }

  @Test public void setCameraType_shouldInvokeMapController() {
    map.setCameraType(CameraType.FLAT);
    verify(mapController).setCameraType(MapController.CameraType.FLAT);
  }

  @Test public void getCameraType_shouldInvokeMapController() {
    map.getCameraType();
    verify(mapController).getCameraType();
  }

  @Test public void setMyLocationEnabled_shouldInvokeOverlayManager() throws Exception {
    map.setMyLocationEnabled(true);
    verify(overlayManager).setMyLocationEnabled(true);
  }

  @Test public void isMyLocationEnabled_shouldInvokeOverlayManager() throws Exception {
    map.isMyLocationEnabled();
    verify(overlayManager).isMyLocationEnabled();
  }

  @Test public void addMarker_shouldInvokeOverlayManager() throws Exception {
    Marker marker = new Marker(0, 0);
    map.addMarker(marker);
    verify(overlayManager).addMarker(marker);
  }

  @Test public void addPolyline_shouldInvokeOverlayManager() throws Exception {
    LngLat lngLat = new LngLat(70, 40);
    Polyline polyline = new Polyline.Builder().add(lngLat).add(lngLat).build();
    map.addPolyline(polyline);
    verify(overlayManager).addPolyline(polyline);
  }

  @Test public void addPolygon_shouldInvokeOverlayManager() throws Exception {
    LngLat lngLat = new LngLat(70, 40);
    Polygon polygon = new Polygon.Builder().add(lngLat).add(lngLat).build();
    map.addPolygon(polygon);
    verify(overlayManager).addPolygon(polygon);
  }

  @Test public void screenPositionToLngLat_shouldInvokeMapController() throws Exception {
    PointF position = new PointF(4.f, 3.f);
    map.screenPositionToLngLat(position);
    verify(mapController).screenPositionToLngLat(position);
  }

  @Test public void lngLatToScreenPosition_shouldInvokeMapController() {
    LngLat lngLat = new LngLat(70, 40);
    map.lngLatToScreenPosition(lngLat);
    verify(mapController).lngLatToScreenPosition(lngLat);
  }

  @Test public void setTapResponder_shouldInvokeMapController() {
    TestTapResponder tapResponder = new TestTapResponder();
    map.setTapResponder(tapResponder);
    TouchInput.TapResponder internalTapResponder =
        Whitebox.getInternalState(map, "internalTapResponder");
    verify(mapController).setTapResponder(internalTapResponder);

    assertThat(tapResponder.singleTapUp).isFalse();
    internalTapResponder.onSingleTapUp(0, 0);
    assertThat(tapResponder.singleTapUp).isTrue();

    assertThat(tapResponder.singleTapConfirmed).isFalse();
    internalTapResponder.onSingleTapConfirmed(0, 0);
    assertThat(tapResponder.singleTapConfirmed).isTrue();
  }

  @Test public void getTapResponder_shouldNotBeNull() {
    TestTapResponder tapResponder = new TestTapResponder();
    map.setTapResponder(tapResponder);
    assertThat(map.getTapResponder()).isNotNull();
  }

  @Test public void setDoubleTapResponder_shouldInvokeMapController() {
    TestDoubleTapResponder doubleTapResponder = new TestDoubleTapResponder();
    map.setDoubleTapResponder(doubleTapResponder);
    verify(mapController).setDoubleTapResponder(doubleTapResponder);
  }

  @Test public void getDoubleTapResponder_shouldNotBeNull() {
    TestDoubleTapResponder doubleTapResponder = new TestDoubleTapResponder();
    map.setDoubleTapResponder(doubleTapResponder);
    assertThat(map.getDoubleTapResponder()).isNotNull();
  }

  @Test public void setLongPressResponder_shouldInvokeMapController() {
    TestLongPressResponder longPressResponder = new TestLongPressResponder();
    map.setLongPressResponder(longPressResponder);
    verify(mapController).setLongPressResponder(longPressResponder);
  }

  @Test public void getLongPressResponder_shouldNotBeNull() {
    TestLongPressResponder longPressResponder = new TestLongPressResponder();
    map.setLongPressResponder(longPressResponder);
    assertThat(map.getLongPressResponder()).isNotNull();
  }

  @Test public void setPanResponder_shouldNotInvokeMapController() {
    TestPanResponder panResponder = new TestPanResponder();
    map.setPanResponder(panResponder);
    verify(mapController, never()).setPanResponder(panResponder);
  }

  @Test public void getPanResponder_shouldNotBeNull() {
    TestPanResponder panResponder = new TestPanResponder();
    map.setPanResponder(panResponder);
    assertThat(map.getPanResponder()).isNotNull();
  }

  @Test public void onPan_shouldInvokeExternalPanResponder() throws Exception {
    TestPanResponder panResponder = new TestPanResponder();
    map.setPanResponder(panResponder);
    map.internalPanResponder.onPan(1f, 2f, 3f, 4f);
    assertThat(panResponder.startX).isEqualTo(1f);
    assertThat(panResponder.startY).isEqualTo(2f);
    assertThat(panResponder.endX).isEqualTo(3f);
    assertThat(panResponder.endY).isEqualTo(4f);
  }

  @Test public void onFling_shouldInvokeExternalPanResponder() throws Exception {
    TestPanResponder panResponder = new TestPanResponder();
    map.setPanResponder(panResponder);
    map.internalPanResponder.onFling(1f, 2f, 3f, 4f);
    assertThat(panResponder.posX).isEqualTo(1f);
    assertThat(panResponder.posY).isEqualTo(2f);
    assertThat(panResponder.velocityX).isEqualTo(3f);
    assertThat(panResponder.velocityY).isEqualTo(4f);
  }

  @Test public void onPan_shouldInvokeOverlayManager() throws Exception {
    map.internalPanResponder.onPan(1f, 2f, 3f, 4f);
    verify(overlayManager).onPan(1f, 2f, 3f, 4f);
  }

  @Test public void onFling_shouldInvokeOverlayManager() throws Exception {
    map.internalPanResponder.onFling(1f, 2f, 3f, 4f);
    verify(overlayManager).onFling(1f, 2f, 3f, 4f);
  }

  @Test public void setRotateResponder_shouldInvokeMapController() {
    TestRotateResponder rotateResponder = new TestRotateResponder();
    map.setRotateResponder(rotateResponder);
    verify(mapController).setRotateResponder(rotateResponder);
  }

  @Test public void getRotateResponder_shouldNotBeNull() {
    TestRotateResponder rotateResponder = new TestRotateResponder();
    map.setRotateResponder(rotateResponder);
    assertThat(map.getRotateResponder()).isNotNull();
  }

  @Test public void setScaleResponder_shouldInvokeMapController() {
    TestScaleResponder scaleResponder = new TestScaleResponder();
    map.setScaleResponder(scaleResponder);
    verify(mapController).setScaleResponder(scaleResponder);
  }

  @Test public void getScaleResponder_shouldNotBeNull() {
    TestScaleResponder scaleResponder = new TestScaleResponder();
    map.setScaleResponder(scaleResponder);
    assertThat(map.getScaleResponder()).isNotNull();
  }

  @Test public void setShoveResponder_shouldInvokeMapController() {
    TestShoveResponder shoveResponder = new TestShoveResponder();
    map.setShoveResponder(shoveResponder);
    verify(mapController).setShoveResponder(shoveResponder);
  }

  @Test public void getShoveResponder_shouldNotBeNull() {
    TestShoveResponder shoveResponder = new TestShoveResponder();
    map.setShoveResponder(shoveResponder);
    assertThat(map.getShoveResponder()).isNotNull();
  }

  @Test public void setSimultaneousGestureAllowed_shouldInvokeMapController() {
    map.setSimultaneousGestureAllowed(TouchInput.Gestures.DOUBLE_TAP,
        TouchInput.Gestures.LONG_PRESS, true);
    verify(mapController).setSimultaneousGestureAllowed(TouchInput.Gestures.DOUBLE_TAP,
        TouchInput.Gestures.LONG_PRESS, true);
  }

  @Test public void isSimultaneousGestureAllowed_shouldInvokeMapController() {
    map.isSimultaneousGestureAllowed(TouchInput.Gestures.DOUBLE_TAP,
        TouchInput.Gestures.LONG_PRESS);
    verify(mapController).isSimultaneousGestureAllowed(TouchInput.Gestures.DOUBLE_TAP,
        TouchInput.Gestures.LONG_PRESS);
  }

  @Test public void drawRoutePins_shouldInvokeOverlayManager() {
    LngLat start = new LngLat(-123, -70);
    LngLat end = new LngLat(-123.1, -70.1);
    map.drawRoutePins(start, end);
    verify(overlayManager).drawRoutePins(start, end);
  }

  @Test public void clearRoutePins_shouldInvokeOverlayManager() {
    map.clearRoutePins();
    verify(overlayManager).clearRoutePins();
  }

  @Test public void drawDroppedPin_shouldInvokeOverlayManager() {
    LngLat point = new LngLat(-123, -70);
    map.drawDroppedPin(point);
    verify(overlayManager).drawDroppedPin(point);
  }

  @Test public void clearDroppedPin_shouldInvokeOverlayManager() {
    map.clearDroppedPins();
    verify(overlayManager).clearDroppedPin();
  }

  @Test public void drawSearchResult_shouldInvokeOverlayManager() {
    LngLat point = new LngLat(-123, -70);
    map.drawSearchResult(point);
    verify(overlayManager).drawSearchResult(point, true);
    map.drawSearchResult(point, false);
    verify(overlayManager).drawSearchResult(point, false);
  }

  @Test public void drawSearchResults_shouldInvokeOverlayManager() {
    LngLat point1 = new LngLat(-123, -70.0);
    LngLat point2 = new LngLat(-123, -70.1);
    LngLat point3 = new LngLat(-123, -70.2);
    List<LngLat> points = new ArrayList<>();
    points.add(point1);
    points.add(point2);
    points.add(point3);
    map.drawSearchResults(points);
    verify(overlayManager).drawSearchResult(point1, true, 0);
    verify(overlayManager).drawSearchResult(point2, true, 1);
    verify(overlayManager).drawSearchResult(point3, true, 2);
    map.drawSearchResults(points, 1);
    verify(overlayManager).drawSearchResult(point1, false, 0);
    verify(overlayManager, times(2)).drawSearchResult(point2, true, 1);
    verify(overlayManager).drawSearchResult(point3, false, 2);
  }

  @Test public void clearSearchResult_shouldInvokeOverlayManager() {
    map.clearSearchResults();
    verify(overlayManager).clearSearchResult();
  }

  @Test public void clearSearchResults_shouldInvokeOverlayManager() {
    map.clearSearchResults();
    verify(overlayManager).clearSearchResult();
  }

  @Test public void drawRoutePin_shouldInvokeOverlayManager() {
    LngLat point = new LngLat(-123, -70.0);
    map.drawRouteLocationMarker(point);
    verify(overlayManager).drawRouteLocationMarker(point);
  }

  @Test public void clearRoutePin_shouldInvokeOverlayManager() {
    map.clearRouteLocationMarker();
    verify(overlayManager).clearRouteLocationMarker();
  }

  @Test public void drawRouteLine_shouldInvokeOverlayManager() {
    LngLat point1 = new LngLat(-123, -70.0);
    LngLat point2 = new LngLat(-123, -70.1);
    LngLat point3 = new LngLat(-123, -70.2);
    List<LngLat> points = new ArrayList<>();
    points.add(point1);
    points.add(point2);
    points.add(point3);
    map.drawRouteLine(points);
    verify(overlayManager).drawRouteLine(points);
  }

  @Test public void clearRouteLine_shouldInvokeOverlayManager() {
    map.clearRouteLine();
    verify(overlayManager).clearRouteLine();
  }

  @Test public void drawTransitRouteLine_shouldInvokeOverlayManager() {
    ArrayList<LngLat> points = new ArrayList();
    points.add(new LngLat(-122.39353246246766, 37.78662344689961));
    points.add(new LngLat(-122.39309926415683, 37.791273135641994));
    String hex = "#ff0000";
    map.drawTransitRouteLine(points, points, hex);
    verify(overlayManager).drawTransitRouteLine(points, points, hex);
  }

  @Test public void clearTransitRouteLine_shouldInvokeOverlayManager() {
    map.clearTransitRouteLine();
    verify(overlayManager).clearTransitRouteLine();
  }

  @Test public void setFeaturePickListener_shouldInvokeFeatureListener() {
    TestFeaturePickListener listener = new TestFeaturePickListener();
    map.setFeaturePickListener(listener);
    mapController.pickFeature(0, 0);
    assertThat(listener.picked).isTrue();
  }

  @Test public void setFeaturePickListener_shouldInvokeCallbackOnMainThread() throws Exception {
    TestFeaturePickListener listener = new TestFeaturePickListener();
    map.setFeaturePickListener(listener);
    mapController.pickFeature(0, 0);
    assertThat(mapView.getAction()).isNotNull();
  }

  @Test public void setViewCompleteListener_shouldInvokeViewCompleteListener() {
    TestViewCompleteListener listener = new TestViewCompleteListener();
    map.setViewCompleteListener(listener);
    listener.onViewComplete();
    assertThat(listener.viewComplete).isTrue();
  }

  @Test public void queueEvent_shouldInvokeMapController() {
    Runnable r = new Runnable() {
      @Override public void run() {

      }
    };
    map.queueEvent(r);
    verify(mapController).queueEvent(r);
  }

  @Test public void queueSceneUpdate_shouldInvokeMapController() {
    map.queueSceneUpdate("test", "true");
    verify(mapController).queueSceneUpdate("test", "true");
  }

  @Test public void applySceneUpdates_shouldInvokeMapController() {
    map.applySceneUpdates();
    verify(mapController).applySceneUpdates();
  }

  private class TestFeaturePickListener implements FeaturePickListener {

    boolean picked = false;

    @Override
    public void onFeaturePick(Map<String, String> properties, float positionX, float positionY) {
      picked = true;
    }
  }

  private class TestViewCompleteListener implements ViewCompleteListener {

    boolean viewComplete = false;

    @Override public void onViewComplete() {
      viewComplete = true;
    }
  }
}
