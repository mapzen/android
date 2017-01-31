package com.mapzen.android.graphics;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TileHttpHandlerTest {
  private TileHttpHandler tileHttpHandler = new TileHttpHandler();

  @Test public void shouldNotBeNull() throws Exception {
    assertThat(tileHttpHandler).isNotNull();
  }
}
