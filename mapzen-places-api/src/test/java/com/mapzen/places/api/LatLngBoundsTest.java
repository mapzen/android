package com.mapzen.places.api;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LatLngBoundsTest {

  LatLng southWestPoint = new LatLng(-50, -90);
  LatLng northEastPoint = new LatLng(40, 80);
  LatLngBounds bounds = new LatLngBounds(southWestPoint, northEastPoint);

  @Test public void shouldSetNorthEastPoint() {
    assertThat(bounds.getNortheast()).isEqualTo(northEastPoint);
  }

  @Test public void shouldSetSouthWestPoint() {
    assertThat(bounds.getSouthwest()).isEqualTo(southWestPoint);
  }

  @Test public void include_shouldSetNorthEastPoint() {
    LatLng pointA = new LatLng(40, 50);
    LatLng pointB = new LatLng(-40, -50);
    LatLngBounds bounds = new LatLngBounds.Builder()
        .include(pointA)
        .include(pointB)
        .build();
    assertThat(bounds.getNortheast().getLatitude()).isEqualTo(40);
    assertThat(bounds.getNortheast().getLongitude()).isEqualTo(50);
  }

  @Test public void include_shouldSetSouthWestPoint() {
    LatLng pointA = new LatLng(40, 50);
    LatLng pointB = new LatLng(-40, -50);
    LatLngBounds bounds = new LatLngBounds.Builder()
        .include(pointA)
        .include(pointB)
        .build();
    assertThat(bounds.getSouthwest().getLatitude()).isEqualTo(-40);
    assertThat(bounds.getSouthwest().getLongitude()).isEqualTo(-50);
  }

  @Test public void contains_shouldReturnTrue() {
    LatLng pointA = new LatLng(40, 50);
    LatLng pointB = new LatLng(-40, -50);
    LatLngBounds bounds = new LatLngBounds.Builder()
        .include(pointA)
        .include(pointB)
        .build();
    LatLng point = new LatLng(20, 20);
    assertThat(bounds.contains(point)).isTrue();
  }

  @Test public void contains_shouldReturnFalse() {
    LatLng pointA = new LatLng(40, 50);
    LatLng pointB = new LatLng(-40, -50);
    LatLngBounds bounds = new LatLngBounds.Builder()
        .include(pointA)
        .include(pointB)
        .build();
    LatLng point = new LatLng(20, 120);
    assertThat(bounds.contains(point)).isFalse();
  }

  @Test public void including_shouldReturnCorrectBounds() {
    LatLng pointA = new LatLng(40, 50);
    LatLng pointB = new LatLng(-40, -50);
    LatLngBounds bounds = new LatLngBounds.Builder()
        .include(pointA)
        .include(pointB)
        .build();
    LatLng point = new LatLng(30, 100);
    LatLngBounds containing = bounds.including(point);
    assertThat(containing.getNortheast().getLatitude()).isEqualTo(40);
    assertThat(containing.getNortheast().getLongitude()).isEqualTo(100);
    assertThat(containing.getSouthwest().getLatitude()).isEqualTo(-40);
    assertThat(containing.getSouthwest().getLongitude()).isEqualTo(-50);
  }

  @Test public void getCenter_shouldReturnCorrectCenter() {
    LatLng pointA = new LatLng(40, 50);
    LatLng pointB = new LatLng(-40, -50);
    LatLngBounds bounds = new LatLngBounds.Builder()
        .include(pointA)
        .include(pointB)
        .build();
    LatLng center = bounds.getCenter();
    assertThat(center.getLatitude()).isEqualTo(0);
    assertThat(center.getLongitude()).isEqualTo(0);
  }
}
