package com.mapzen.android.graphics.model;

import java.util.Set;

/**
 * Walkabout outdoor map style.
 */
public class WalkaboutStyle extends ThemedMapStyle {

  /**
   * Creates a new instance.
   */
  public WalkaboutStyle() {
    super("style/walkabout-style/walkabout-style.yaml");
  }

  @Override public String getBaseStyleFilename() {
    return "walkabout-style.yaml";
  }

  @Override public String getStyleRootPath() {
    return "style/walkabout-style/";
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
