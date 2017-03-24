package com.mapzen.android.graphics.model;

import com.mapzen.tangram.Marker;

/**
 * Dynamic marker overlay constructed using a local bitmap.
 */
public class BitmapMarker {
  private final MarkerManager markerManager;
  private final Marker tangramMarker;

  /**
   * Constructor that wraps a Tangram marker.
   *
   * @param tangramMarker the underlying Tangram marker object.
   */
  public BitmapMarker(MarkerManager markerManager, Marker tangramMarker) {
    this.markerManager = markerManager;
    this.tangramMarker = tangramMarker;
  }

  /**
   * Removes this marker from the map. After a marker has been removed, the behavior of all its
   * methods is undefined.
   */
  public void remove() {
    markerManager.removeMarker(tangramMarker);
  }
}
