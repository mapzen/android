package com.mapzen.android.graphics;

import com.mapzen.tangram.LabelPickResult;

/**
 * Listener invoked when a label on the map is selected.
 */
public interface LabelPickListener {

  /**
   * Receives information about labels found in a call to {@link
   * com.mapzen.tangram.MapController#pickLabel(float, float)}.
   *
   * @param result Object containing information about the selected label. Can be null.
   * @param positionX The horizontal screen coordinate of the center of the feature. Will be 0 if
   * {@link LabelPickResult} is null.
   * @param positionY The vertical screen coordinate of the center of the feature. Will be 0 if
   * {@link LabelPickResult} is null.
   */
  void onLabelPicked(LabelPickResult result, float positionX, float positionY);
}
