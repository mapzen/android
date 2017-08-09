package com.mapzen.android.graphics.model;

import com.mapzen.tangram.LngLat;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MarkerOptionsTest {
  private MarkerOptions markerOptions = new MarkerOptions();

  @Test public void shouldNotBeNull() throws Exception {
    assertThat(markerOptions).isNotNull();
  }

  @Test public void shouldSetPosition() throws Exception {
    assertThat(markerOptions.position(new LngLat()).getPosition()).isEqualTo(new LngLat());
  }

  @Test public void shouldSetIcon() throws Exception {
    assertThat(markerOptions.icon(123).getIcon()).isEqualTo(123);
  }
}
