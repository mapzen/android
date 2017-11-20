package com.mapzen.android.graphics;

import com.mapzen.android.graphics.model.BitmapMarker;

import android.support.annotation.NonNull;

/**
 * Listener invoked when a marker on the map is selected. Note that all
 * {@link com.mapzen.android.graphics.model.Polygon}s,
 * {@link com.mapzen.android.graphics.model.Polyline}s, and
 * search/routing pins are not markers and will receive pick events via either the
 * {@link LabelPickListener} or {@link FeaturePickListener} interfaces.
 */
public interface MarkerPickListener {
  /**
   * Receives information about markers found in a call to {@link
   * com.mapzen.tangram.MapController#pickMarker(float, float)} (float, float)}.
   */
  void onMarkerPick(@NonNull BitmapMarker marker);
}
