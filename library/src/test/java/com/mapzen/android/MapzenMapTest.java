package com.mapzen.android;

import com.mapzen.android.model.EaseType;
import com.mapzen.android.model.Marker;
import com.mapzen.android.model.Polygon;
import com.mapzen.android.model.Polyline;
import com.mapzen.tangram.LngLat;
import com.mapzen.tangram.MapController;
import com.mapzen.tangram.TouchInput;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@SuppressStaticInitializationFor("com.mapzen.tangram.MapController")
public class MapzenMapTest {

    private MapzenMap map;
    private MapController mapController;
    private OverlayManager overlayManager;

    @Before public void setUp() throws Exception {
        mapController = mock(MapController.class);
        overlayManager = mock(OverlayManager.class);
        map = new MapzenMap(mapController, overlayManager);
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
        verify(mapController).setZoomEased(15, 300);
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
        LngLat lngLat = new LngLat(0, 0);
        map.setPosition(lngLat);
        verify(mapController).setPosition(lngLat);
    }

    @Test public void setPositionAnimated_shouldInvokeMapControllerAnimated() throws Exception {
        LngLat lngLat = new LngLat(0, 0);
        map.setPosition(lngLat, 300);
        verify(mapController).setPositionEased(lngLat, 300);
    }

    @Test public void setPositionAnimatedEased_shouldInvokeMapControllerAnimatedEased()
            throws Exception {
        LngLat lngLat = new LngLat(0, 0);
        map.setPosition(lngLat, 300, EaseType.CUBIC);
        verify(mapController).setPositionEased(lngLat, 300, MapController.EaseType.CUBIC);
    }

    @Test public void getPosition_shouldInvokeMapController() throws Exception {
        LngLat lngLat = new LngLat(0, 0);
        when(mapController.getPosition()).thenReturn(lngLat);
        assertThat(map.getPosition()).isEqualTo(lngLat);
    }

    @Test public void setRotation_shouldInvokeMapController() throws Exception {
        map.setRotation(3.14f);
        verify(mapController).setRotation(3.14f);
    }

    @Test public void setRotationAnimated_shouldInvokeMapControllerAnimated() throws Exception {
        map.setRotation(3.14f, 300);
        verify(mapController).setRotationEased(3.14f, 300);
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
        verify(mapController).setTiltEased(3.14f, 300);
    }

    @Test public void setTiltAnimatedEased_shouldInvokeMapControllerAnimatedEased()
            throws Exception {
        map.setTilt(3.14f, 300, EaseType.CUBIC);
        verify(mapController).setTiltEased(3.14f, 300, MapController.EaseType.CUBIC);
    }

    @Test public void getTilt_shouldInvokeMapController() throws Exception {
        when(mapController.getTilt()).thenReturn(3.14f);
        assertThat(map.getTilt()).isEqualTo(3.14f);
    }

    @Test public void setMyLocationEnabled_shouldInvokeOverlayManager() throws Exception {
        map.setMyLocationEnabled(true);
        verify(overlayManager).setMyLocationEnabled(true);
    }

    @Test public void addMarker_shouldInvokeOverlayManager() throws Exception {
        Marker marker = new Marker(0, 0);
        map.addMarker(marker);
        verify(overlayManager).addMarker(marker);
    }

    @Test public void addPolyline_shouldInvokeOverlayManager() throws Exception {
        LngLat lngLat = new LngLat(0, 0);
        Polyline polyline = new Polyline.Builder().add(lngLat).add(lngLat).build();
        map.addPolyline(polyline);
        verify(overlayManager).addPolyline(polyline);
    }

    @Test public void addPolygon_shouldInvokeOverlayManager() throws Exception {
        LngLat lngLat = new LngLat(0, 0);
        Polygon polygon = new Polygon.Builder().add(lngLat).add(lngLat).build();
        map.addPolygon(polygon);
        verify(overlayManager).addPolygon(polygon);
    }

    @Test public void coordinatesAtScreenPosition_shouldInvokeMapController() throws Exception {
        map.coordinatesAtScreenPosition(4, 3);
        verify(mapController).coordinatesAtScreenPosition(4, 3);
    }

    @Test public void setTapResponder_shouldInvokeMapController() {
        TestTapResponder tapResponder = new TestTapResponder();
        map.setTapResponder(tapResponder);
        verify(mapController).setTapResponder(tapResponder);
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

    @Test public void setPanResponder_shouldInvokeMapController() {
        TestPanResponder panResponder = new TestPanResponder();
        map.setPanResponder(panResponder);
        verify(mapController).setPanResponder(panResponder);
    }

    @Test public void getPanResponder_shouldNotBeNull() {
        TestPanResponder panResponder = new TestPanResponder();
        map.setPanResponder(panResponder);
        assertThat(map.getPanResponder()).isNotNull();
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

}
