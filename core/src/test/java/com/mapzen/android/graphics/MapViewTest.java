package com.mapzen.android.graphics;

import com.mapzen.R;
import com.mapzen.android.core.MapzenManager;
import com.mapzen.android.graphics.model.BubbleWrapStyle;
import com.mapzen.android.graphics.model.MapStyle;
import com.mapzen.tangram.MapController;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import java.util.Locale;

import static com.mapzen.TestHelper.getMockContext;
import static com.mapzen.android.graphics.MapView.OVERLAY_MODE_CLASSIC;
import static com.mapzen.android.graphics.MapView.OVERLAY_MODE_SDK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.net.ssl.*")
@SuppressStaticInitializationFor("com.mapzen.tangram.MapController")
public class MapViewTest {
  private MapView mapView;

  @Before public void setup() throws Exception {
    mapView = PowerMockito.spy(new MapView(getMockContext()));
    TangramMapView tangramMapView = mock(TangramMapView.class);
    when(mapView.getTangramMapView()).thenReturn(tangramMapView);
    when(tangramMapView.getMap(any(MapController.SceneLoadListener.class))).thenReturn(mock(
        MapController.class));
    MapzenManager.instance(getMockContext()).setApiKey("fake-mapzen-api-key");
  }

  @After public void tearDown() throws Exception {
    MapzenManager.instance(getMockContext()).setApiKey(null);
  }

  @Test public void shouldNotBeNull() throws Exception {
    assertThat(mapView).isNotNull();
  }

  @Test public void getMapAsync_shouldInjectMapInitializer() throws Exception {
    MapzenManager.instance(getMockContext()).setApiKey("fake-mapzen-api-key");
    mapView.getMapAsync(new TestCallback());
    assertThat(mapView.mapInitializer).isNotNull();
  }

  @Test public void getMapAsync_shouldInvokeMapInitializer() throws Exception {
    final MapInitializer mapInitializer = mock(MapInitializer.class);
    final OnMapReadyCallback callback = new TestCallback();
    mapView.mapInitializer = mapInitializer;
    mapView.getMapAsync(callback);
    verify(mapInitializer, times(1)).init(mapView, callback);
  }

  @Test public void getMapAsync_shouldUseGivenLocale() throws Exception {
    final MapInitializer mapInitializer = mock(MapInitializer.class);
    final OnMapReadyCallback callback = new TestCallback();
    final MapStyle mapStyle = new BubbleWrapStyle();
    mapView.mapInitializer = mapInitializer;
    mapView.getMapAsync(mapStyle, Locale.FRENCH, callback);
    verify(mapInitializer, times(1)).init(mapView, mapStyle, Locale.FRENCH, callback);
  }

  @Test public void shouldInflateLayoutWithOverlayModeSdk() throws Exception {
    Context context = mock(Context.class);
    TypedArray typedArray = mock(TypedArray.class);
    TestLayoutInflater layoutInflater = new TestLayoutInflater(context);
    initMockAttributes(context, typedArray, layoutInflater, OVERLAY_MODE_SDK);

    MapView mapView = new MapView(context, mock(AttributeSet.class));

    assertThat(layoutInflater.getResId()).isEqualTo(R.layout.mz_view_map);
    assertThat(layoutInflater.getRoot()).isEqualTo(mapView);
  }

  @Test public void shouldInflateLayoutWithOverlayModeClassic() throws Exception {
    Context context = mock(Context.class);
    TypedArray typedArray = mock(TypedArray.class);
    TestLayoutInflater layoutInflater = new TestLayoutInflater(context);
    initMockAttributes(context, typedArray, layoutInflater, OVERLAY_MODE_CLASSIC);

    MapView mapView = new MapView(context, mock(AttributeSet.class));

    assertThat(layoutInflater.getResId()).isEqualTo(R.layout.mz_view_map_classic);
    assertThat(layoutInflater.getRoot()).isEqualTo(mapView);
  }

  @Test public void onDestroy_shouldDestroyMapzenMap() throws Exception {
    MapzenMap map = mock(MapzenMap.class);
    mapView.setMapzenMap(map);
    mapView.mapInitializer = mock(MapInitializer.class);
    mapView.onDestroy();
    verify(map).onDestroy();
  }

  @Test public void onDestroy_shouldTakedownInitializer() throws Exception {
    MapInitializer initializer = mock(MapInitializer.class);
    mapView.mapInitializer = initializer;
    mapView.onDestroy();
    verify(initializer).takeDown();
  }

  private void initMockAttributes(Context context, TypedArray typedArray,
      TestLayoutInflater layoutInflater, int overlayMode) {
    when(context.obtainStyledAttributes(any(AttributeSet.class), eq(R.styleable.MapView)))
        .thenReturn(typedArray);
    when(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
        .thenReturn(layoutInflater);
    when(typedArray.getInteger(R.styleable.MapView_overlayMode, OVERLAY_MODE_SDK))
        .thenReturn(overlayMode);
  }
}
