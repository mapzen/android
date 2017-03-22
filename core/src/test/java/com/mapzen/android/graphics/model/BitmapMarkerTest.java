package com.mapzen.android.graphics.model;

import com.mapzen.tangram.Marker;

import org.junit.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

public class BitmapMarkerTest {
  private Marker tangramMarker = Mockito.mock(Marker.class);
  private BitmapMarker bitmapMarker = new BitmapMarker(tangramMarker);

  @Test public void shouldNotBeNull() throws Exception {
    assertThat(bitmapMarker).isNotNull();
  }
}
