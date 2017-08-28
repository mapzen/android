package com.mapzen.android.graphics.model;

import com.mapzen.tangram.LngLat;

import org.junit.Test;

import android.graphics.drawable.Drawable;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;


public class BitmapMarkerOptionsTest {

  private BitmapMarkerOptions markerOptions = new BitmapMarkerOptions();

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

  @Test public void shouldSetDrawOrder() throws Exception {
    assertThat(markerOptions.drawOrder(1).getDrawOrder()).isEqualTo(1);
  }

  @Test public void shouldSetUserData() throws Exception {
    Map userData = mock(Map.class);
    assertThat(markerOptions.userData(userData).getUserData()).isEqualTo(userData);
  }

  @Test public void shouldSetColorInt() throws Exception {
    assertThat(markerOptions.colorInt(8).getColorInt()).isEqualTo(8);
  }

  @Test public void shouldSetColorIntResetColorHex() throws Exception {
    markerOptions.colorHex("test");
    markerOptions.colorInt(8);
    assertThat(markerOptions.getColorHex()).isNull();
  }

  @Test public void shouldSetColorHex() throws Exception {
    assertThat(markerOptions.colorHex("asdf").getColorHex()).isEqualTo("asdf");
  }

  @Test public void shouldSetColorHexResetColorInt() throws Exception {
    markerOptions.colorInt(10);
    markerOptions.colorHex("asdf");
    assertThat(markerOptions.getColorInt()).isEqualTo(Integer.MIN_VALUE);
  }
}

