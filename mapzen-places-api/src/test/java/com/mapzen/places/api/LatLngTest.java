package com.mapzen.places.api;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LatLngTest {

  LatLng latLng = new LatLng(40, 70);

  @Test public void shouldSetLat() {
    assertThat(latLng.getLatitude()).isEqualTo(40);
  }

  @Test public void shouldSetLng() {
    assertThat(latLng.getLongitude()).isEqualTo(70);
  }

  @Test public void shouldSetMinLat() {
    LatLng minLat = new LatLng(-100, 70);
    assertThat(minLat.getLatitude()).isEqualTo(-90);
  }

  @Test public void shouldSetMaxLat() {
    LatLng maxLat = new LatLng(100, 70);
    assertThat(maxLat.getLatitude()).isEqualTo(90);
  }

  @Test public void shouldSetMinLng() {
    LatLng minLng = new LatLng(40, -200);
    assertThat(minLng.getLongitude()).isEqualTo(-180);
  }

  @Test public void shouldSetMaxLng() {
    LatLng maxLng = new LatLng(40, 200);
    assertThat(maxLng.getLongitude()).isEqualTo(180);
  }
}