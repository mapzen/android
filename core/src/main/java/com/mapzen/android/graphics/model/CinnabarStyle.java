package com.mapzen.android.graphics.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashSet;
import java.util.Set;

import static com.mapzen.android.graphics.model.ThemeColor.CINNABAR;

/**
 * Cinnabar default map style.
 */
public class CinnabarStyle extends ThemedMapStyle {

  private final Set<ThemeColor> supportedColors = new HashSet<ThemeColor>() { {
    add(CINNABAR);
  } };

  /**
   * Creates a new instance.
   */
  public CinnabarStyle() {
    super("cinnabar-style/cinnabar-style.yaml");
  }

  @NonNull @Override public String getBaseStyleFilename() {
    return "cinnabar-style.yaml";
  }

  @NonNull @Override public String getStyleRootPath() {
    return "cinnabar-style/";
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
    return CINNABAR;
  }

  @Nullable @Override public Set<ThemeColor> getColors() {
    return supportedColors;
  }
}
