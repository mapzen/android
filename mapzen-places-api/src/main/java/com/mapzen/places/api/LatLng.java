package com.mapzen.places.api;

/**
 * Represents a pair of coordinates as degrees.
 */
public class LatLng {

  private final double LAT_MIN = -90;
  private final double LAT_MAX = 90;
  private final double LNG_MIN = -180;
  private final double LNG_MAX = 180;

  private final double latitude;
  private final double longitude;

  public LatLng(double lat, double lng) {
    if (lat < LAT_MIN) {
      lat = LAT_MIN;
    }
    if (lat > LAT_MAX) {
      lat = LAT_MAX;
    }
    if (lng < LNG_MIN) {
      lng = LNG_MIN;
    }
    if (lng > LNG_MAX) {
      lng = LNG_MAX;
    }
    latitude = lat;
    longitude = lng;
  }

  /**
   * Latitude, in degrees. This value is in the range [-90, 90].
   * @return
   */
  public double getLatitude() {
    return latitude;
  }

  /**
   * Longitude, in degrees. This value is in the range [-180, 180].
   * @return
   */
  public double getLongitude() {
    return longitude;
  }
}
