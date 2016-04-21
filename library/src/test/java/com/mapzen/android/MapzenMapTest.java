package com.mapzen.android;

import com.mapzen.android.model.Marker;
import com.mapzen.android.model.Polygon;
import com.mapzen.android.model.Polyline;
import com.mapzen.tangram.LngLat;
import com.mapzen.tangram.MapController;

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

    @Test public void getZoom_shouldInvokeMapController() throws Exception {
        when(mapController.getZoom()).thenReturn(15f);
        assertThat(map.getZoom()).isEqualTo(15);
    }

    @Test public void setPosition_shouldInvokeMapController() throws Exception {
        LngLat lngLat = new LngLat(0, 0);
        map.setPosition(lngLat);
        verify(mapController).setPosition(lngLat);
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

    @Test public void getRotation_shouldInvokeMapController() throws Exception {
        when(mapController.getRotation()).thenReturn(3.14f);
        assertThat(map.getRotation()).isEqualTo(3.14f);
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
}
