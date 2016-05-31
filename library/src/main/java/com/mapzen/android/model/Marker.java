package com.mapzen.android.model;

import com.mapzen.tangram.LngLat;

/**
 * Represents a pin marker on a map.
 */
public class Marker {

  private final LngLat latLng;

  /**
   * Constructs a new object.
   */
  public Marker(double longitude, double latitude) {
    this.latLng = new LngLat(longitude, latitude);
  }

  /**
   * Return the marker's coordinate location.
   */
  public LngLat getLocation() {
    return latLng;
  }

  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Marker marker = (Marker) o;

    return !(latLng != null ? !latLng.equals(marker.latLng) : marker.latLng != null);
  }

  @Override public int hashCode() {
    return latLng != null ? latLng.hashCode() : 0;
  }
}
