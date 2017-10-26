package com.mapzen.android.graphics.model;

import java.util.Set;

/**
 * Tron 2.0 map style.
 */
public class TronStyle extends ThemedMapStyle {

  /**
   * Creates a new instance.
   */
  public TronStyle() {
    super("tron-style/tron-style.yaml");
  }

  @Override public String getBaseStyleFilename() {
    return "tron-style.yaml";
  }

  @Override public String getStyleRootPath() {
    return "tron-style/";
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
