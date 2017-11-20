package com.mapzen.android.graphics.model;

import android.support.annotation.NonNull;

/**
 * Available color themes for {@link RefillStyle}.
 */
public enum ThemeColor {
  NONE(null),
  BLACK("black"),
  BLUE("blue"),
  BLUEGRAY("blue-gray"),
  BROWNORANGE("brown-orange"),
  GRAY("gray"),
  GRAYGOLD("gray-gold"),
  HIGHCONTRAST("high-contrast"),
  INTROVERTED("introverted"),
  INTROVERTEDBLUE("introverted-blue"),
  PINK("pink"),
  PINKYELLOW("pink-yellow"),
  PURPLEGREEN("purple-green"),
  SEPIA("sepia"),
  CINNABAR("cinnabar"),
  ZINC("zinc");

  private final String color;

  /**
   * Constructor.
   * @param color
   */
  ThemeColor(String color) {
    this.color = color;
  }

  /**
   * Returns string value of theme color.
   * @return
   */
  @NonNull public String toString() {
    return color;
  }
}
