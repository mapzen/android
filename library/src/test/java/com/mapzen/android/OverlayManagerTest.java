package com.mapzen.android;

import com.mapzen.android.lost.api.LostApiClient;
import com.mapzen.tangram.LngLat;
import com.mapzen.tangram.MapController;
import com.mapzen.tangram.TestMapController;

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

@RunWith(PowerMockRunner.class)
@SuppressStaticInitializationFor("com.mapzen.tangram.MapController")
@PrepareForTest(OverlayManager.class)
public class OverlayManagerTest {

    private MapController mapController;
    private OverlayManager mapManager;

    @Before
    public void setup() throws Exception {
        mapController = PowerMockito.mock(TestMapController.class);
        LostApiClient lostApiClient = PowerMockito.mock(LostApiClient.class);
        mapManager = PowerMockito.spy(new OverlayManager(mapController, lostApiClient));
        PowerMockito.doNothing().when(mapManager, "addCurrentLocationMapDataToMap");
        PowerMockito.doNothing().when(mapManager, "handleMyLocationEnabledChanged");
    }

    @Test
    public void setMyLocationEnabled_shouldCenterMapOnCurrentLocation() throws Exception {
        Mockito.doCallRealMethod().when(mapController).setPosition(any(LngLat.class), anyInt());
        Mockito.when(mapController.getPosition()).thenCallRealMethod();

        mapManager.setMyLocationEnabled(true);
        Location location = new Location("test");
        location.setLongitude(-40.0);
        location.setLatitude(-70.0);
        Whitebox.invokeMethod(mapManager.locationListener, "onLocationChanged", location);
        assertThat(mapController.getPosition().latitude).isEqualTo(location.getLatitude());
        assertThat(mapController.getPosition().longitude).isEqualTo(location.getLongitude());
    }

    @Test
    public void setMyLocationEnabled_shouldNotChangeZoomLevel() throws Exception {
        Mockito.doCallRealMethod().when(mapController).setPosition(any(LngLat.class), anyInt());
        Mockito.doCallRealMethod().when(mapController).setZoom(anyFloat());
        Mockito.when(mapController.getZoom()).thenCallRealMethod();

        mapController.setZoom(17);
        mapManager.setMyLocationEnabled(true);
        Location location = new Location("test");
        Whitebox.invokeMethod(mapManager.locationListener, "onLocationChanged", location);
        assertThat(mapController.getZoom()).isEqualTo(17);
    }

    @Test
    public void setMyLocationEnabled_shouldNotChangeTilt() throws Exception {
        Mockito.doCallRealMethod().when(mapController).setPosition(any(LngLat.class), anyInt());
        Mockito.doCallRealMethod().when(mapController).setTilt(anyFloat());
        Mockito.when(mapController.getTilt()).thenCallRealMethod();

        mapController.setTilt(8);
        mapManager.setMyLocationEnabled(true);
        Location location = new Location("test");
        Whitebox.invokeMethod(mapManager.locationListener, "onLocationChanged", location);
        assertThat(mapController.getTilt()).isEqualTo(8);
    }

    @Test
    public void setMyLocationEnabled_shouldNotChangeRotation() throws Exception {
        Mockito.doCallRealMethod().when(mapController).setPosition(any(LngLat.class), anyInt());
        Mockito.doCallRealMethod().when(mapController).setRotation(anyFloat());
        Mockito.when(mapController.getRotation()).thenCallRealMethod();

        mapController.setRotation(8);
        mapManager.setMyLocationEnabled(true);
        Location location = new Location("test");
        Whitebox.invokeMethod(mapManager.locationListener, "onLocationChanged", location);
        assertThat(mapController.getRotation()).isEqualTo(8);
    }
}
