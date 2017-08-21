package com.mapzen.android.graphics.model;

import com.mapzen.android.graphics.internal.StyleStringGenerator;

/**
 * Creates {@link BitmapMarker} objects.
 */
public class BitmapMarkerFactory {

  /**
   * Creates new {@link BitmapMarker} with the given parameters.
   * @param manager
   * @param marker
   * @param styleStringGenerator
   * @return
   */
  BitmapMarker createMarker(MarkerManager manager, com.mapzen.tangram.Marker marker,
      StyleStringGenerator styleStringGenerator) {
    return new BitmapMarker(manager, marker, styleStringGenerator);
  }
}
