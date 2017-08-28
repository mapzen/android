package com.mapzen.android.graphics.internal;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StyleStringGeneratorTest {

  private StyleStringGenerator generator = new StyleStringGenerator();

  @Test public void defaultStyleString() throws Exception {
    assertThat(generator.getStyleString(50, 50, true, "#FFFFFF")).isEqualTo("{ style: 'points', "
        + "color: '#FFFFFF', size: [50px, 50px], collide: false, interactive: true }");
  }

}
