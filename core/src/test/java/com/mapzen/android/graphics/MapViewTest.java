package com.mapzen.android.graphics;

import com.mapzen.R;

import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import static com.mapzen.TestHelper.getMockContext;
import static com.mapzen.android.graphics.MapView.OVERLAY_MODE_CLASSIC;
import static com.mapzen.android.graphics.MapView.OVERLAY_MODE_SDK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MapViewTest {
  private MapView mapView;

  @Before public void setup() throws Exception {
    mapView = PowerMockito.spy(new MapView(getMockContext()));
    when(mapView.getTangramMapView()).thenReturn(mock(TangramMapView.class));
  }

  @Test public void shouldNotBeNull() throws Exception {
    assertThat(mapView).isNotNull();
  }

  @Test public void getMapAsync_shouldInjectMapInitializer() throws Exception {
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

  @Test public void getMapAsync_shouldInvokeMapInitializerWithApiKey() throws Exception {
    final MapInitializer mapInitializer = mock(MapInitializer.class);
    final OnMapReadyCallback callback = new TestCallback();
    final String key = "vector-tiles-test-key";
    mapView.mapInitializer = mapInitializer;
    mapView.getMapAsync(key, callback);
    verify(mapInitializer, times(1)).init(mapView, key, callback);
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
