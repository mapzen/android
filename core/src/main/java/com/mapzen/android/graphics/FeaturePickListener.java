package com.mapzen.android.graphics;

import java.util.Map;

/**
 * Interface for a callback to receive information about features picked from the map.
 */
public interface FeaturePickListener {
  /**
   * Receive information about features found in a call to {@link #pickFeature(float, float)}.
   *
   * @param properties A mapping of string keys to string or number values
   * @param positionX The horizontal screen coordinate of the center of the feature
   * @param positionY The vertical screen coordinate of the center of the feature
   */
  void onFeaturePick(Map<String, String> properties, float positionX, float positionY);
}
