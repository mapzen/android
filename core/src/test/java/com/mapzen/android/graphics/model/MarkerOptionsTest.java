package com.mapzen.android.graphics.model;

import com.mapzen.tangram.LngLat;

import org.junit.Test;

import android.graphics.drawable.Drawable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class MarkerOptionsTest {
  private MarkerOptions markerOptions = new MarkerOptions();

  @Test public void shouldNotBeNull() throws Exception {
    assertThat(markerOptions).isNotNull();
  }

  @Test public void shouldSetPosition() throws Exception {
    assertThat(markerOptions.position(new LngLat()).getPosition()).isEqualTo(new LngLat());
  }

  @Test public void shouldSetIconAndNullDrawable() throws Exception {
    assertThat(markerOptions.icon(123).getIcon()).isEqualTo(123);
    assertThat(markerOptions.getIconDrawable()).isNull();
  }

  @Test public void shouldSetIconDrawableAndNullIcon() throws Exception {
    Drawable drawable = mock(Drawable.class);
    assertThat(markerOptions.icon(drawable).getIconDrawable()).isEqualTo(drawable);
    assertThat(markerOptions.getIcon()).isEqualTo(Integer.MIN_VALUE);
  }
}
