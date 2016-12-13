package com.mapzen.places.api;

/**
 * Represents a pair of coordinates as degrees.
 */
public class LatLng {

  private static final double LAT_MIN = -90;
  private static final double LAT_MAX = 90;
  private static final double LNG_MIN = -180;
  private static final double LNG_MAX = 180;
  private static final double ALL_LNGS = 360;

  private final double latitude;
  private final double longitude;

  /**
   * Constructs a new object given a latitude and longitude in degrees.
   * @param lat
   * @param lng
   */
  public LatLng(double lat, double lng) {
    if (LNG_MIN <= lng && lng < LNG_MAX) {
      this.longitude = lng;
    } else {
      this.longitude = ((lng - LNG_MAX) % ALL_LNGS + ALL_LNGS) % ALL_LNGS - LNG_MAX;
    }
    this.latitude = Math.max(LAT_MIN, Math.min(LAT_MAX, lat));
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
