package com.mapzen.android.graphics.model;

import org.junit.Test;

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
import static org.assertj.core.api.Assertions.assertThat;

public class RefillStyleTest {

  private RefillStyle style = new RefillStyle();

  @Test public void getSceneFile_shouldReturnCorrectValue() throws Exception {
    assertThat(style.getSceneFile()).isEqualTo("styles/refill-style/refill-style.yaml");
  }

  @Test public void getBaseStyleFilename_shouldReturnCorrectValue() throws Exception {
    assertThat(style.getBaseStyleFilename()).isEqualTo("refill-style.yaml");
  }

  @Test public void getStyleRootPath_shouldReturnCorrectValue() throws Exception {
    assertThat(style.getStyleRootPath()).isEqualTo("styles/refill-style/");
  }

  @Test public void getThemesPath_shouldReturnCorrectValue() throws Exception {
    assertThat(style.getThemesPath()).isEqualTo("themes/");
  }

  @Test public void getDefaultDetailLevel_shouldReturnCorrectValue() throws Exception {
    assertThat(style.getDefaultDetailLevel()).isEqualTo(10);
  }

  @Test public void getDetailCount_shouldReturnCorrectValue() throws Exception {
    assertThat(style.getDetailCount()).isEqualTo(12);
  }

  @Test public void getDefaultLabelLevel_shouldReturnCorrectValue() throws Exception {
    assertThat(style.getDefaultLabelLevel()).isEqualTo(10);
  }

  @Test public void getLabelCount_shouldReturnCorrectValue() throws Exception {
    assertThat(style.getLabelCount()).isEqualTo(12);
  }

  @Test public void getDefaultColor_shouldReturnCorrectValue() throws Exception {
    assertThat(style.getDefaultColor()).isEqualTo(BLACK);
  }

  @Test public void getColors_shouldReturnCorrectValue() throws Exception {
    Set colors = new HashSet<ThemeColor>() { {
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
    } };
    assertThat(style.getColors()).isEqualTo(colors);
  }
}
