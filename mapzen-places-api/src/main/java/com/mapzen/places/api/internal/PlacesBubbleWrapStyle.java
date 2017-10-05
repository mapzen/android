package com.mapzen.places.api.internal;

import com.mapzen.android.graphics.model.BubbleWrapStyle;

/**
 * Style used for place picker UI. Sets highest label level as default label level.
 */
class PlacesBubbleWrapStyle extends BubbleWrapStyle {

  @Override public int getDefaultLabelLevel() {
    return 11;
  }
}
