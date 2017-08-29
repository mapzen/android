package com.mapzen.android.graphics.model;

import java.util.Set;

/**
 * Abstract class for map style which supports themes.
 */
public abstract class ThemedMapStyle extends MapStyle {

  public ThemedMapStyle(String baseSceneFile) {
    super(baseSceneFile);
  }

  public abstract String getStyleRootPath();
  public abstract String getBaseStyleFilename();
  public abstract String getThemesPath();
  public abstract int getDefaultDetailLevel();
  public abstract int getDetailCount();
  public abstract int getDefaultLabelLevel();
  public abstract int getLabelCount();
  public abstract ThemeColor getDefaultColor();
  public abstract Set<ThemeColor> getColors();
}
