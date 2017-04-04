package com.mapzen.android.graphics;

import com.mapzen.android.graphics.model.BitmapMarker;

/**
 * Listener invoked when a marker on the map is selected.
 */
public interface MarkerPickListener {
  /**
   * Receives information about markers found in a call to {@link
   * com.mapzen.tangram.MapController#pickMarker(float, float)} (float, float)}.
   */
  void onMarkerPick(BitmapMarker marker);
}
