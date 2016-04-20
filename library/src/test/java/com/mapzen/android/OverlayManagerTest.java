package com.mapzen.android;

import com.mapzen.android.lost.api.LostApiClient;
import com.mapzen.android.model.Marker;
import com.mapzen.android.model.Polygon;
import com.mapzen.android.model.Polyline;
import com.mapzen.tangram.LngLat;
import com.mapzen.tangram.MapController;
import com.mapzen.tangram.MapData;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import android.location.Location;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;


@RunWith(PowerMockRunner.class)
@SuppressStaticInitializationFor("com.mapzen.tangram.MapController")
@PrepareForTest(OverlayManager.class)
public class OverlayManagerTest {

    private MapController mapController;
    private OverlayManager overlayManager;

    @Before
    public void setup() throws Exception {
        mapController = PowerMockito.mock(TestMapController.class);
        LostApiClient lostApiClient = PowerMockito.mock(LostApiClient.class);
        MapView mapView = PowerMockito.mock(MapView.class);
        overlayManager = PowerMockito.spy(new OverlayManager(mapController, lostApiClient,
                mapView));
        PowerMockito.doNothing().when(overlayManager, "initCurrentLocationMapData");
        PowerMockito.doNothing().when(overlayManager, "handleMyLocationEnabledChanged");
    }

    @Test
    public void setMyLocationEnabled_shouldCenterMapOnCurrentLocation() throws Exception {
        Mockito.doCallRealMethod().when(mapController).setPosition(any(LngLat.class), anyInt());
        Mockito.when(mapController.getPosition()).thenCallRealMethod();

        overlayManager.setMyLocationEnabled(true);
        Location location = new Location("test");
        location.setLongitude(-40.0);
        location.setLatitude(-70.0);
        Whitebox.invokeMethod(overlayManager.locationListener, "onLocationChanged", location);
        assertThat(mapController.getPosition().latitude).isEqualTo(location.getLatitude());
        assertThat(mapController.getPosition().longitude).isEqualTo(location.getLongitude());
    }

    @Test
    public void setMyLocationEnabled_shouldNotChangeZoomLevel() throws Exception {
        Mockito.doCallRealMethod().when(mapController).setPosition(any(LngLat.class), anyInt());
        Mockito.doCallRealMethod().when(mapController).setZoom(anyFloat());
        Mockito.when(mapController.getZoom()).thenCallRealMethod();

        mapController.setZoom(17);
        overlayManager.setMyLocationEnabled(true);
        Location location = new Location("test");
        Whitebox.invokeMethod(overlayManager.locationListener, "onLocationChanged", location);
        assertThat(mapController.getZoom()).isEqualTo(17);
    }

    @Test
    public void setMyLocationEnabled_shouldNotChangeTilt() throws Exception {
        Mockito.doCallRealMethod().when(mapController).setPosition(any(LngLat.class), anyInt());
        Mockito.doCallRealMethod().when(mapController).setTilt(anyFloat());
        Mockito.when(mapController.getTilt()).thenCallRealMethod();

        mapController.setTilt(8);
        overlayManager.setMyLocationEnabled(true);
        Location location = new Location("test");
        Whitebox.invokeMethod(overlayManager.locationListener, "onLocationChanged", location);
        assertThat(mapController.getTilt()).isEqualTo(8);
    }

    @Test
    public void setMyLocationEnabled_shouldNotChangeRotation() throws Exception {
        Mockito.doCallRealMethod().when(mapController).setPosition(any(LngLat.class), anyInt());
        Mockito.doCallRealMethod().when(mapController).setRotation(anyFloat());
        Mockito.when(mapController.getRotation()).thenCallRealMethod();

        mapController.setRotation(8);
        overlayManager.setMyLocationEnabled(true);
        Location location = new Location("test");
        Whitebox.invokeMethod(overlayManager.locationListener, "onLocationChanged", location);
        assertThat(mapController.getRotation()).isEqualTo(8);
    }

    @Test
    public void addPolyline_shouldReturnMapData() throws Exception {
        PowerMockito.doReturn(Mockito.mock(MapData.class)).when(mapController, "addDataLayer",
                anyString());
        PowerMockito.doReturn(Mockito.mock(MapData.class)).when(overlayManager,
                "addPolylineToPolylineMapData", anyList());

        Polyline polyline = new Polyline.Builder()
                .add(new LngLat(-73.9903, 40.74433))
                .add(new LngLat(-73.984770, 40.734807))
                .add(new LngLat(-73.998674, 40.732172))
                .add(new LngLat(-73.996142, 40.741050))
                .build();
        MapData polylineData = overlayManager.addPolyline(polyline);
        assertThat(polylineData).isNotNull();
    }

    @Test(expected = IllegalArgumentException.class)
    public void addPolyline_shouldRequireTwoPoints() {
        Polyline polyline = new Polyline.Builder()
                .add(new LngLat(-73.9903, 40.74433))
                .build();
        overlayManager.addPolyline(null);
        overlayManager.addPolyline(polyline);
    }

    @Test
    public void addPolygon_shouldReturnMapData() throws Exception {
        PowerMockito.doReturn(Mockito.mock(MapData.class)).when(mapController, "addDataLayer",
                anyString());
        PowerMockito.doReturn(Mockito.mock(MapData.class)).when(overlayManager,
                "addPolygonToPolygonMapData", anyList());

        Polygon polygon = new Polygon.Builder()
                .add(new LngLat(-73.9903, 40.74433))
                .add(new LngLat(-73.984770, 40.734807))
                .add(new LngLat(-73.998674, 40.732172))
                .add(new LngLat(-73.996142, 40.741050))
                .build();
        MapData polygonData = overlayManager.addPolygon(polygon);
        assertThat(polygonData).isNotNull();
    }

    @Test(expected = IllegalArgumentException.class)
    public void addPolygon_shouldRequireTwoPoints() {
        Polygon polygon = new Polygon.Builder()
                .add(new LngLat(-73.9903, 40.74433))
                .build();
        overlayManager.addPolygon(null);
        overlayManager.addPolygon(polygon);
    }

    @Test
    public void addMarker_shouldReturnMapData() throws Exception {
        PowerMockito.doReturn(Mockito.mock(MapData.class)).when(mapController, "addDataLayer",
                anyString());
        PowerMockito.doReturn(Mockito.mock(MapData.class)).when(overlayManager,
                "addPointToMarkerMapData", any(Marker.class));

        Marker marker = new Marker(-73.9903, 40.74433);
        MapData markerMapData = overlayManager.addMarker(marker);
        assertThat(markerMapData).isNotNull();
    }

    @Test(expected = IllegalArgumentException.class)
    public void addMarker_shouldRequirePoint() {
        MapData markerMapData = overlayManager.addMarker(null);
        assertThat(markerMapData).isNotNull();
    }
}
