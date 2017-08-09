package com.mapzen.android.graphics.internal;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StyleStringGeneratorTest {

  private StyleStringGenerator generator = new StyleStringGenerator();

  @Test public void defaultStyleString() throws Exception {
    assertThat(generator.getStyleString()).isEqualTo("{ style: 'points', color: '#FFFFFF', "
        + "size: [50px, 50px], collide: false, interactive: true }");
  }

  @Test public void setSize_updatesStyleString() throws Exception {
    generator.setSize(10, 10);
    assertThat(generator.getStyleString()).isEqualTo("{ style: 'points', color: '#FFFFFF', "
        + "size: [10px, 10px], collide: false, interactive: true }");
  }

  @Test public void setInteractive_updatesStyleString() throws Exception {
    generator.setInteractive(false);
    assertThat(generator.getStyleString()).isEqualTo("{ style: 'points', color: '#FFFFFF', "
        + "size: [50px, 50px], collide: false, interactive: false }");
  }

  @Test public void setBackgroundColor_updatesStyleString() throws Exception {
    generator.setBackgroundColor("#0000FF");
    assertThat(generator.getStyleString()).isEqualTo("{ style: 'points', color: '#0000FF', "
        + "size: [50px, 50px], collide: false, interactive: true }");
  }
}
