package com.mapzen.android.graphics.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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

  @NonNull @Override public String getBaseStyleFilename() {
    return "tron-style.yaml";
  }

  @NonNull @Override public String getStyleRootPath() {
    return "tron-style/";
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
