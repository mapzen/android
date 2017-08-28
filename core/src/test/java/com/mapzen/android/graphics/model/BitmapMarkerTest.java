package com.mapzen.android.graphics.model;

import com.mapzen.android.graphics.internal.StyleStringGenerator;
import com.mapzen.tangram.LngLat;
import com.mapzen.tangram.MapController;
import com.mapzen.tangram.Marker;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

import android.graphics.Color;
import android.graphics.drawable.Drawable;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@SuppressStaticInitializationFor("com.mapzen.tangram.MapController")
public class BitmapMarkerTest {
  private Marker tangramMarker = mock(Marker.class);
  private MapController mapController = mock(MapController.class);
  private BitmapMarkerManager bitmapMarkerManager = new BitmapMarkerManager(
      new BitmapMarkerFactory(), new StyleStringGenerator());
  private StyleStringGenerator styleStringGenerator  = mock(StyleStringGenerator.class);
  private BitmapMarker bitmapMarker = new BitmapMarker(bitmapMarkerManager, tangramMarker,
      styleStringGenerator);

  @Before public void setup() throws Exception {
    bitmapMarkerManager.setMapController(mapController);
  }

  @Test public void shouldNotBeNull() throws Exception {
    assertThat(bitmapMarker).isNotNull();
  }

  @Test public void remove_shouldRemoveMarkerFromMapController() throws Exception {
    bitmapMarker.remove();
    verify(mapController).removeMarker(tangramMarker);
  }

  @Test public void setPosition_shouldCallTangramMarker() throws Exception {
    LngLat pos = new LngLat(40, 70);
    bitmapMarker.setPosition(pos);
    verify(tangramMarker).setPoint(pos);
  }

  @Test public void setPosition_shouldSetPosition() throws Exception {
    LngLat pos = new LngLat(40, 70);
    bitmapMarker.setPosition(pos);
    assertThat(bitmapMarker.getPosition()).isEqualTo(pos);
  }

  @Test public void setPosition_animated_shouldCallTangramMarker() throws Exception {
    LngLat pos = new LngLat(40, 70);
    bitmapMarker.setPosition(pos, 1, EaseType.CUBIC);
    verify(tangramMarker).setPointEased(pos, 1, MapController.EaseType.CUBIC);
  }

  @Test public void setPosition_animated_shouldSetPosition() throws Exception {
    LngLat pos = new LngLat(40, 70);
    bitmapMarker.setPosition(pos, 1, EaseType.CUBIC);
    assertThat(bitmapMarker.getPosition()).isEqualTo(pos);
  }

  @Test public void setIcon_shouldCallTangramMarker() throws Exception {
    int resId = 1;
    bitmapMarker.setIcon(resId);
    verify(tangramMarker).setDrawable(resId);
  }

  @Test public void setIcon_shouldSetIcon() throws Exception {
    int resId = 1;
    bitmapMarker.setIcon(resId);
    assertThat(bitmapMarker.getIcon()).isEqualTo(resId);
  }

  @Test public void setIcon_shouldNullIconDrawable() throws Exception {
    int resId = 1;
    bitmapMarker.setIcon(mock(Drawable.class));
    bitmapMarker.setIcon(resId);
    assertThat(bitmapMarker.getIconDrawable()).isNull();
  }

  @Test public void setIcon_drawable_shouldCallTangramMarker() throws Exception {
    Drawable drawable = mock(Drawable.class);
    bitmapMarker.setIcon(drawable);
    verify(tangramMarker).setDrawable(drawable);
  }

  @Test public void setIcon_drawable_shouldSetIcon() throws Exception {
    Drawable drawable = mock(Drawable.class);
    bitmapMarker.setIcon(drawable);
    assertThat(bitmapMarker.getIconDrawable()).isEqualTo(drawable);
  }

  @Test public void setIcon_drawable_shouldResetIconResId() throws Exception {
    Drawable drawable = mock(Drawable.class);
    bitmapMarker.setIcon(1);
    bitmapMarker.setIcon(drawable);
    assertThat(bitmapMarker.getIcon()).isEqualTo(Integer.MIN_VALUE);
  }

  @Test public void setSize_shouldCallTangramMarker() throws Exception {
    int width = 10;
    int height = 5;
    bitmapMarker.setSize(width, height);
    verify(tangramMarker).setStylingFromString(anyString());
  }

  @Test public void setSize_shouldSetWidth() throws Exception {
    int width = 10;
    int height = 5;
    bitmapMarker.setSize(width, height);
    assertThat(bitmapMarker.getWidth()).isEqualTo(width);
  }

  @Test public void setSize_shouldSetHeight() throws Exception {
    int width = 10;
    int height = 5;
    bitmapMarker.setSize(width, height);
    assertThat(bitmapMarker.getHeight()).isEqualTo(height);
  }

  @Test public void setVisible_shouldCallTangramMarker() throws Exception {
    bitmapMarker.setVisible(true);
    verify(tangramMarker).setVisible(true);
  }

  @Test public void setVisible_shouldSetVisibility() throws Exception {
    bitmapMarker.setVisible(true);
    assertThat(bitmapMarker.isVisible()).isTrue();
    bitmapMarker.setVisible(false);
    assertThat(bitmapMarker.isVisible()).isFalse();
  }

  @Test public void setDrawOrder_shouldCallTangramMarker() throws Exception {
    bitmapMarker.setDrawOrder(1);
    verify(tangramMarker).setDrawOrder(1);
  }

  @Test public void setDrawOrder_shouldSetDrawOrder() throws Exception {
    bitmapMarker.setDrawOrder(1);
    assertThat(bitmapMarker.getDrawOrder()).isEqualTo(1);
  }

  @Test public void setUserData_shouldCallTangramMarker() throws Exception {
    HashMap obj = new HashMap();
    bitmapMarker.setUserData(obj);
    verify(tangramMarker).setUserData(obj);
  }

  @Test public void getDrawOrder_shouldCallTangramMarker() throws Exception {
    bitmapMarker.getUserData();
    verify(tangramMarker).getUserData();
  }

  @Test public void setInteractive_shouldCallTangramMarker() throws Exception {
    bitmapMarker.setInteractive(true);
    verify(tangramMarker).setStylingFromString(anyString());
  }

  @Test public void setInteractive_shouldSetInteractive() throws Exception {
    bitmapMarker.setInteractive(true);
    assertThat(bitmapMarker.isInteractive()).isTrue();
    bitmapMarker.setInteractive(false);
    assertThat(bitmapMarker.isInteractive()).isFalse();
  }

  @Test public void setColor_colorInt_shouldCallTangramMarker() throws Exception {
    bitmapMarker.setColor(Color.BLUE);
    verify(tangramMarker).setStylingFromString(anyString());
  }

  @Test public void setColor_shouldSetColor() throws Exception {
    bitmapMarker.setColor(Color.BLUE);
    assertThat(bitmapMarker.getColor()).isEqualTo(Color.BLUE);
  }

  @Test public void setColor_shouldUpdateColorHex() throws Exception {
    bitmapMarker.setColor("test");
    bitmapMarker.setColor(Color.BLUE);
    assertThat(bitmapMarker.getColorHex()).isEqualTo("#ff0000ff");
  }

  @Test public void setColor_hex_shouldCallTangramMarker() throws Exception {
    bitmapMarker.setColor("#222222");
    verify(tangramMarker).setStylingFromString(anyString());
  }

  @Test public void setColor_hex_shouldSetColor() throws Exception {
    bitmapMarker.setColor("hex");
    assertThat(bitmapMarker.getColorHex()).isEqualTo("hex");
  }

  @Test public void setIcon_hex_shouldResetColorInt() throws Exception {
    bitmapMarker.setColor(Color.BLUE);
    bitmapMarker.setColor("test");
    assertThat(bitmapMarker.getColor()).isEqualTo(Integer.MIN_VALUE);
  }

  @Test public void getTangramMarker_shouldReturnMarker() throws Exception {
    Marker marker = mock(Marker.class);
    bitmapMarker.setTangramMarker(marker);
    assertThat(bitmapMarker.getTangramMarker()).isEqualTo(marker);
  }
}
