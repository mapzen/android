package com.mapzen.places.api.internal;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PlacesBubbleWrapStyleTest {

  PlacesBubbleWrapStyle bubbleWrap = new PlacesBubbleWrapStyle();

  @Test public void shouldReturnCorrectLabelLevel() throws Exception {
    assertThat(bubbleWrap.getDefaultLabelLevel()).isEqualTo(11);
  }
}
