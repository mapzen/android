package com.mapzen.android.graphics.model;

import com.mapzen.android.graphics.internal.StyleStringGenerator;
import com.mapzen.tangram.LngLat;
import com.mapzen.tangram.MapController;
import com.mapzen.tangram.Marker;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

import android.graphics.Color;
import android.graphics.drawable.Drawable;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@SuppressStaticInitializationFor("com.mapzen.tangram.MapController")
public class BitmapMarkerTest {
  private Marker tangramMarker = mock(Marker.class);
  private MapController mapController = mock(MapController.class);
  private StyleStringGenerator styleStringGenerator  = mock(StyleStringGenerator.class);
  private MarkerManager markerManager = new MarkerManager(mapController, styleStringGenerator);
  private BitmapMarker bitmapMarker = new BitmapMarker(markerManager, tangramMarker,
      styleStringGenerator);

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

  @Test public void setPosition_animated_shouldCallTangramMarker() throws Exception {
    LngLat pos = new LngLat(40, 70);
    bitmapMarker.setPosition(pos, 1, EaseType.CUBIC);
    verify(tangramMarker).setPointEased(pos, 1, MapController.EaseType.CUBIC);
  }

  @Test public void setIcon_shouldCallTangramMarker() throws Exception {
    int resId = 1;
    bitmapMarker.setIcon(resId);
    verify(tangramMarker).setDrawable(resId);
  }

  @Test public void setIcon_drawable_shouldCallTangramMarker() throws Exception {
    Drawable drawable = mock(Drawable.class);
    bitmapMarker.setIcon(drawable);
    verify(tangramMarker).setDrawable(drawable);
  }

  @Test public void setSize_shouldCallTangramMarkerAndStyleStringGenerator() throws Exception {
    int width = 10;
    int height = 5;
    bitmapMarker.setSize(width, height);
    verify(styleStringGenerator).setSize(width, height);
    verify(tangramMarker).setStylingFromString(styleStringGenerator.getStyleString());
  }

  @Test public void setVisible_shouldCallTangramMarker() throws Exception {
    bitmapMarker.setVisible(true);
    verify(tangramMarker).setVisible(true);
  }

  @Test public void setDrawOrder_shouldCallTangramMarker() throws Exception {
    bitmapMarker.setDrawOrder(1);
    verify(tangramMarker).setDrawOrder(1);
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
    verify(styleStringGenerator).setInteractive(true);
    verify(tangramMarker).setStylingFromString(styleStringGenerator.getStyleString());
  }

  @Test public void setBackgroundColor_colorInt_shouldCallTangramMarker() throws Exception {
    bitmapMarker.setBackgroundColor(Color.BLUE);
    verify(styleStringGenerator).setBackgroundColor("#ff0000ff");
    verify(tangramMarker).setStylingFromString(styleStringGenerator.getStyleString());
  }

  @Test public void setBackgroundColor_hex_shouldCallTangramMarker() throws Exception {
    bitmapMarker.setBackgroundColor("#222222");
    verify(styleStringGenerator).setBackgroundColor("#222222");
    verify(tangramMarker).setStylingFromString(styleStringGenerator.getStyleString());
  }
}
