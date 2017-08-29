package com.mapzen.android.graphics.model;

import org.junit.Test;

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

public class ThemeColorTest {

  @Test public void blackToString_shouldReturnCorrectValue() throws Exception {
    assertThat(BLACK.toString()).isEqualTo("black");
  }

  @Test public void blueToString_shouldReturnCorrectValue() throws Exception {
    assertThat(BLUE.toString()).isEqualTo("blue");
  }

  @Test public void bluegrayToString_shouldReturnCorrectValue() throws Exception {
    assertThat(BLUEGRAY.toString()).isEqualTo("blue-gray");
  }

  @Test public void brownorangeToString_shouldReturnCorrectValue() throws Exception {
    assertThat(BROWNORANGE.toString()).isEqualTo("brown-orange");
  }

  @Test public void grayToString_shouldReturnCorrectValue() throws Exception {
    assertThat(GRAY.toString()).isEqualTo("gray");
  }

  @Test public void graygoldToString_shouldReturnCorrectValue() throws Exception {
    assertThat(GRAYGOLD.toString()).isEqualTo("gray-gold");
  }

  @Test public void highcontrastToString_shouldReturnCorrectValue() throws Exception {
    assertThat(HIGHCONTRAST.toString()).isEqualTo("high-contrast");
  }

  @Test public void introvertedToString_shouldReturnCorrectValue() throws Exception {
    assertThat(INTROVERTED.toString()).isEqualTo("introverted");
  }

  @Test public void introvertedblueToString_shouldReturnCorrectValue() throws Exception {
    assertThat(INTROVERTEDBLUE.toString()).isEqualTo("introverted-blue");
  }

  @Test public void pinkToString_shouldReturnCorrectValue() throws Exception {
    assertThat(PINK.toString()).isEqualTo("pink");
  }

  @Test public void pinkyellowToString_shouldReturnCorrectValue() throws Exception {
    assertThat(PINKYELLOW.toString()).isEqualTo("pink-yellow");
  }

  @Test public void purplegreenToString_shouldReturnCorrectValue() throws Exception {
    assertThat(PURPLEGREEN.toString()).isEqualTo("purple-green");
  }

  @Test public void sepiaToString_shouldReturnCorrectValue() throws Exception {
    assertThat(SEPIA.toString()).isEqualTo("sepia");
  }
}