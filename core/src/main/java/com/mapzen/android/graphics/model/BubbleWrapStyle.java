package com.mapzen.android.graphics.model;

import java.util.Set;

/**
 * BubbleWrap default map style.
 */
public class BubbleWrapStyle extends ThemedMapStyle {

  /**
   * Creates a new instance.
   */
  public BubbleWrapStyle() {
    super("bubble-wrap/bubble-wrap-style.yaml");
  }

  @Override public String getBaseStyleFilename() {
    return "bubble-wrap-style.yaml";
  }

  @Override public String getStyleRootPath() {
    return "bubble-wrap/";
  }

  @Override public String getThemesPath() {
    return "themes/";
  }

  @Override public int getDefaultLod() {
    return NONE;
  }

  @Override public int getLodCount() {
    return NONE;
  }

  @Override public int getDefaultLabelLevel() {
    return 5;
  }

  @Override public int getLabelCount() {
    return 12;
  }

  @Override public ThemeColor getDefaultColor() {
    return ThemeColor.NONE;
  }

  @Override public Set<ThemeColor> getColors() {
    return null;
  }
}
