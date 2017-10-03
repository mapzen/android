package com.mapzen.android.graphics;

import com.mapzen.android.graphics.model.ThemeColor;
import com.mapzen.android.graphics.model.ThemedMapStyle;

import static com.mapzen.android.graphics.model.ThemedMapStyle.NONE;

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
    StringBuilder importBuilder = new StringBuilder()
        .append("{ import: [ ")
        .append(themedMapStyle.getBaseStyleFilename())
        .append(", ");

    if (labelLevel != NONE) {
      importBuilder.append(themedMapStyle.getThemesPath());
      importBuilder.append("label-");
      importBuilder.append(labelLevel);
      importBuilder.append(".yaml");
    }

    if (labelLevel != NONE && (detailLevel != NONE || colorExists(color))) {
      importBuilder.append(", ");
    }

    if (detailLevel != NONE) {
      importBuilder.append(themedMapStyle.getThemesPath());
      importBuilder.append("detail-");
      importBuilder.append(detailLevel);
      importBuilder.append(".yaml");
    }

    if (detailLevel != NONE && colorExists(color)) {
      importBuilder.append(", ");
    }

    if (colorExists(color)) {
      importBuilder.append(themedMapStyle.getThemesPath());
      importBuilder.append("color-");
      importBuilder.append(color.toString());
      importBuilder.append(".yaml");
    }

    importBuilder.append(" ] }");
    return importBuilder.toString();
  }

  private boolean colorExists(ThemeColor color) {
    return color != null && color != ThemeColor.NONE;
  }
}
