package com.mapzen.android.graphics;

import com.mapzen.android.graphics.model.ThemedMapStyle;

/**
 * Handles creating fully qualified import yaml for a given {@link ThemedMapStyle} so that
 * it can be applied by {@link MapzenMap}.
 */
class MapStyleThemeYamlGenerator {

  /**
   * Creates import yaml string for a given theme and label level.
   * @param themedMapStyle
   * @param labelLevel
   * @return
   */
  String getLabelThemeYaml(ThemedMapStyle themedMapStyle, int labelLevel) {
    String themeFileName = new StringBuilder()
        .append("label-")
        .append(labelLevel)
        .append(".yaml")
        .toString();
    return getThemeYaml(themedMapStyle, themeFileName);
  }

  /**
   * Creates import yaml string for a given theme and detail level.
   * @param themedMapStyle
   * @param detailLevel
   * @return
   */
  String getDetailThemeYaml(ThemedMapStyle themedMapStyle, int detailLevel) {
    String themeFileName = new StringBuilder()
        .append("detail-")
        .append(detailLevel)
        .append(".yaml")
        .toString();
    return getThemeYaml(themedMapStyle, themeFileName);
  }

  /**
   * Creates import yaml string for a given theme and color.
   * @param themedMapStyle
   * @param color
   * @return
   */
  String getColorThemeYaml(ThemedMapStyle themedMapStyle, String color) {
    String themeFileName = new StringBuilder()
        .append("color-")
        .append(color)
        .append(".yaml")
        .toString();
    return getThemeYaml(themedMapStyle, themeFileName);
  }

  private String getThemeYaml(ThemedMapStyle themedMapStyle, String themeFileName) {
    return "{ import: [ " + themedMapStyle.getSceneFile() + ", "
        + themedMapStyle.getThemesPath() + themeFileName + "] }";
  }
}
