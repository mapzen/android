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
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
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
  private MarkerManager markerManager = new MarkerManager(mapController, markerFactory,
      new StyleStringGenerator());

  @Before public void setUp() throws Exception {
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
    markerManager.addMarker(markerOptions);
    verify(tangramMarker).setPoint(lngLat);
  }

  @Test public void addMarker_resId_shouldSetDrawable() throws Exception {
    int resId = 123;
    MarkerOptions markerOptions = new MarkerOptions().icon(resId);
    markerManager.addMarker(markerOptions);
    verify(tangramMarker).setDrawable(resId);
    verify(tangramMarker, never()).setDrawable(any(Drawable.class));
  }

  @Test public void addMarker_res_shouldSetDrawable() throws Exception {
    Drawable res = mock(Drawable.class);
    MarkerOptions markerOptions = new MarkerOptions().icon(res);
    markerManager.addMarker(markerOptions);
    verify(tangramMarker).setDrawable(res);
    verify(tangramMarker, never()).setDrawable(anyInt());
  }

  @Test public void addMarker_shouldSetStyling() throws Exception {
    MarkerOptions markerOptions = new MarkerOptions();
    markerManager.addMarker(markerOptions);
    verify(tangramMarker).setStylingFromString(anyString());
  }

  @Test public void addMarker_shouldSetSize() throws Exception {
    MarkerOptions markerOptions = new MarkerOptions().size(100, 100);
    markerManager.addMarker(markerOptions);
    verify(tangramMarker).setStylingFromString("{ style: 'points', color: '#fff', "
        + "size: [100px, 100px], collide: false, interactive: true }");
  }

  @Test public void addMarker_shouldReturnBitmapMarker() throws Exception {
    assertThat(markerManager.addMarker(new MarkerOptions())).isNotNull();
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
    when(marker.getStyleStringGenerator()).thenReturn(new StyleStringGenerator());

    markerManager.restoreMarkers();

    verify(tangramMarker).setPoint(point);
    verify(tangramMarker).setDrawable(drawable);
    verify(tangramMarker).setStylingFromString("{ style: 'points', color: '#ff00ff', "
        + "size: [10px, 20px], collide: false, interactive: true }");
    verify(marker).setTangramMarker(any(Marker.class));
    verify(tangramMarker).setVisible(isVisible);
    verify(tangramMarker).setDrawOrder(drawOrder);
    verify(tangramMarker).setUserData(userData);
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

    BitmapMarker marker = markerManager.addMarker(options);
    BitmapMarker anotherMarker = markerManager.addMarker(options);
    StyleStringGenerator styleStringGenerator = new StyleStringGenerator();
    when(marker.getStyleStringGenerator()).thenReturn(styleStringGenerator);
    when(anotherMarker.getStyleStringGenerator()).thenReturn(styleStringGenerator);
    when(marker.getPosition()).thenReturn(point);
    when(anotherMarker.getPosition()).thenReturn(point);
    when(marker.getIconDrawable()).thenReturn(drawable);
    when(anotherMarker.getIconDrawable()).thenReturn(drawable);
    when(marker.getWidth()).thenReturn(width);
    when(anotherMarker.getWidth()).thenReturn(width);
    when(marker.getHeight()).thenReturn(height);
    when(anotherMarker.getHeight()).thenReturn(height);
    when(marker.isInteractive()).thenReturn(isInteractive);
    when(anotherMarker.isInteractive()).thenReturn(isInteractive);
    when(marker.getColor()).thenReturn(colorInt);
    when(anotherMarker.getColor()).thenReturn(colorInt);
    when(marker.isVisible()).thenReturn(isVisible);
    when(anotherMarker.isVisible()).thenReturn(isVisible);
    when(marker.getDrawOrder()).thenReturn(drawOrder);
    when(anotherMarker.getDrawOrder()).thenReturn(drawOrder);
    when(marker.getUserData()).thenReturn(userData);
    when(anotherMarker.getUserData()).thenReturn(userData);

    markerManager.restoreMarkers();

    //times(4) instead of times(2) because 1x for adding each marker, 1x when restoring
    verify(tangramMarker, times(4)).setPoint(point);
    verify(tangramMarker, times(4)).setDrawable(drawable);
    verify(tangramMarker, times(4)).setStylingFromString(styleStringGenerator.getStyleString(
        width, height, isInteractive, "#ff0000ff"));
    verify(marker, times(2)).setTangramMarker(tangramMarker);
    verify(anotherMarker, times(2)).setTangramMarker(tangramMarker);
    verify(tangramMarker, times(4)).setVisible(isVisible);
    verify(tangramMarker, times(4)).setDrawOrder(drawOrder);
    verify(tangramMarker, times(4)).setUserData(userData);
  }

}
