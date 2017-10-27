package com.mapzen.android.graphics.model;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static com.mapzen.android.graphics.model.ThemeColor.CINNABAR;
import static com.mapzen.android.graphics.model.ThemedMapStyle.NONE;
import static org.assertj.core.api.Assertions.assertThat;

public class CinnabarStyleTest {

  private CinnabarStyle style = new CinnabarStyle();

  @Test public void getSceneFile_shouldReturnCorrectValue() throws Exception {
    assertThat(style.getSceneFile()).isEqualTo("cinnabar-style/cinnabar-style.yaml");
  }

  @Test public void getBaseStyleFilename_shouldReturnCorrectValue() throws Exception {
    assertThat(style.getBaseStyleFilename()).isEqualTo("cinnabar-style.yaml");
  }

  @Test public void getStyleRootPath_shouldReturnCorrectValue() throws Exception {
    assertThat(style.getStyleRootPath()).isEqualTo("cinnabar-style/");
  }

  @Test public void getThemesPath_shouldReturnCorrectValue() throws Exception {
    assertThat(style.getThemesPath()).isEqualTo("themes/");
  }

  @Test public void getDefaultLod_shouldReturnCorrectValue() throws Exception {
    assertThat(style.getDefaultLod()).isEqualTo(NONE);
  }

  @Test public void getLodCount_shouldReturnCorrectValue() throws Exception {
    assertThat(style.getLodCount()).isEqualTo(NONE);
  }

  @Test public void getDefaultLabelLevel_shouldReturnCorrectValue() throws Exception {
    assertThat(style.getDefaultLabelLevel()).isEqualTo(5);
  }

  @Test public void getLabelCount_shouldReturnCorrectValue() throws Exception {
    assertThat(style.getLabelCount()).isEqualTo(12);
  }

  @Test public void getDefaultColor_shouldReturnCorrectValue() throws Exception {
    assertThat(style.getDefaultColor()).isEqualTo(ThemeColor.CINNABAR);
  }

  @Test public void getColors_shouldReturnCorrectValue() throws Exception {
    Set colors = new HashSet<ThemeColor>() {
      {
        add(CINNABAR);
      }
    };
    assertThat(style.getColors()).isEqualTo(colors);
  }
}
