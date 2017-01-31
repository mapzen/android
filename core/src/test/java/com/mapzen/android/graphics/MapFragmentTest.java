package com.mapzen.android.graphics;

import com.mapzen.R;
import com.mapzen.android.OkHttp3TestUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import static com.mapzen.android.graphics.MapView.OVERLAY_MODE_CLASSIC;
import static com.mapzen.android.graphics.MapView.OVERLAY_MODE_SDK;
import okhttp3.internal.tls.CertificateChainCleaner;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ TrustManagerFactory.class, SSLContext.class, CertificateChainCleaner.class })
@SuppressStaticInitializationFor("com.mapzen.tangram.MapController")
public class MapFragmentTest {

  private MapFragment mapFragment;

  @Before public void setUp() throws Exception {
    OkHttp3TestUtils.initMockSslContext();
    final Resources resources = mock(Resources.class);
    when(resources.getIdentifier((String) any(), (String) any(), (String) any())).thenReturn(0);

    final Activity activity = mock(Activity.class);
    when(activity.getResources()).thenReturn(resources);
    when(activity.getPackageName()).thenReturn("");

    mapFragment = new MapFragment();
    mapFragment.mapView = new TestMapView();
    mapFragment.onAttach(activity);
  }

  @Test public void shouldNotBeNull() throws Exception {
    assertThat(mapFragment).isNotNull();
  }

  @Test public void getMap_shouldReturnMapzenMap() throws Exception {
    TestCallback callback = new TestCallback();
    mapFragment.getMapAsync(callback);
    assertThat(callback.map).isInstanceOf(MapzenMap.class);
  }

  @Test public void shouldInflateLayoutWithOverlayModeSdk() throws Exception {
    Context context = mock(Context.class);
    TypedArray typedArray = mock(TypedArray.class);
    ViewGroup viewGroup = mock(ViewGroup.class);
    TestLayoutInflater layoutInflater = new TestLayoutInflater(context);
    initMockAttributes(context, typedArray, layoutInflater, OVERLAY_MODE_SDK);

    mapFragment = new MapFragment();
    mapFragment.onInflate(context, mock(AttributeSet.class), null);
    mapFragment.onCreateView(layoutInflater, viewGroup, null);

    assertThat(layoutInflater.getResId()).isEqualTo(R.layout.mz_fragment_map);
    assertThat(layoutInflater.getRoot()).isEqualTo(viewGroup);
  }

  @Test public void shouldInflateLayoutWithOverlayModeClassic() throws Exception {
    Context context = mock(Context.class);
    TypedArray typedArray = mock(TypedArray.class);
    ViewGroup viewGroup = mock(ViewGroup.class);
    TestLayoutInflater layoutInflater = new TestLayoutInflater(context);
    initMockAttributes(context, typedArray, layoutInflater, OVERLAY_MODE_CLASSIC);

    mapFragment = new MapFragment();
    mapFragment.onInflate(context, mock(AttributeSet.class), null);
    mapFragment.onCreateView(layoutInflater, viewGroup, null);

    assertThat(layoutInflater.getResId()).isEqualTo(R.layout.mz_fragment_map_classic);
    assertThat(layoutInflater.getRoot()).isEqualTo(viewGroup);
  }

  private void initMockAttributes(Context context, TypedArray typedArray,
      TestLayoutInflater layoutInflater, int overlayMode) {
    when(context.obtainStyledAttributes(any(AttributeSet.class), eq(R.styleable.MapFragment)))
        .thenReturn(typedArray);
    when(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
        .thenReturn(layoutInflater);
    when(typedArray.getInteger(R.styleable.MapFragment_overlayMode, OVERLAY_MODE_SDK))
        .thenReturn(overlayMode);
  }
}
