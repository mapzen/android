package com.mapzen.places.api.internal;

import com.mapzen.places.api.Places;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PlacesTest {

  @Test public void geoDataApiShouldNotBeNull() {
    assertThat(Places.GeoDataApi).isNotNull();
  }

  @Test public void geoDataApiShouldBeOfCorrectClass() {
    assertThat(Places.GeoDataApi).isInstanceOf(GeoDataApiImpl.class);
  }
}
