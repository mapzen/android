package com.mapzen.android.graphics.model;

import com.mapzen.tangram.MapController;
import com.mapzen.tangram.Marker;

public class MarkerManager {
  private final MapController mapController;

  public MarkerManager(MapController mapController) {
    this.mapController = mapController;
  }

  public BitmapMarker addMarker(MarkerOptions markerOptions) {
    final Marker marker = mapController.addMarker();
    marker.setPoint(markerOptions.getPosition());
    marker.setDrawable(markerOptions.getIcon());
    marker.setStylingFromString(markerOptions.getStyle());
    return new BitmapMarker(marker);
  }
}
