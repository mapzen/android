package com.mapzen.places.api.internal;

import com.mapzen.pelias.BoundingBox;
import com.mapzen.places.api.LatLng;
import com.mapzen.places.api.LatLngBounds;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PointToBoundsConverterTest {

  private PointToBoundsConverter pointConverter = new PointToBoundsConverter();

  @Test public void boundsFromPoint_shouldCreateCorrectBounds() throws Exception {
    LatLng point = new LatLng(40.0, 70.0);
    LatLngBounds bounds = pointConverter.boundsFromPoint(point);
    assertThat(bounds.getCenter().getLatitude()).isEqualTo(40.0);
    assertThat(bounds.getCenter().getLongitude()).isEqualTo(70.0);
    assertThat(bounds.getSouthwest().getLatitude()).isEqualTo(39.98);
    assertThat(bounds.getSouthwest().getLongitude()).isEqualTo(69.98);
    assertThat(bounds.getNortheast().getLatitude()).isEqualTo(40.02);
    assertThat(bounds.getNortheast().getLongitude()).isEqualTo(70.02);
  }

  @Test public void boundingBoxFromPoint_shouldCreateCorrectBounds() throws Exception {
    BoundingBox bounds = pointConverter.boundingBoxFromPoint(40.0, 70.0);
    assertThat(bounds.getMinLat()).isEqualTo(39.98);
    assertThat(bounds.getMinLon()).isEqualTo(69.98);
    assertThat(bounds.getMaxLat()).isEqualTo(40.02);
    assertThat(bounds.getMaxLon()).isEqualTo(70.02);
  }
}
