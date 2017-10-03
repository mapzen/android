package com.mapzen.android.graphics.model;

import java.util.HashSet;
import java.util.Set;

import static com.mapzen.android.graphics.model.ThemeColor.ZINC;

/**
 * Zinc more labels map style.
 *
 * Deprecated in favor of {@link RefillStyle} with {@link ThemeColor#ZINC}.
 */
@Deprecated
public class ZincStyle extends ThemedMapStyle {

  private final Set<ThemeColor> supportedColors = new HashSet<ThemeColor>() {
    {
      add(ZINC);
    }
  };

  /**
   * Creates a new instance.
   */
  public ZincStyle() {
    super("styles/refill-style/refill-style.yaml");
  }

  @Override public String getBaseStyleFilename() {
    return "refill-style.yaml";
  }

  @Override public String getStyleRootPath() {
    return "styles/refill-style/";
  }

  @Override public String getThemesPath() {
    return "themes/";
  }

  @Override public int getDefaultLod() {
    return 10;
  }

  @Override public int getLodCount() {
    return 12;
  }

  @Override public int getDefaultLabelLevel() {
    return 5;
  }

  @Override public int getLabelCount() {
    return 12;
  }

  @Override public ThemeColor getDefaultColor() {
    return ZINC;
  }

  @Override public Set<ThemeColor> getColors() {
    return supportedColors;
  }
}
