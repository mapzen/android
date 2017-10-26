package com.mapzen.android.graphics.model;

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

  @Override public String getBaseStyleFilename() {
    return "cinnabar-style.yaml";
  }

  @Override public String getStyleRootPath() {
    return "cinnabar-style/";
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
    return CINNABAR;
  }

  @Override public Set<ThemeColor> getColors() {
    return supportedColors;
  }
}
