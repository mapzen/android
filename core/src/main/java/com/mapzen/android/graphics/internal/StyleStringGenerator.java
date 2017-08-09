package com.mapzen.android.graphics.internal;

/**
 * Handles updating properties used to generate a style string for a Tangram
 * {@link com.mapzen.tangram.Marker}. Used directly by
 * {@link com.mapzen.android.graphics.model.BitmapMarker}.
 */
public class StyleStringGenerator {

  private int width = 50;
  private int height = 50;
  private boolean interactive = true;
  private String bgHex = "#FFFFFF";

  /**
   * Set the width and height in pixels.
   * @param width
   * @param height
   */
  public void setSize(int width, int height) {
    this.width = width;
    this.height = height;
  }

  /**
   * Set whether or not the marker can be selected.
   * @param interactive
   */
  public void setInteractive(boolean interactive) {
    this.interactive = interactive;
  }

  /**
   * Sets the hex value for background color to be used.
   * @param hex
   */
  public void setBackgroundColor(String hex) {
    this.bgHex = hex;
  }

  /**
   * Return the style string given the current property configurations.
   * @return
   */
  public String getStyleString() {
    return new StringBuilder()
        .append("{ style: 'points', color: '")
        .append(bgHex)
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
