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

  private final MarkerManager markerManager;
  private final Marker tangramMarker;
  private final StyleStringGenerator styleStringGenerator;

  /**
   * Constructor that wraps a Tangram marker.
   *
   * @param tangramMarker the underlying Tangram marker object.
   */
  public BitmapMarker(MarkerManager markerManager, Marker tangramMarker,
      StyleStringGenerator styleStringGenerator) {
    this.markerManager = markerManager;
    this.tangramMarker = tangramMarker;
    this.styleStringGenerator = styleStringGenerator;
  }

  /**
   * Removes this marker from the map. After a marker has been removed, the behavior of all its
   * methods is undefined.
   */
  public void remove() {
    markerManager.removeMarker(tangramMarker);
  }

  /**
   * Sets the marker's coordinate position.
   * @param position
   */
  public void setPosition(LngLat position) {
    this.tangramMarker.setPoint(position);
  }

  /**
   * Sets the marker's coordinate position with animation.
   * @param position
   * @param duration
   * @param easeType
   */
  public void setPosition(LngLat position, int duration, EaseType easeType) {
    this.tangramMarker.setPointEased(position, duration,
        EaseTypeConverter.EASE_TYPE_TO_MAP_CONTROLLER_EASE_TYPE.get(easeType));
  }

  /**
   * Sets the resource id displayed as the marker's icon.
   * @param resourceId
   */
  public void setIcon(int resourceId) {
    this.tangramMarker.setDrawable(resourceId);
  }

  /**
   * Sets the drawable displayed as the marker's icon.
   * @param drawable
   */
  public void setIcon(Drawable drawable) {
    this.tangramMarker.setDrawable(drawable);
  }

  /**
   * Sets the width and height in pixels for the marker's size.
   * @param width
   * @param height
   */
  public void setSize(int width, int height) {
    styleStringGenerator.setSize(width, height);
    updateStyleString();
  }

  /**
   * Sets the marker's visibility.
   * @param visible
   */
  public void setVisible(boolean visible) {
    tangramMarker.setVisible(visible);
  }

  /**
   * Sets marker z-axis draw order.
   * @param drawOrder
   */
  public void setDrawOrder(int drawOrder) {
    this.tangramMarker.setDrawOrder(drawOrder);
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
   * Sets background color of marker given a color int ie {@code android.graphics.Color.BLUE}.
   * @param colorInt
   */
  public void setBackgroundColor(int colorInt) {
    String hex = "#" + Integer.toHexString(colorInt);
    styleStringGenerator.setBackgroundColor(hex);
    updateStyleString();
  }

  /**
   * Sets background color of marker given a color hex string.
   * @param hex
   */
  public void setBackgroundColor(String hex) {
    styleStringGenerator.setBackgroundColor(hex);
    updateStyleString();
  }

  /**
   * Sets whether or not marker can be selected.
   * @param interactive
   */
  public void setInteractive(boolean interactive) {
    styleStringGenerator.setInteractive(interactive);
    updateStyleString();
  }

  private void updateStyleString() {
    tangramMarker.setStylingFromString(styleStringGenerator.getStyleString());
  }

}
