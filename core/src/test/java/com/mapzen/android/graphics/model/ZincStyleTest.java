package com.mapzen.android.graphics.model;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static com.mapzen.android.graphics.model.ThemeColor.ZINC;
import static org.assertj.core.api.Assertions.assertThat;

public class ZincStyleTest {

  private ZincStyle style = new ZincStyle();

  @Test public void getSceneFile_shouldReturnCorrectValue() throws Exception {
    assertThat(style.getSceneFile()).isEqualTo("refill-style/refill-style.yaml");
  }

  @Test public void getBaseStyleFilename_shouldReturnCorrectValue() throws Exception {
    assertThat(style.getBaseStyleFilename()).isEqualTo("refill-style.yaml");
  }

  @Test public void getStyleRootPath_shouldReturnCorrectValue() throws Exception {
    assertThat(style.getStyleRootPath()).isEqualTo("refill-style/");
  }

  @Test public void getThemesPath_shouldReturnCorrectValue() throws Exception {
    assertThat(style.getThemesPath()).isEqualTo("themes/");
  }

  @Test public void getDefaultLod_shouldReturnCorrectValue() throws Exception {
    assertThat(style.getDefaultLod()).isEqualTo(10);
  }

  @Test public void getLodCount_shouldReturnCorrectValue() throws Exception {
    assertThat(style.getLodCount()).isEqualTo(12);
  }

  @Test public void getDefaultLabelLevel_shouldReturnCorrectValue() throws Exception {
    assertThat(style.getDefaultLabelLevel()).isEqualTo(5);
  }

  @Test public void getLabelCount_shouldReturnCorrectValue() throws Exception {
    assertThat(style.getLabelCount()).isEqualTo(12);
  }

  @Test public void getDefaultColor_shouldReturnCorrectValue() throws Exception {
    assertThat(style.getDefaultColor()).isEqualTo(ZINC);
  }

  @Test public void getColors_shouldReturnCorrectValue() throws Exception {
    Set colors = new HashSet<ThemeColor>() {
      {
        add(ZINC);
      }
    };
    assertThat(style.getColors()).isEqualTo(colors);
  }
}
