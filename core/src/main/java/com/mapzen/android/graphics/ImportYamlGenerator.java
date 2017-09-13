package com.mapzen.android.graphics;

import com.mapzen.android.graphics.model.ThemeColor;
import com.mapzen.android.graphics.model.ThemedMapStyle;

/**
 * Handles creating fully qualified import yaml for a given {@link ThemedMapStyle} so that
 * it can be applied by {@link MapzenMap}.
 */
class ImportYamlGenerator {

  /**
   * Creates import yaml string for a given theme, label level, detail level, and color.
   * @param themedMapStyle
   * @param labelLevel
   * @return
   */
  String getImportYaml(ThemedMapStyle themedMapStyle, int labelLevel, int detailLevel,
      ThemeColor color) {
    String labelFileName = new StringBuilder()
        .append("label-")
        .append(labelLevel)
        .append(".yaml")
        .toString();
    String detailFileName = new StringBuilder()
        .append("detail-")
        .append(detailLevel)
        .append(".yaml")
        .toString();
    String colorFileName = new StringBuilder()
        .append("color-")
        .append(color.toString())
        .append(".yaml")
        .toString();
    return "{ import: [ "
        + themedMapStyle.getBaseStyleFilename() + ", "
        + themedMapStyle.getThemesPath() + labelFileName + ", "
        + themedMapStyle.getThemesPath() + detailFileName + ", "
        + themedMapStyle.getThemesPath() + colorFileName
        + " ] }";
  }
}
