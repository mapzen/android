package com.mapzen.places.api.internal;

import com.mapzen.pelias.BoundingBox;
import com.mapzen.places.api.LatLng;
import com.mapzen.places.api.LatLngBounds;

/**
 * Constructs a {@link LatLngBounds} given a {@link LatLng}.
 */
public class PointToBoundsConverter {

  private static final double BOUNDS_RADIUS = 0.02;

  /**
   * Creates a bounding box with reasonable radius from the given point.
   * @param point
   * @return
   */
  public LatLngBounds boundsFromPoint(LatLng point) {
    double minLat = point.getLatitude() - BOUNDS_RADIUS;
    double minLon = point.getLongitude() - BOUNDS_RADIUS;
    double maxLat = point.getLatitude() + BOUNDS_RADIUS;
    double maxLon = point.getLongitude() + BOUNDS_RADIUS;
    LatLng sw = new LatLng(minLat, minLon);
    LatLng ne = new LatLng(maxLat, maxLon);
    return new LatLngBounds(sw, ne);
  }

  /**
   * Creates a bounding box with reasonable radius from the given point information.
   * @param latitude
   * @param longitude
   * @return
   */
  public BoundingBox boundingBoxFromPoint(double latitude, double longitude) {
    double minLat = latitude - BOUNDS_RADIUS;
    double minLon = longitude - BOUNDS_RADIUS;
    double maxLat = latitude + BOUNDS_RADIUS;
    double maxLon = longitude + BOUNDS_RADIUS;
    return new BoundingBox(minLat, minLon, maxLat, maxLon);
  }
}
