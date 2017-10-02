package com.mapzen.android.graphics;

import com.mapzen.android.core.MapzenManager;
import com.mapzen.android.graphics.model.BitmapMarker;
import com.mapzen.android.graphics.model.BitmapMarkerManager;
import com.mapzen.android.graphics.model.BubbleWrapStyle;
import com.mapzen.android.graphics.model.CameraType;
import com.mapzen.android.graphics.model.EaseType;
import com.mapzen.android.graphics.model.Marker;
import com.mapzen.android.graphics.model.Polygon;
import com.mapzen.android.graphics.model.Polyline;
import com.mapzen.android.graphics.model.RefillStyle;
import com.mapzen.android.graphics.model.ThemeColor;
import com.mapzen.android.graphics.model.WalkaboutStyle;
import com.mapzen.tangram.HttpHandler;
import com.mapzen.tangram.LabelPickResult;
import com.mapzen.tangram.LngLat;
import com.mapzen.tangram.MapController;
import com.mapzen.tangram.SceneUpdate;
import com.mapzen.tangram.TouchInput;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.mapzen.TestHelper.getMockContext;
import static com.mapzen.android.graphics.SceneUpdateManager.STYLE_GLOBAL_VAR_API_KEY;
import static com.mapzen.android.graphics.SceneUpdateManager.STYLE_GLOBAL_VAR_BIKE_OVERLAY;
import static com.mapzen.android.graphics.SceneUpdateManager.STYLE_GLOBAL_VAR_LANGUAGE;
import static com.mapzen.android.graphics.SceneUpdateManager.STYLE_GLOBAL_VAR_PATH_OVERLAY;
import static com.mapzen.android.graphics.SceneUpdateManager.STYLE_GLOBAL_VAR_TRANSIT_OVERLAY;
import static com.mapzen.android.graphics.model.ThemeColor.BLUE;
import static com.mapzen.android.graphics.model.ThemedMapStyle.NONE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.net.ssl.*")
@SuppressStaticInitializationFor("com.mapzen.tangram.MapController")
public class MapzenMapTest {

  private TestMapView mapView;
  private MapzenMap map;
  private TestMapController mapController;
  private OverlayManager overlayManager;
  private LabelPickHandler labelPickHandler;
  private MapStateManager mapStateManager;
  private BitmapMarkerManager bitmapMarkerManager;
  private SceneUpdateManager sceneUpdateManager;
  private Locale locale;
  private MapzenManager mapzenManager;
  private ImportYamlGenerator yamlGenerator;

  @Before public void setUp() throws Exception {
    mapView = new TestMapView();
    mapController = mock(TestMapController.class);
    doCallRealMethod().when(mapController)
        .setFeaturePickListener(any(MapController.FeaturePickListener.class));
    doCallRealMethod().when(mapController).pickFeature(anyFloat(), anyFloat());
    doCallRealMethod().when(mapController)
        .setMarkerPickListener(any(MapController.MarkerPickListener.class));
    doCallRealMethod().when(mapController).pickMarker(anyFloat(), anyFloat());
    doCallRealMethod().when(mapController).setPosition(any(LngLat.class));
    doCallRealMethod().when(mapController).getPosition();
    doCallRealMethod().when(mapController).setZoom(anyFloat());
    doCallRealMethod().when(mapController).getZoom();
    doCallRealMethod().when(mapController).setRotation(anyFloat());
    doCallRealMethod().when(mapController).getRotation();
    doCallRealMethod().when(mapController).setTilt(anyFloat());
    doCallRealMethod().when(mapController).getTilt();
    overlayManager = mock(OverlayManager.class);
    mapStateManager = new MapStateManager();
    labelPickHandler = new LabelPickHandler(mapView);
    bitmapMarkerManager = mock(BitmapMarkerManager.class);
    sceneUpdateManager = new SceneUpdateManager();
    locale = new Locale("en_us");
    mapzenManager = mock(MapzenManager.class);
    yamlGenerator = mock(ImportYamlGenerator.class);
    map = new MapzenMap(mapView, mapController, overlayManager, mapStateManager, labelPickHandler,
        bitmapMarkerManager, sceneUpdateManager, locale, mapzenManager, yamlGenerator);
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

  @Test public void setRotateResponder_shouldNotInvokeMapController() {
    TestRotateResponder rotateResponder = new TestRotateResponder();
    map.setRotateResponder(rotateResponder);
    verify(mapController, never()).setRotateResponder(rotateResponder);
  }

  @Test public void getRotateResponder_shouldNotBeNull() {
    TestRotateResponder rotateResponder = new TestRotateResponder();
    map.setRotateResponder(rotateResponder);
    assertThat(map.getRotateResponder()).isNotNull();
  }

  @Test public void setRotateResponder_shouldInvokeOverlayManager() {
    TestRotateResponder rotateResponder = new TestRotateResponder();
    map.setRotateResponder(rotateResponder);
    map.internalRotateResponder.onRotate(1, 2, 0.5f);
    verify(overlayManager).onRotate(1, 2, 0.5f);
  }

  @Test public void setRotateResponder_shouldInvokeRotateResponder() {
    TestRotateResponder rotateResponder = new TestRotateResponder();
    map.setRotateResponder(rotateResponder);
    map.internalRotateResponder.onRotate(1, 2, 0.5f);
    assertThat(rotateResponder.rotated).isTrue();
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

  @Test public void setFeaturePickListener_shouldInvokeFeatureListener() throws Exception {
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

  @Test public void setMarkerPickListener_shouldInvokeMarkerListener() throws Exception {
    TestMarkerPickListener listener = new TestMarkerPickListener();
    map.setMarkerPickListener(listener);
    mapController.pickMarker(0, 0);
    assertThat(listener.picked).isTrue();
  }

  @Test public void setMarkerPickListener_shouldInvokeCallbackOnMainThread() throws Exception {
    TestMarkerPickListener listener = new TestMarkerPickListener();
    map.setMarkerPickListener(listener);
    mapController.pickMarker(0, 0);
    assertThat(mapView.getAction()).isNotNull();
  }

  @Test public void setLabelPickListener_shouldInvokeLabelListener() {
    TestLabelPickListener listener = new TestLabelPickListener();
    map.setLabelPickListener(listener);
    labelPickHandler.onLabelPick(mock(LabelPickResult.class), 0, 0);
    assertThat(listener.picked).isTrue();
  }

  @Test public void setLabelPickListener_shouldInvokeCallbackOnMainThread() throws Exception {
    TestLabelPickListener listener = new TestLabelPickListener();
    map.setLabelPickListener(listener);
    labelPickHandler.onLabelPick(mock(LabelPickResult.class), 0, 0);
    assertThat(mapView.getAction()).isNotNull();
  }

  @Test public void setLabelPickListener_shouldDoNothingForNullLabelResult() {
    TestLabelPickListener listener = new TestLabelPickListener();
    map.setLabelPickListener(listener);
    labelPickHandler.onLabelPick(null, 0, 0);
    assertThat(listener.picked).isFalse();
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

  @Test
  public void queueSceneUpdate_shouldNotCallMapController() throws Exception {
    // Reset because updateSceneAsync called in MapzenMap constructor
    reset(mapController);

    map.queueSceneUpdate("test", "test");
    verify(mapController, never()).updateSceneAsync(any(List.class));
  }

  @Test
  public void applySceneUpdates_shouldApplyQueuedUpdates() throws Exception {
    // Reset because updateSceneAsync called in MapzenMap constructor
    reset(mapController);

    map.queueSceneUpdate("test", "test");
    map.applySceneUpdates();
    SceneUpdate update = new SceneUpdate("test", "test");
    verify(mapController).updateSceneAsync(argThat(new SceneUpdatesMatcher(update)));
  }

  @Test
  public void applySceneUpdates_shouldClearQueuedUpdates() throws Exception {
    // Reset because updateSceneAsync called in MapzenMap constructor
    reset(mapController);

    map.queueSceneUpdate("test", "test");
    map.applySceneUpdates();
    SceneUpdate update = new SceneUpdate("test", "test");
    verify(mapController).updateSceneAsync(argThat(new SceneUpdatesMatcher(update)));
    map.applySceneUpdates();
    verify(mapController, times(1)).updateSceneAsync(any(List.class));
  }

  @Test public void onDestroy_shouldPersistMapPosition() throws Exception {
    LngLat position = new LngLat(1, 2);
    mapController.setPosition(position);
    map.onDestroy();
    assertThat(mapStateManager.getPosition()).isEqualTo(position);
  }

  @Test public void onDestroy_shouldPersistMapZoom() throws Exception {
    mapController.setZoom(15);
    map.onDestroy();
    assertThat(mapStateManager.getZoom()).isEqualTo(15);
  }

  @Test public void onDestroy_shouldPersistMapRotation() throws Exception {
    mapController.setRotation(3.14f);
    map.onDestroy();
    assertThat(mapStateManager.getRotation()).isEqualTo(3.14f);
  }

  @Test public void onDestroy_shouldPersistMapTilt() throws Exception {
    mapController.setTilt(1.57f);
    map.onDestroy();
    assertThat(mapStateManager.getTilt()).isEqualTo(1.57f);
  }

  @Test public void onDestroy_shouldUnregisterApiKeyListener() throws Exception {
    mapzenManager.addApiKeyChangeListener(map.apiKeyChangeListener);
    map.onDestroy();
    verify(mapzenManager).removeApiKeyChangeListener(map.apiKeyChangeListener);
  }

  @Test public void restoreMapState_shouldPersistPosition() throws Exception {
    verify(mapController).setPosition(new LngLat(0, 0));
  }

  @Test public void restoreMapState_shouldPersistZoom() throws Exception {
    verify(mapController).setZoom(0);
  }

  @Test public void restoreMapState_shouldPersistRotation() throws Exception {
    verify(mapController).setRotation(0);
  }

  @Test public void restoreMapState_shouldPersistTilt() throws Exception {
    verify(mapController).setTilt(0);
  }

  @Test public void restoreMapState_shouldPersistCameraType() throws Exception {
    verify(mapController).setCameraType(MapController.CameraType.ISOMETRIC);
  }

  @Test public void restoreMapState_shouldPersistOverlaysEnabled() throws Exception {
    ArrayList<SceneUpdate> updates = new ArrayList<>();
    updates.add(new SceneUpdate(STYLE_GLOBAL_VAR_TRANSIT_OVERLAY, "false"));
    updates.add(new SceneUpdate(STYLE_GLOBAL_VAR_BIKE_OVERLAY, "false"));
    updates.add(new SceneUpdate(STYLE_GLOBAL_VAR_PATH_OVERLAY, "true"));
    verify(mapController).updateSceneAsync(argThat(new SceneUpdatesMatcher(updates)));
  }

  @Test public void setStyle_shouldSetStyleAndGlobalVariables() throws Exception {
    map.setStyle(new WalkaboutStyle());
    verify(mapController).loadSceneYaml(anyString(), anyString(), any(List.class));
  }

  @Test public void setStyle_shouldNotCallMapControllerAsyncMethod() throws Exception {
    map.setStyle(new WalkaboutStyle());
    verify(mapController, never()).loadSceneFileAsync(anyString(), any(List.class));
  }

  @Test public void setStyleAsync_shouldSetStyleAndGlobalVariables() throws Exception {
    map.setStyleAsync(new WalkaboutStyle(), null);
    verify(mapController).loadSceneYamlAsync(anyString(), anyString(), any(List.class));
  }

  @Test public void setStyleAsync_shouldCallListenerOnSceneLoaded() throws Exception {
    int sceneId = 1;
    when(mapController.loadSceneYamlAsync(anyString(), anyString(), anyList())).thenReturn(sceneId);
    TestOnStyleLoadedListener listener = new TestOnStyleLoadedListener();
    map.setStyleAsync(new WalkaboutStyle(), listener);
    map.internalSceneLoadListener.onSceneReady(sceneId, null);
    assertThat(listener.loaded).isTrue();
  }

  @Test public void setStyleAsync_shouldNotCallMapControllerSyncMethod() throws Exception {
    map.setStyleAsync(new WalkaboutStyle(), null);
    verify(mapController, never()).loadSceneFile(anyString(), any(List.class));
  }

  @Test public void overlays_shouldBeDisabledByDefaultExceptPath() throws Exception {
    when(mapzenManager.getApiKey()).thenReturn("test-api-key");
    map.setStyle(new BubbleWrapStyle());
    List<SceneUpdate> sceneUpdates = new ArrayList<>();
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_API_KEY, "test-api-key"));
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_LANGUAGE, locale.getLanguage()));
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_TRANSIT_OVERLAY, "false"));
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_BIKE_OVERLAY, "false"));
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_PATH_OVERLAY, "true"));
    verify(mapController).loadSceneYaml(anyString(), anyString(), argThat(
        new SceneUpdatesMatcher(sceneUpdates)));
  }

  @Test public void setTransitOverlayEnabled_shouldCallSceneUpdates() throws Exception {
    map.setTransitOverlayEnabled(true);
    SceneUpdate sceneUpdate = new SceneUpdate(STYLE_GLOBAL_VAR_TRANSIT_OVERLAY, "true");
    verify(mapController).updateSceneAsync(argThat(new SceneUpdatesMatcher(sceneUpdate)));
  }

  @Test public void setBikeOverlayEnabled_shouldCallSceneUpdates() throws Exception {
    map.setBikeOverlayEnabled(true);
    SceneUpdate sceneUpdate = new SceneUpdate(STYLE_GLOBAL_VAR_BIKE_OVERLAY, "true");
    verify(mapController).updateSceneAsync(argThat(new SceneUpdatesMatcher(sceneUpdate)));
  }

  @Test public void setPathOverlayEnabled_shouldCallSceneUpdates() throws Exception {
    map.setPathOverlayEnabled(true);
    SceneUpdate sceneUpdate = new SceneUpdate(STYLE_GLOBAL_VAR_PATH_OVERLAY, "true");
    verify(mapController).updateSceneAsync(argThat(new SceneUpdatesMatcher(sceneUpdate)));
  }

  @Test public void setOverlaysEnabled_fff_shouldCallSceneUpdates() throws Exception {
    map.setOverlaysEnabled(false, false, false);
    List<SceneUpdate> sceneUpdates = new ArrayList<>();
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_TRANSIT_OVERLAY, "false"));
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_BIKE_OVERLAY, "false"));
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_PATH_OVERLAY, "false"));
    verify(mapController).updateSceneAsync(argThat(new SceneUpdatesMatcher(sceneUpdates)));
  }

  @Test public void setOverlaysEnabled_ttt_shouldCallSceneUpdates() throws Exception {
    map.setOverlaysEnabled(true, true, true);
    List<SceneUpdate> sceneUpdates = new ArrayList<>();
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_TRANSIT_OVERLAY, "true"));
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_BIKE_OVERLAY, "true"));
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_PATH_OVERLAY, "true"));
    verify(mapController).updateSceneAsync(argThat(new SceneUpdatesMatcher(sceneUpdates)));
  }

  @Test public void onSceneReady_restoresMarkers() throws Exception {
    map.internalSceneLoadListener.onSceneReady(1, null);
    verify(bitmapMarkerManager).restoreMarkers();
  }

  @Test public void setHttpHandler_shouldCallMapController() throws Exception {
    MapzenMapHttpHandler mapzenMapHandler = mock(MapzenMapHttpHandler.class);
    HttpHandler handler = mock(HttpHandler.class);
    when(mapzenMapHandler.httpHandler()).thenReturn(handler);
    map.setHttpHandler(mapzenMapHandler);
    verify(mapController).setHttpHandler(handler);
  }

  @Test public void updatedApiKey_shouldCreateSceneUpdate() throws Exception {
    MapzenManager manager = MapzenManager.instance(getMockContext());
    manager.setApiKey("key");
    new MapzenMap(mapView, mapController, overlayManager, mapStateManager, labelPickHandler,
        bitmapMarkerManager, sceneUpdateManager, locale, manager, yamlGenerator);
    manager.setApiKey("updated-key");
    List<SceneUpdate> updates = new ArrayList<>();
    updates.add(new SceneUpdate(STYLE_GLOBAL_VAR_API_KEY, "updated-key"));
    verify(mapController).updateSceneAsync(argThat(new SceneUpdatesMatcher(updates)));
  }

  @Test public void setStyle_themedStyle_shouldCallLoadYamlWithThemeDefaults() throws Exception {
    when(mapzenManager.getApiKey()).thenReturn("apiKey");

    RefillStyle refillStyle = new RefillStyle();
    map.setStyle(refillStyle);

    assertThat(mapStateManager.getThemeColor()).isEqualTo(refillStyle.getDefaultColor());
    assertThat(mapStateManager.getLabelLevel()).isEqualTo(refillStyle.getDefaultLabelLevel());
    assertThat(mapStateManager.getLod()).isEqualTo(refillStyle.getDefaultLod());

    String yaml = yamlGenerator.getImportYaml(refillStyle, mapStateManager.getLabelLevel(),
        mapStateManager.getLod(), mapStateManager.getThemeColor());
    String resourceRoot = refillStyle.getStyleRootPath();
    List<SceneUpdate> sceneUpdates = new ArrayList<>();
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_API_KEY, "apiKey"));
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_LANGUAGE, "en_us"));
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_TRANSIT_OVERLAY, "false"));
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_BIKE_OVERLAY, "false"));
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_PATH_OVERLAY, "true"));
    verify(mapController).loadSceneYaml(eq(yaml), eq(resourceRoot), argThat(
        new SceneUpdatesMatcher(sceneUpdates)));
  }

  @Test public void setStyleAndLabelLevel_shouldCallLoadYamlWithCorrectValues() throws Exception {
    when(mapzenManager.getApiKey()).thenReturn("apiKey");

    RefillStyle refillStyle = new RefillStyle();
    map.setStyleAndLabelLevel(refillStyle, 8);

    assertThat(mapStateManager.getThemeColor()).isEqualTo(refillStyle.getDefaultColor());
    assertThat(mapStateManager.getLabelLevel()).isEqualTo(8);
    assertThat(mapStateManager.getLod()).isEqualTo(refillStyle.getDefaultLod());

    String yaml = yamlGenerator.getImportYaml(refillStyle, 8,
        mapStateManager.getLod(), mapStateManager.getThemeColor());
    String resourceRoot = refillStyle.getStyleRootPath();
    List<SceneUpdate> sceneUpdates = new ArrayList<>();
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_API_KEY, "apiKey"));
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_LANGUAGE, "en_us"));
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_TRANSIT_OVERLAY, "false"));
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_BIKE_OVERLAY, "false"));
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_PATH_OVERLAY, "true"));
    verify(mapController).loadSceneYaml(eq(yaml), eq(resourceRoot), argThat(
        new SceneUpdatesMatcher(sceneUpdates)));
  }

  @Test public void setStyleAndLabelLevelAsync_shouldCallLoadYamlWithCorrectValues()
      throws Exception {
    when(mapzenManager.getApiKey()).thenReturn("apiKey");

    RefillStyle refillStyle = new RefillStyle();
    map.setStyleAndLabelLevelAsync(refillStyle, 8, null);

    assertThat(mapStateManager.getThemeColor()).isEqualTo(refillStyle.getDefaultColor());
    assertThat(mapStateManager.getLabelLevel()).isEqualTo(8);
    assertThat(mapStateManager.getLod()).isEqualTo(refillStyle.getDefaultLod());

    String yaml = yamlGenerator.getImportYaml(refillStyle, 8,
        mapStateManager.getLod(), mapStateManager.getThemeColor());
    String resourceRoot = refillStyle.getStyleRootPath();
    List<SceneUpdate> sceneUpdates = new ArrayList<>();
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_API_KEY, "apiKey"));
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_LANGUAGE, "en_us"));
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_TRANSIT_OVERLAY, "false"));
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_BIKE_OVERLAY, "false"));
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_PATH_OVERLAY, "true"));
    verify(mapController).loadSceneYamlAsync(eq(yaml), eq(resourceRoot), argThat(
        new SceneUpdatesMatcher(sceneUpdates)));
  }

  @Test public void setStyleAndLabelLevelAsync_shouldCallListenerOnSceneLoaded() throws Exception {
    when(mapzenManager.getApiKey()).thenReturn("apiKey");
    int sceneId = 8;
    when(mapController.loadSceneYamlAsync(anyString(), anyString(), anyList())).thenReturn(sceneId);
    TestOnStyleLoadedListener listener = new TestOnStyleLoadedListener();
    RefillStyle refillStyle = new RefillStyle();
    map.setStyleAndLabelLevelAsync(refillStyle, 8, listener);
    map.internalSceneLoadListener.onSceneReady(sceneId, null);
    assertThat(listener.loaded).isTrue();
  }

  @Test(expected = IllegalArgumentException.class)
  public void setStyleAndLabelLevel_shouldVerifyLowValue() throws Exception {
    map.setStyleAndLabelLevel(new RefillStyle(), -10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void setStyleAndLabelLevelAsync_shouldVerifyLowValue() throws Exception {
    map.setStyleAndLabelLevelAsync(new RefillStyle(), -10, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void setStyleAndLabelLevel_shouldVerifyHighValue() throws Exception {
    map.setStyleAndLabelLevel(new RefillStyle(), 12);
  }

  @Test(expected = IllegalArgumentException.class)
  public void setStyleAndLabelLevelAsync_shouldVerifyHighValue() throws Exception {
    map.setStyleAndLabelLevelAsync(new RefillStyle(), 12, null);
  }

  @Test public void setStyleLabelLevelLodThemeColorAsync_shouldVerifyNoValue() throws Exception {
    map.setStyleLabelLevelLodThemeColorAsync(new RefillStyle(), NONE, NONE, ThemeColor.NONE, null);
    verify(mapController).loadSceneYamlAsync(anyString(), anyString(), anyList());
  }

  @Test public void setStyleLabelLevelLodThemeColor_shouldVerifyNoValue() throws Exception {
    map.setStyleLabelLevelLodThemeColor(new RefillStyle(), NONE, NONE, ThemeColor.NONE);
    verify(mapController).loadSceneYaml(anyString(), anyString(), anyList());
  }

  @Test public void setStyleAndLod_shouldCallLoadYamlWithCorrectValues() throws Exception {
    when(mapzenManager.getApiKey()).thenReturn("apiKey");

    RefillStyle refillStyle = new RefillStyle();
    map.setStyleAndLod(refillStyle, 8);

    assertThat(mapStateManager.getThemeColor()).isEqualTo(refillStyle.getDefaultColor());
    assertThat(mapStateManager.getLabelLevel()).isEqualTo(refillStyle.getDefaultLabelLevel());
    assertThat(mapStateManager.getLod()).isEqualTo(8);

    String yaml = yamlGenerator.getImportYaml(refillStyle, mapStateManager.getLabelLevel(),
        8, mapStateManager.getThemeColor());
    String resourceRoot = refillStyle.getStyleRootPath();
    List<SceneUpdate> sceneUpdates = new ArrayList<>();
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_API_KEY, "apiKey"));
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_LANGUAGE, "en_us"));
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_TRANSIT_OVERLAY, "false"));
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_BIKE_OVERLAY, "false"));
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_PATH_OVERLAY, "true"));
    verify(mapController).loadSceneYaml(eq(yaml), eq(resourceRoot), argThat(
        new SceneUpdatesMatcher(sceneUpdates)));
  }

  @Test public void setStyleAndLodAsync_shouldCallLoadYamlWithCorrectValues() throws Exception {
    when(mapzenManager.getApiKey()).thenReturn("apiKey");

    RefillStyle refillStyle = new RefillStyle();
    map.setStyleAndLodAsync(refillStyle, 8, null);

    assertThat(mapStateManager.getThemeColor()).isEqualTo(refillStyle.getDefaultColor());
    assertThat(mapStateManager.getLabelLevel()).isEqualTo(refillStyle.getDefaultLabelLevel());
    assertThat(mapStateManager.getLod()).isEqualTo(8);

    String yaml = yamlGenerator.getImportYaml(refillStyle, mapStateManager.getLabelLevel(),
        8, mapStateManager.getThemeColor());
    String resourceRoot = refillStyle.getStyleRootPath();
    List<SceneUpdate> sceneUpdates = new ArrayList<>();
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_API_KEY, "apiKey"));
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_LANGUAGE, "en_us"));
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_TRANSIT_OVERLAY, "false"));
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_BIKE_OVERLAY, "false"));
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_PATH_OVERLAY, "true"));
    verify(mapController).loadSceneYamlAsync(eq(yaml), eq(resourceRoot), argThat(
        new SceneUpdatesMatcher(sceneUpdates)));
  }

  @Test public void setStyleAndLodAsync_shouldCallListenerOnSceneLoaded() throws Exception {
    when(mapzenManager.getApiKey()).thenReturn("apiKey");
    int sceneId = 8;
    when(mapController.loadSceneYamlAsync(anyString(), anyString(), anyList())).thenReturn(sceneId);
    TestOnStyleLoadedListener listener = new TestOnStyleLoadedListener();
    RefillStyle refillStyle = new RefillStyle();
    map.setStyleAndLodAsync(refillStyle, 8, listener);
    map.internalSceneLoadListener.onSceneReady(sceneId, null);
    assertThat(listener.loaded).isTrue();
  }

  @Test(expected = IllegalArgumentException.class)
  public void setStyleAndLod_shouldVerifyLowValue() throws Exception {
    map.setStyleAndLod(new RefillStyle(), -10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void setStyleAndLodAsync_shouldVerifyLowValue() throws Exception {
    map.setStyleAndLodAsync(new RefillStyle(), -10, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void setStyleAndLod_shouldVerifyHighValue() throws Exception {
    map.setStyleAndLod(new RefillStyle(), 12);
  }

  @Test(expected = IllegalArgumentException.class)
  public void setStyleAndLodAsync_shouldVerifyHighValue() throws Exception {
    map.setStyleAndLodAsync(new RefillStyle(), 12, null);
  }

  @Test public void setStyleAndThemeColor_shouldCallLoadYamlWithCorrectValues() throws Exception {
    when(mapzenManager.getApiKey()).thenReturn("apiKey");

    RefillStyle refillStyle = new RefillStyle();
    map.setStyleAndThemeColor(refillStyle, ThemeColor.PINK);

    assertThat(mapStateManager.getThemeColor()).isEqualTo(ThemeColor.PINK);
    assertThat(mapStateManager.getLabelLevel()).isEqualTo(refillStyle.getDefaultLabelLevel());
    assertThat(mapStateManager.getLod()).isEqualTo(refillStyle.getDefaultLod());

    String yaml = yamlGenerator.getImportYaml(refillStyle, mapStateManager.getLabelLevel(),
        mapStateManager.getLod(), ThemeColor.PINK);
    String resourceRoot = refillStyle.getStyleRootPath();
    List<SceneUpdate> sceneUpdates = new ArrayList<>();
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_API_KEY, "apiKey"));
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_LANGUAGE, "en_us"));
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_TRANSIT_OVERLAY, "false"));
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_BIKE_OVERLAY, "false"));
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_PATH_OVERLAY, "true"));
    verify(mapController).loadSceneYaml(eq(yaml), eq(resourceRoot), argThat(
        new SceneUpdatesMatcher(sceneUpdates)));
  }

  @Test public void setStyleAndThemeColorAsync_shouldCallLoadYamlWithCorrectValues()
      throws Exception {
    when(mapzenManager.getApiKey()).thenReturn("apiKey");

    RefillStyle refillStyle = new RefillStyle();
    map.setStyleAndThemeColorAsync(refillStyle, ThemeColor.PINK, null);

    assertThat(mapStateManager.getThemeColor()).isEqualTo(ThemeColor.PINK);
    assertThat(mapStateManager.getLabelLevel()).isEqualTo(refillStyle.getDefaultLabelLevel());
    assertThat(mapStateManager.getLod()).isEqualTo(refillStyle.getDefaultLod());

    String yaml = yamlGenerator.getImportYaml(refillStyle, mapStateManager.getLabelLevel(),
        mapStateManager.getLod(), ThemeColor.PINK);
    String resourceRoot = refillStyle.getStyleRootPath();
    List<SceneUpdate> sceneUpdates = new ArrayList<>();
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_API_KEY, "apiKey"));
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_LANGUAGE, "en_us"));
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_TRANSIT_OVERLAY, "false"));
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_BIKE_OVERLAY, "false"));
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_PATH_OVERLAY, "true"));
    verify(mapController).loadSceneYamlAsync(eq(yaml), eq(resourceRoot), argThat(
        new SceneUpdatesMatcher(sceneUpdates)));
  }

  @Test public void setStyleAndThemeColorAsync_shouldCallListenerOnSceneLoaded() throws Exception {
    when(mapzenManager.getApiKey()).thenReturn("apiKey");
    int sceneId = 8;
    when(mapController.loadSceneYamlAsync(anyString(), anyString(), anyList())).thenReturn(sceneId);
    TestOnStyleLoadedListener listener = new TestOnStyleLoadedListener();
    RefillStyle refillStyle = new RefillStyle();
    map.setStyleAndThemeColorAsync(refillStyle, BLUE, listener);
    map.internalSceneLoadListener.onSceneReady(sceneId, null);
    assertThat(listener.loaded).isTrue();
  }

  @Test public void setStyleLabelDetailLevelThemeColor_shouldCallLoadYamlWithCorrectValues()
      throws Exception {
    when(mapzenManager.getApiKey()).thenReturn("apiKey");

    RefillStyle refillStyle = new RefillStyle();
    map.setStyleLabelLevelLodThemeColor(refillStyle, 3, 4, BLUE);

    assertThat(mapStateManager.getThemeColor()).isEqualTo(BLUE);
    assertThat(mapStateManager.getLabelLevel()).isEqualTo(3);
    assertThat(mapStateManager.getLod()).isEqualTo(4);

    String yaml = yamlGenerator.getImportYaml(refillStyle, 3, 4, BLUE);
    String resourceRoot = refillStyle.getStyleRootPath();
    List<SceneUpdate> sceneUpdates = new ArrayList<>();
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_API_KEY, "apiKey"));
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_LANGUAGE, "en_us"));
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_TRANSIT_OVERLAY, "false"));
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_BIKE_OVERLAY, "false"));
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_PATH_OVERLAY, "true"));
    verify(mapController).loadSceneYaml(eq(yaml), eq(resourceRoot), argThat(
        new SceneUpdatesMatcher(sceneUpdates)));
  }

  @Test public void setStyleLabelDetailLevelThemeColorAsync_shouldCallLoadYamlWithCorrectValues()
      throws Exception {
    when(mapzenManager.getApiKey()).thenReturn("apiKey");

    RefillStyle refillStyle = new RefillStyle();
    map.setStyleLabelLevelLodThemeColorAsync(refillStyle, 3, 4, BLUE, null);

    assertThat(mapStateManager.getThemeColor()).isEqualTo(BLUE);
    assertThat(mapStateManager.getLabelLevel()).isEqualTo(3);
    assertThat(mapStateManager.getLod()).isEqualTo(4);

    String yaml = yamlGenerator.getImportYaml(refillStyle, 3, 4, BLUE);
    String resourceRoot = refillStyle.getStyleRootPath();
    List<SceneUpdate> sceneUpdates = new ArrayList<>();
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_API_KEY, "apiKey"));
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_LANGUAGE, "en_us"));
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_TRANSIT_OVERLAY, "false"));
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_BIKE_OVERLAY, "false"));
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_PATH_OVERLAY, "true"));
    verify(mapController).loadSceneYamlAsync(eq(yaml), eq(resourceRoot), argThat(
        new SceneUpdatesMatcher(sceneUpdates)));
  }

  @Test public void setStyleLabelDetailLevelThemeColorAsync_shouldCallListenerOnSceneLoaded()
      throws Exception {
    when(mapzenManager.getApiKey()).thenReturn("apiKey");
    int sceneId = 8;
    when(mapController.loadSceneYamlAsync(anyString(), anyString(), anyList())).thenReturn(sceneId);
    TestOnStyleLoadedListener listener = new TestOnStyleLoadedListener();
    RefillStyle refillStyle = new RefillStyle();
    map.setStyleLabelLevelLodThemeColorAsync(refillStyle, 1, 2, BLUE, listener);
    map.internalSceneLoadListener.onSceneReady(sceneId, null);
    assertThat(listener.loaded).isTrue();
  }

  public class TestRotateResponder implements TouchInput.RotateResponder {

    boolean rotated = false;

    @Override public boolean onRotate(float x, float y, float rotation) {
      rotated = true;
      return false;
    }
  }

  private class TestFeaturePickListener implements FeaturePickListener {

    boolean picked = false;

    @Override
    public void onFeaturePick(Map<String, String> properties, float positionX, float positionY) {
      picked = true;
    }
  }

  private class TestMarkerPickListener implements MarkerPickListener {

    boolean picked = false;

    @Override
    public void onMarkerPick(BitmapMarker marker) {
      picked = true;
    }
  }

  private class TestLabelPickListener implements LabelPickListener {

    boolean picked = false;

    @Override
    public void onLabelPicked(LabelPickResult result, float positionX, float positionY) {
      picked = true;
    }
  }

  private class TestViewCompleteListener implements ViewCompleteListener {

    boolean viewComplete = false;

    @Override public void onViewComplete() {
      viewComplete = true;
    }
  }

  private class TestOnStyleLoadedListener implements OnStyleLoadedListener {

    boolean loaded = false;

    @Override public void onStyleLoaded() {
      loaded = true;
    }
  }
}
