package com.mapzen.android.graphics;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

import android.app.Activity;
import android.content.res.Resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class) @SuppressStaticInitializationFor("com.mapzen.tangram.MapController")
public class MapFragmentTest {

  private MapFragment mapFragment;

  @Before public void setUp() throws Exception {
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

  @Test public void getMap_shouldSetHttpHandler() throws Exception {
    TestCallback callback = new TestCallback();
    mapFragment.getMapAsync(callback);
    verify(callback.map.getMapController(), times(1)).setHttpHandler((TileHttpHandler) any());
  }
}
