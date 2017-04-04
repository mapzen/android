package com.mapzen.android.graphics.model;

import com.mapzen.tangram.MapController;
import com.mapzen.tangram.Marker;

/**
 * Manages {@link BitmapMarker} instances on the map.
 */
public class MarkerManager {
  private final MapController mapController;

  /**
   * Constructor.
   *
   * @param mapController Tangram map controller used to generate markers.
   */
  public MarkerManager(MapController mapController) {
    this.mapController = mapController;
  }

  /**
   * Adds a new marker to the map.
   *
   * @param markerOptions options that define the appearance of the marker.
   * @return a new bitmap marker wrapper for the Tangram marker object.
   */
  public BitmapMarker addMarker(MarkerOptions markerOptions) {
    final Marker marker = mapController.addMarker();
    marker.setPoint(markerOptions.getPosition());
    marker.setDrawable(markerOptions.getIcon());
    marker.setStylingFromString(markerOptions.getStyle());
    return new BitmapMarker(this, marker);
  }

  /**
   * Removes a marker from the map.
   *
   * @param marker Tangram marker to be removed.
   */
  public void removeMarker(Marker marker) {
    mapController.removeMarker(marker);
  }
}
