package com.mapzen.android.graphics.model;

import com.mapzen.android.graphics.internal.StyleStringGenerator;
import com.mapzen.tangram.LngLat;
import com.mapzen.tangram.MapController;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@SuppressStaticInitializationFor("com.mapzen.tangram.MapController")
public class MarkerManagerTest {
  private MapController mapController = mock(MapController.class);
  private com.mapzen.tangram.Marker tangramMarker = mock(com.mapzen.tangram.Marker.class);
  private MarkerManager markerManager = new MarkerManager(mapController);

  @Before public void setUp() throws Exception {
    when(mapController.addMarker()).thenReturn(tangramMarker);
  }

  @Test public void shouldNotBeNull() throws Exception {
    assertThat(markerManager).isNotNull();
  }

  @Test public void addMarker_shouldAddMarkerToMapController() throws Exception {
    markerManager.addMarker(new MarkerOptions());
    verify(mapController).addMarker();
  }

  @Test public void addMarker_shouldSetPosition() throws Exception {
    LngLat lngLat = new LngLat();
    MarkerOptions markerOptions = new MarkerOptions().position(lngLat);
    markerManager.addMarker(markerOptions);
    verify(tangramMarker).setPoint(lngLat);
  }

  @Test public void addMarker_shouldSetDrawable() throws Exception {
    int resId = 123;
    MarkerOptions markerOptions = new MarkerOptions().icon(resId);
    markerManager.addMarker(markerOptions);
    verify(tangramMarker).setDrawable(resId);
  }

  @Test public void addMarker_shouldSetStyling() throws Exception {
    MarkerOptions markerOptions = new MarkerOptions();
    markerManager.addMarker(markerOptions);
    verify(tangramMarker).setStylingFromString(anyString());
  }

  @Test public void addMarker_shouldSetSize() throws Exception {
    MarkerOptions markerOptions = new MarkerOptions().size(100, 100);
    markerManager.addMarker(markerOptions);
    verify(tangramMarker).setStylingFromString("{ style: 'points', color: '#FFFFFF', "
        + "size: [100px, 100px], collide: false, interactive: true }");
  }

  @Test public void addMarker_shouldReturnBitmapMarker() throws Exception {
    assertThat(markerManager.addMarker(new MarkerOptions())).isNotNull();
  }
}
