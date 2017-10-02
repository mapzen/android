package com.mapzen.android.graphics.model;

import java.util.HashSet;
import java.util.Set;

import static com.mapzen.android.graphics.model.ThemeColor.BLACK;
import static com.mapzen.android.graphics.model.ThemeColor.BLUE;
import static com.mapzen.android.graphics.model.ThemeColor.BLUEGRAY;
import static com.mapzen.android.graphics.model.ThemeColor.BROWNORANGE;
import static com.mapzen.android.graphics.model.ThemeColor.GRAY;
import static com.mapzen.android.graphics.model.ThemeColor.GRAYGOLD;
import static com.mapzen.android.graphics.model.ThemeColor.HIGHCONTRAST;
import static com.mapzen.android.graphics.model.ThemeColor.INTROVERTED;
import static com.mapzen.android.graphics.model.ThemeColor.INTROVERTEDBLUE;
import static com.mapzen.android.graphics.model.ThemeColor.PINK;
import static com.mapzen.android.graphics.model.ThemeColor.PINKYELLOW;
import static com.mapzen.android.graphics.model.ThemeColor.PURPLEGREEN;
import static com.mapzen.android.graphics.model.ThemeColor.SEPIA;
import static com.mapzen.android.graphics.model.ThemeColor.ZINC;

/**
 * Refill default map style.
 */
public class RefillStyle extends ThemedMapStyle {

  private final Set<ThemeColor> supportedColors = new HashSet<ThemeColor>() { {
    add(BLACK);
    add(BLUE);
    add(BLUEGRAY);
    add(BROWNORANGE);
    add(GRAY);
    add(GRAYGOLD);
    add(HIGHCONTRAST);
    add(INTROVERTED);
    add(INTROVERTEDBLUE);
    add(PINK);
    add(PINKYELLOW);
    add(PURPLEGREEN);
    add(SEPIA);
    add(ZINC);
  } };

  /**
   * Creates a new instance.
   */
  public RefillStyle() {
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
    return BLACK;
  }

  @Override public Set<ThemeColor> getColors() {
    return supportedColors;
  }
}
