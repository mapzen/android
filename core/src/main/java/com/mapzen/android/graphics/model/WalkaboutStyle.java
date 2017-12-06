package com.mapzen.android.graphics.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Set;

/**
 * Walkabout outdoor map style.
 */
public class WalkaboutStyle extends ThemedMapStyle {

  /**
   * Creates a new instance.
   */
  public WalkaboutStyle() {
    super("walkabout-style/walkabout-style.yaml");
  }

  @NonNull @Override public String getBaseStyleFilename() {
    return "walkabout-style.yaml";
  }

  @NonNull @Override public String getStyleRootPath() {
    return "walkabout-style/";
  }

  @NonNull @Override public String getThemesPath() {
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

  @NonNull @Override public ThemeColor getDefaultColor() {
    return ThemeColor.NONE;
  }

  @Nullable @Override public Set<ThemeColor> getColors() {
    return null;
  }
}
