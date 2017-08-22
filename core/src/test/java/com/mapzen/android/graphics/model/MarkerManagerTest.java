package com.mapzen.android.graphics.model;

import com.mapzen.android.graphics.internal.StyleStringGenerator;
import com.mapzen.tangram.LngLat;
import com.mapzen.tangram.MapController;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

import android.graphics.Color;
import android.graphics.drawable.Drawable;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@SuppressStaticInitializationFor("com.mapzen.tangram.MapController")
public class MarkerManagerTest {
  private MapController mapController = mock(MapController.class);
  private com.mapzen.tangram.Marker tangramMarker = mock(com.mapzen.tangram.Marker.class);
  private BitmapMarkerFactory markerFactory = mock(BitmapMarkerFactory.class);
  private MarkerManager markerManager = new MarkerManager(markerFactory,
      new StyleStringGenerator());

  @Before public void setUp() throws Exception {
    markerManager.setMapController(mapController);
    when(mapController.addMarker()).thenReturn(tangramMarker);
    when(markerFactory.createMarker(any(MarkerManager.class), any(com.mapzen.tangram.Marker.class),
        any(StyleStringGenerator.class))).thenReturn(mock(BitmapMarker.class));
  }

  @Test public void shouldNotBeNull() throws Exception {
    assertThat(markerManager).isNotNull();
  }

  @Test public void addMarker_shouldAddMarkerToMapController() throws Exception {
    markerManager.addMarker(new MarkerOptions());
    verify(mapController).addMarker();
  }

  @Test public void addMarker_shouldSetPosition() throws Exception {
    LngLat lngLat = new LngLat();
    MarkerOptions markerOptions = new MarkerOptions().position(lngLat);
    BitmapMarker marker = markerManager.addMarker(markerOptions);
    verify(marker).setPosition(lngLat);
  }

  @Test public void addMarker_resId_shouldSetDrawable() throws Exception {
    int resId = 123;
    MarkerOptions markerOptions = new MarkerOptions().icon(resId);
    BitmapMarker marker = markerManager.addMarker(markerOptions);
    verify(marker).setIcon(resId);
    verify(marker, never()).setIcon(any(Drawable.class));
  }

  @Test public void addMarker_res_shouldSetDrawable() throws Exception {
    Drawable res = mock(Drawable.class);
    MarkerOptions markerOptions = new MarkerOptions().icon(res);
    BitmapMarker marker = markerManager.addMarker(markerOptions);
    verify(marker).setIcon(res);
    verify(marker, never()).setIcon(anyInt());
  }

  @Test public void addMarker_shouldSetSize() throws Exception {
    MarkerOptions markerOptions = new MarkerOptions().size(100, 100);
    BitmapMarker marker = markerManager.addMarker(markerOptions);
    verify(marker).setSize(100, 100);
  }

  @Test public void addMarker_shouldReturnBitmapMarker() throws Exception {
    assertThat(markerManager.addMarker(new MarkerOptions())).isNotNull();
  }

  @Test public void addMarker_shouldSetVisibile() throws Exception {
    MarkerOptions markerOptions = new MarkerOptions().visible(false);
    BitmapMarker marker = markerManager.addMarker(markerOptions);
    verify(marker).setVisible(false);
  }

  @Test public void addMarker_shouldSetUserData() throws Exception {
    Object data = mock(Object.class);
    MarkerOptions markerOptions = new MarkerOptions().userData(data);
        BitmapMarker marker = markerManager.addMarker(markerOptions);
    verify(marker).setUserData(data);
  }

  @Test public void addMarker_shouldSetDrawOrder() throws Exception {
    MarkerOptions markerOptions = new MarkerOptions().drawOrder(8);
        BitmapMarker marker = markerManager.addMarker(markerOptions);
    verify(marker).setDrawOrder(8);
  }

  @Test public void addMarker_shouldSetColorHex() throws Exception {
    MarkerOptions markerOptions = new MarkerOptions().colorHex("#fff");
        BitmapMarker marker = markerManager.addMarker(markerOptions);
    verify(marker).setColor("#fff");
  }

  @Test public void addMarker_shouldSetColorInt() throws Exception {
    MarkerOptions markerOptions = new MarkerOptions().colorInt(Color.RED);
        BitmapMarker marker = markerManager.addMarker(markerOptions);
    verify(marker).setColor(Color.RED);
  }

  @Test public void addMarker_shouldSetInteractive() throws Exception {
    MarkerOptions markerOptions = new MarkerOptions().interactive(false);
        BitmapMarker marker = markerManager.addMarker(markerOptions);
    verify(marker).setInteractive(false);
  }

  @Test public void restoreMarkers_shouldProperlyRestoreManagedMarkers() throws Exception {
    Drawable drawable = mock(Drawable.class);
    LngLat point = new LngLat(40, 70);
    int width = 10;
    int height = 20;
    boolean isVisible = true;
    int drawOrder = 20;
    Map userData = mock(HashMap.class);
    String colorHex = "#ff00ff";
    boolean isInteractive = true;
    MarkerOptions options = new MarkerOptions()
        .icon(drawable)
        .position(point)
        .size(width, height)
        .colorHex(colorHex)
        .drawOrder(drawOrder)
        .visible(isVisible)
        .userData(userData)
        .interactive(isInteractive);
    BitmapMarker marker = markerManager.addMarker(options);
    markerManager.restoreMarkers();

    verify(marker).setTangramMarker(tangramMarker);
    verify(marker).setPosition(point);
    verify(marker).setIcon(drawable);
    verify(marker).setSize(width, height);
    verify(marker).setColor(colorHex);
    verify(marker).setInteractive(isInteractive);
    verify(marker).setVisible(isVisible);
    verify(marker).setDrawOrder(drawOrder);
    verify(marker).setUserData(userData);
  }

  @Test public void restoreMarkers_shouldProperlyRestoreAllManagedMarkers() throws Exception {
    Drawable drawable = mock(Drawable.class);
    LngLat point = new LngLat(40, 70);
    int width = 10;
    int height = 20;
    boolean isVisible = true;
    int drawOrder = 20;
    Map userData = mock(HashMap.class);
    int colorInt = Color.BLUE;
    boolean isInteractive = true;
    MarkerOptions options = new MarkerOptions()
        .icon(drawable)
        .position(point)
        .size(width, height)
        .visible(isVisible)
        .drawOrder(drawOrder)
        .userData(userData)
        .colorInt(colorInt)
        .interactive(isInteractive);

    BitmapMarker marker = mock(BitmapMarker.class);
    when(markerFactory.createMarker(any(MarkerManager.class), any(com.mapzen.tangram.Marker.class),
        any(StyleStringGenerator.class))).thenReturn(marker);
    when(marker.getPosition()).thenReturn(point);
    when(marker.getIconDrawable()).thenReturn(drawable);
    when(marker.getWidth()).thenReturn(width);
    when(marker.getHeight()).thenReturn(height);
    when(marker.isInteractive()).thenReturn(isInteractive);
    when(marker.getColor()).thenReturn(colorInt);
    when(marker.isVisible()).thenReturn(isVisible);
    when(marker.getDrawOrder()).thenReturn(drawOrder);
    when(marker.getUserData()).thenReturn(userData);

    markerManager.addMarker(options);
    markerManager.addMarker(options);
    markerManager.restoreMarkers();

    //times(2) 2 markers and 1x 1x when restoring
    verify(marker, times(2)).setTangramMarker(tangramMarker);
    //times(4) 2 markers and 1x for when adding each marker, 1x when restoring
    verify(marker, times(4)).setPosition(point);
    verify(marker, times(4)).setIcon(drawable);
    verify(marker, times(4)).setSize(width, height);
    verify(marker, times(4)).setColor(Color.BLUE);
    verify(marker, times(4)).setInteractive(isInteractive);
    verify(marker, times(4)).setVisible(isVisible);
    verify(marker, times(4)).setDrawOrder(drawOrder);
    verify(marker, times(4)).setUserData(userData);
  }

}
