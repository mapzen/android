package com.mapzen.android.graphics;

import android.support.annotation.NonNull;

import java.util.Map;

/**
 * Interface for a callback to receive information about features picked from the map.
 */
public interface FeaturePickListener {
  /**
   * Receive information about features found in a call to
   * {@link com.mapzen.tangram.MapController#pickFeature(float, float)}. Note that a feature refers
   * to a {@link com.mapzen.android.graphics.model.Polygon} or
   * {@link com.mapzen.android.graphics.model.Polyline} and is any
   * {@link com.mapzen.tangram.MapData} drawn using non-point style. It does not include
   * {@link com.mapzen.android.graphics.model.Marker}s or points, including MapData drawn using
   * points style. To receive pick information for
   * {@link com.mapzen.android.graphics.model.Marker}s, use the {@link MarkerPickListener} interface
   * and to receive pick information for points, including MapData drawn using points style, use the
   * {@link FeaturePickListener} interface.
   *
   * @param properties A mapping of string keys to string or number values
   * @param positionX The horizontal screen coordinate of the center of the feature
   * @param positionY The vertical screen coordinate of the center of the feature
   */
  void onFeaturePick(@NonNull Map<String, String> properties, float positionX, float positionY);
}
