package com.mapzen.android.graphics.internal;

/**
 * Handles updating properties used to generate a style string for a Tangram
 * {@link com.mapzen.tangram.Marker}. Used directly by
 * {@link com.mapzen.android.graphics.model.BitmapMarker}.
 */
public class StyleStringGenerator {

  /**
   * Return the style string given the current property configurations.
   * @return
   */
  public String getStyleString(int width, int height, boolean interactive, String colorHex) {
    return new StringBuilder()
        .append("{ style: 'points', color: '")
        .append(colorHex)
        .append("', size: [")
        .append(width)
        .append("px, ")
        .append(height)
        .append("px], ")
        .append("collide: false, interactive: ")
        .append(interactive)
        .append(" }")
        .toString();
  }
}
