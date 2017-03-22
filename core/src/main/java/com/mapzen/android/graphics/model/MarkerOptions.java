package com.mapzen.android.graphics.model;

import com.mapzen.R;
import com.mapzen.tangram.LngLat;

/**
 * Defines options for a {@link BitmapMarker}.
 */
public class MarkerOptions {
  private static final LngLat DEFAULT_POSITION = new LngLat(-73.985428, 40.748817);
  private static final int DEFAULT_DRAWABLE = R.drawable.mapzen;
  private static final String DEFAULT_STYLE = "{ style: 'points', color: 'white',"
      + "size: [50px, 50px], collide: false, interactive: true }";

  private LngLat position = DEFAULT_POSITION;
  private int resId = DEFAULT_DRAWABLE;
  private String style = DEFAULT_STYLE;

  // Setters

  public MarkerOptions position(LngLat position) {
    this.position = position;
    return this;
  }

  public MarkerOptions icon(int resId) {
    this.resId = resId;
    return this;
  }

  public MarkerOptions style(String style) {
    this.style = style;
    return this;
  }

  // Getters

  public LngLat getPosition() {
    return position;
  }

  public int getIcon() {
    return resId;
  }

  public String getStyle() {
    return style;
  }
}
