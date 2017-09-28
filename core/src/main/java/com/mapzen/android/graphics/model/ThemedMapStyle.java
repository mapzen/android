package com.mapzen.android.graphics.model;

import java.util.Set;

/**
 * Abstract class for map style which supports themes.
 */
public abstract class ThemedMapStyle extends MapStyle {

  public static final int NONE = -1;

  /**
   * Constructor.
   * @param baseSceneFile
   */
  public ThemedMapStyle(String baseSceneFile) {
    super(baseSceneFile);
  }

  /**
   * Returns the style's root directory. Styles should be located within the assets directory.
   * ie. "styles/refill-style/"
   * @return
   */
  public abstract String getStyleRootPath();

  /**
   * Returns the style's base file.
   * ie. "refill-style.yaml"
   * @return
   */
  public abstract String getBaseStyleFilename();

  /**
   * Returns the themes directory path.
   * ie "themes/"
   * @return
   */
  public abstract String getThemesPath();

  /**
   * Returns the default detail level for the style.
   * @return
   */
  public abstract int getDefaultLod();

  /**
   * Returns the total number of detail levels for the style. Detail theme filenames should be
   * zero-based and follow the format "detail-{DETAIL-LEVEL}.yaml".
   * ie "detail-8.yaml"
   * @return
   */
  public abstract int getLodCount();

  /**
   * Returns the default label level for the style.
   * @return
   */
  public abstract int getDefaultLabelLevel();

  /**
   * Returns the total number of label levels for the style. Label theme filenames should be
   * zero-based and follow the format "label-{LABEL-LEVEL}.yaml".
   * ie. "label-3.yaml"
   * @return
   */
  public abstract int getLabelCount();

  /**
   * Returns the default color for the style.
   * @return
   */
  public abstract ThemeColor getDefaultColor();

  /**
   * Returns colors the theme supports. Color theme filenames should follow the format
   * "color-{THEME-COLOR}.yaml"
   * ie. "color-pink.yaml"
   * @return
   */
  public abstract Set<ThemeColor> getColors();
}
