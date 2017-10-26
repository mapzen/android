package com.mapzen.android.graphics.model;

import org.junit.Test;

import static com.mapzen.android.graphics.model.ThemedMapStyle.NONE;
import static org.assertj.core.api.Assertions.assertThat;

public class BubbleWrapStyleTest {

  private BubbleWrapStyle style = new BubbleWrapStyle();

  @Test public void getSceneFile_shouldReturnCorrectValue() throws Exception {
    assertThat(style.getSceneFile()).isEqualTo("bubble-wrap/bubble-wrap-style.yaml");
  }

  @Test public void getBaseStyleFilename_shouldReturnCorrectValue() throws Exception {
    assertThat(style.getBaseStyleFilename()).isEqualTo("bubble-wrap-style.yaml");
  }

  @Test public void getStyleRootPath_shouldReturnCorrectValue() throws Exception {
    assertThat(style.getStyleRootPath()).isEqualTo("bubble-wrap/");
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
