package com.mapzen.android.graphics;

import com.mapzen.tangram.LabelPickResult;

import android.support.annotation.NonNull;

/**
 * Listener invoked when a label on the map is selected.
 */
public interface LabelPickListener {

  /**
   * Receives information about labels found in a call to {@link
   * com.mapzen.tangram.MapController#pickLabel(float, float)}. Note that a label refers
   * to POI labels, search pins, route pins or more generally, any
   * {@link com.mapzen.tangram.MapData} drawn using point style. It does not include
   * {@link com.mapzen.android.graphics.model.Marker}s,
   * {@link com.mapzen.android.graphics.model.Polygon}, or
   * {@link com.mapzen.android.graphics.model.Polyline}.
   * To receive pick information for {@link com.mapzen.android.graphics.model.Marker}s, use the
   * {@link MarkerPickListener} interface and to receive pick information for
   * {@link com.mapzen.android.graphics.model.Polygon} or
   * {@link com.mapzen.android.graphics.model.Polyline}, use the {@link FeaturePickListener}
   * interface.
   *
   * @param result Object containing information about the selected label. Can be null.
   * @param positionX The horizontal screen coordinate of the center of the feature. Will be 0 if
   * {@link LabelPickResult} is null.
   * @param positionY The vertical screen coordinate of the center of the feature. Will be 0 if
   * {@link LabelPickResult} is null.
   */
  void onLabelPicked(@NonNull LabelPickResult result, float positionX, float positionY);
}
