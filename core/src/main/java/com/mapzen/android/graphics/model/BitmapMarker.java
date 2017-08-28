package com.mapzen.android.graphics.model;

import com.mapzen.android.graphics.internal.EaseTypeConverter;
import com.mapzen.android.graphics.internal.StyleStringGenerator;
import com.mapzen.tangram.LngLat;
import com.mapzen.tangram.Marker;

import android.graphics.drawable.Drawable;

/**
 * Dynamic marker overlay constructed using a local bitmap.
 */
public class BitmapMarker {

  private final BitmapMarkerManager bitmapMarkerManager;
  private Marker tangramMarker;
  private final StyleStringGenerator styleStringGenerator;
  private LngLat position;
  private int resourceId;
  private Drawable drawable;
  private int width;
  private int height;
  private boolean isVisible;
  private int drawOrder;
  private int colorInt;
  private String colorHex;
  private boolean isInteractive;

  /**
   * Constructor that wraps a Tangram marker.
   *
   * @param tangramMarker the underlying Tangram marker object.
   */
  public BitmapMarker(BitmapMarkerManager bitmapMarkerManager, Marker tangramMarker,
      StyleStringGenerator styleStringGenerator) {
    this.bitmapMarkerManager = bitmapMarkerManager;
    this.tangramMarker = tangramMarker;
    this.styleStringGenerator = styleStringGenerator;
  }

  /**
   * Removes this marker from the map. After a marker has been removed, the behavior of all its
   * methods is undefined.
   */
  public void remove() {
    bitmapMarkerManager.removeMarker(this);
  }

  /**
   * Sets the marker's coordinate position.
   * @param position
   */
  public void setPosition(LngLat position) {
    this.position = position;
    this.tangramMarker.setPoint(position);
  }

  /**
   * Sets the marker's coordinate position with animation.
   * @param position
   * @param duration
   * @param easeType
   */
  public void setPosition(LngLat position, int duration, EaseType easeType) {
    this.position = position;
    this.tangramMarker.setPointEased(position, duration,
        EaseTypeConverter.EASE_TYPE_TO_MAP_CONTROLLER_EASE_TYPE.get(easeType));
  }

  /**
   * Returns the marker's coordinate position.
   * @return
   */
  public LngLat getPosition() {
    return this.position;
  }

  /**
   * Sets the drawable resource id displayed as the marker's icon. Setting this value will override
   * existing icon drawable values set via {@link BitmapMarker#setIcon(Drawable)}.
   * @param resourceId
   */
  public void setIcon(int resourceId) {
    this.resourceId = resourceId;
    this.drawable = null;
    this.tangramMarker.setDrawable(resourceId);
  }

  /**
   * Returns the marker's icon resource id.
   * @return
   */
  public int getIcon() {
    return this.resourceId;
  }

  /**
   * Sets the drawable displayed as the marker's icon. Setting this value will override existing
   * icon resource id values set via {@link BitmapMarker#setIcon(int)}.
   * @param drawable
   */
  public void setIcon(Drawable drawable) {
    this.resourceId = Integer.MIN_VALUE;
    this.drawable = drawable;
    this.tangramMarker.setDrawable(drawable);
  }

  /**
   * Returns the marker's icon drawable.
   * @return
   */
  public Drawable getIconDrawable() {
    return this.drawable;
  }

  /**
   * Sets the width and height in pixels for the marker's size.
   * @param width
   * @param height
   */
  public void setSize(int width, int height) {
    this.width = width;
    this.height = height;
    updateStyleString();
  }

  /**
   * Returns the marker's width in pixels.
   * @return
   */
  public int getWidth() {
    return this.width;
  }

  /**
   * Returns the marker's height in pixels.
   * @return
   */
  public int getHeight() {
    return this.height;
  }

  /**
   * Sets the marker's visibility.
   * @param visible
   */
  public void setVisible(boolean visible) {
    this.isVisible = visible;
    tangramMarker.setVisible(visible);
  }

  /**
   * Returns whether the marker is visible.
   * @return
   */
  public boolean isVisible() {
    return isVisible;
  }

  /**
   * Sets marker z-axis draw order.
   * @param drawOrder
   */
  public void setDrawOrder(int drawOrder) {
    this.drawOrder = drawOrder;
    this.tangramMarker.setDrawOrder(drawOrder);
  }

  /**
   * Returns the marker's z-axis draw order.
   * @return
   */
  public int getDrawOrder() {
    return this.drawOrder;
  }

  /**
   * Sets extra data to be associated with this marker.
   * @param userData
   */
  public void setUserData(Object userData) {
    this.tangramMarker.setUserData(userData);
  }

  /**
   * Gets extra data associated with this marker.
   * @return
   */
  public Object getUserData() {
    return this.tangramMarker.getUserData();
  }

  /**
   * Sets color of marker given a color int ie {@code android.graphics.Color.BLUE}. Setting this
   * value will override existing color hex values set via {@link BitmapMarker#setColor(String)}.
   * @param colorInt
   */
  public void setColor(int colorInt) {
    this.colorInt = colorInt;
    this.colorHex = "#" + Integer.toHexString(colorInt);
    updateStyleString();
  }

  /**
   * Returns the marker's color int.
   * @return
   */
  public int getColor() {
    return this.colorInt;
  }

  /**
   * Sets color of marker given a color hex string. Setting this value will override existing
   * color int values set via {@link BitmapMarker#setColor(int)}.
   * @param hex
   */
  public void setColor(String hex) {
    this.colorHex = hex;
    this.colorInt = Integer.MIN_VALUE;
    updateStyleString();
  }

  /**
   * Returns the marker's color hex.
   * @return
   */
  public String getColorHex() {
    return this.colorHex;
  }

  /**
   * Sets whether or not marker can be selected.
   * @param interactive
   */
  public void setInteractive(boolean interactive) {
    this.isInteractive = interactive;
    updateStyleString();
  }

  /**
   * Returns whether or not the marker responds to touches.
   * @return
   */
  public boolean isInteractive() {
    return this.isInteractive;
  }

  /**
   * Allows setting the tangram marker, useful when restoring markers.
   * @param tangramMarker
   */
  void setTangramMarker(Marker tangramMarker) {
    this.tangramMarker = tangramMarker;
  }

  /**
   * Returns the underlying Tangram {@link Marker}.
   * @return
   */
  Marker getTangramMarker() {
    return tangramMarker;
  }

  /**
   * Returns the object used to generate style string.
   * @return
   */
  StyleStringGenerator getStyleStringGenerator() {
    return styleStringGenerator;
  }

  private void updateStyleString() {
    tangramMarker.setStylingFromString(styleStringGenerator.getStyleString(width, height,
        isInteractive, colorHex));
  }

}
