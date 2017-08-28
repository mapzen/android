package com.mapzen.android.graphics.model;

import com.mapzen.R;
import com.mapzen.tangram.LngLat;

import android.graphics.drawable.Drawable;

/**
 * Defines options for a {@link BitmapMarker}.
 */
public class BitmapMarkerOptions {
  private static final LngLat DEFAULT_POSITION = new LngLat(-73.985428, 40.748817);
  private static final int DEFAULT_DRAWABLE = R.drawable.mapzen;
  private static final int DEFAULT_WIDTH = 50;
  private static final int DEFAULT_HEIGHT = 50;
  private static final int RES_NONE = Integer.MIN_VALUE;

  private LngLat position = DEFAULT_POSITION;
  private int resId = DEFAULT_DRAWABLE;
  private Drawable res = null;
  private int width = DEFAULT_WIDTH;
  private int height = DEFAULT_HEIGHT;
  private boolean isVisible = true;
  private int drawOrder = 1;
  private Object userData;
  private int colorInt = Integer.MIN_VALUE;
  private String colorHex = "#fff";
  private boolean isInteractive = true;

  // Setters

  /**
   * Set the marker position.
   *
   * @param position coordinate to display the marker.
   * @return this marker options instance.
   */
  public BitmapMarkerOptions position(LngLat position) {
    this.position = position;
    return this;
  }

  /**
   * Set the marker icon resource ID. Setting this property will override previously set resource
   * ids set in the call to {@link BitmapMarkerOptions#icon(Drawable)}.
   *
   * @param resId drawable resource ID for the marker to display.
   * @return this marker options instance.
   */
  public BitmapMarkerOptions icon(int resId) {
    this.resId = resId;
    this.res = null;
    return this;
  }

  /**
   * Set the marker icon resource. Setting this property will override previously set resource ids
   * set in the call to {@link BitmapMarkerOptions#icon(int)}.
   *
   * @param res drawable resource for the marker to display.
   * @return this marker options instance.
   */
  public BitmapMarkerOptions icon(Drawable res) {
    this.res = res;
    this.resId = RES_NONE;
    return this;
  }

  /**
   * Set the marker size.
   *
   * @param width in pixels
   * @param height in pixels
   * @return this marker options instance.
   */
  public BitmapMarkerOptions size(int width, int height) {
    this.width = width;
    this.height = height;
    return this;
  }

  /**
   * Sets the marker visibility.
   * @param isVisible
   * @return
   */
  public BitmapMarkerOptions visible(boolean isVisible) {
    this.isVisible = isVisible;
    return this;
  }

  /**
   * Sets marker z-axis draw order.
   */
  public BitmapMarkerOptions drawOrder(int drawOrder) {
    this.drawOrder = drawOrder;
    return this;
  }

  /**
   * Sets extra data to be associated with the marker.
   * @param userData
   * @return
   */
  public BitmapMarkerOptions userData(Object userData) {
    this.userData = userData;
    return this;
  }

  /**
   * Sets color resource int. Setting the color in overrides previous set color hex values set via
   * {@link BitmapMarkerOptions#colorHex(String)}.
   * @param colorInt
   * @return
   */
  public BitmapMarkerOptions colorInt(int colorInt) {
    this.colorInt = colorInt;
    this.colorHex = null;
    return this;
  }

  /**
   * Sets color hex value. Setting the color in overrides previous set color int values set via
   * {@link BitmapMarkerOptions#colorInt(int)}.
   * @param colorHex
   * @return
   */
  public BitmapMarkerOptions colorHex(String colorHex) {
    this.colorHex = colorHex;
    this.colorInt = Integer.MIN_VALUE;
    return this;
  }

  /**
   * Sets whether or not the marker is interactive.
   * @param isInteractive
   * @return
   */
  public BitmapMarkerOptions interactive(boolean isInteractive) {
    this.isInteractive = isInteractive;
    return this;
  }

  // Getters

  public LngLat getPosition() {
    return position;
  }

  public int getIcon() {
    return resId;
  }

  public Drawable getIconDrawable() {
    return res;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public boolean isVisible() {
    return isVisible;
  }

  public int getDrawOrder() {
    return drawOrder;
  }

  public Object getUserData() {
    return userData;
  }

  public int getColorInt() {
    return colorInt;
  }

  public String getColorHex() {
    return colorHex;
  }

  public boolean isInteractive() {
    return isInteractive;
  }
}
