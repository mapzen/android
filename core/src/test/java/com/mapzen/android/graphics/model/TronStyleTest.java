package com.mapzen.android.graphics.model;

import org.junit.Test;

import static com.mapzen.android.graphics.model.ThemedMapStyle.NONE;
import static org.assertj.core.api.Assertions.assertThat;

public class TronStyleTest {

  private TronStyle style = new TronStyle();

  @Test public void getSceneFile_shouldReturnCorrectValue() throws Exception {
    assertThat(style.getSceneFile()).isEqualTo("tron-style/tron-style.yaml");
  }

  @Test public void getBaseStyleFilename_shouldReturnCorrectValue() throws Exception {
    assertThat(style.getBaseStyleFilename()).isEqualTo("tron-style.yaml");
  }

  @Test public void getStyleRootPath_shouldReturnCorrectValue() throws Exception {
    assertThat(style.getStyleRootPath()).isEqualTo("tron-style/");
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
    assertThat(style.getDefaultColor()).isEqualTo(ThemeColor.NONE);
  }

  @Test public void getColors_shouldReturnCorrectValue() throws Exception {
    assertThat(style.getColors()).isNull();
  }
}
