package com.mapzen.android.graphics.model;

import com.mapzen.tangram.MapController;
import com.mapzen.tangram.Marker;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@SuppressStaticInitializationFor("com.mapzen.tangram.MapController")
public class BitmapMarkerTest {
  private Marker tangramMarker = mock(Marker.class);
  private MapController mapController = mock(MapController.class);
  private MarkerManager markerManager = new MarkerManager(mapController);
  private BitmapMarker bitmapMarker = new BitmapMarker(markerManager, tangramMarker);

  @Test public void shouldNotBeNull() throws Exception {
    assertThat(bitmapMarker).isNotNull();
  }

  @Test public void remove_shouldRemoveMarkerFromMapController() throws Exception {
    bitmapMarker.remove();
    verify(mapController).removeMarker(tangramMarker);
  }
}
