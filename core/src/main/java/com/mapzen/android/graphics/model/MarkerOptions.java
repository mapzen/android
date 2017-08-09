package com.mapzen.android.graphics.model;

import com.mapzen.R;
import com.mapzen.tangram.LngLat;

/**
 * Defines options for a {@link BitmapMarker}.
 */
public class MarkerOptions {
  private static final LngLat DEFAULT_POSITION = new LngLat(-73.985428, 40.748817);
  private static final int DEFAULT_DRAWABLE = R.drawable.mapzen;
  //private static final String DEFAULT_STYLE = "{ style: 'points', color: 'white',"
  //    + "size: [50px, 50px], collide: false, interactive: true }";
  private static final int DEFAULT_WIDTH = 50;
  private static final int DEFAULT_HEIGHT = 50;

  private LngLat position = DEFAULT_POSITION;
  private int resId = DEFAULT_DRAWABLE;
  //private String style = DEFAULT_STYLE;
  private int width = DEFAULT_WIDTH;
  private int height = DEFAULT_HEIGHT;

  // Setters

  /**
   * Set the marker position.
   *
   * @param position coordinate to display the marker.
   * @return this marker options instance.
   */
  public MarkerOptions position(LngLat position) {
    this.position = position;
    return this;
  }

  /**
   * Set the marker icon resource ID.
   *
   * @param resId drawable resource ID for the marker to display.
   * @return this marker options instance.
   */
  public MarkerOptions icon(int resId) {
    this.resId = resId;
    return this;
  }

  /**
   * Set the marker size.
   *
   * @param width in pixels
   * @param height in pixels
   * @return this marker options instance.
   */
  public MarkerOptions size(int width, int height) {
    this.width = width;
    this.height = height;
    return this;
  }

  // Getters

  public LngLat getPosition() {
    return position;
  }

  public int getIcon() {
    return resId;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }
}
