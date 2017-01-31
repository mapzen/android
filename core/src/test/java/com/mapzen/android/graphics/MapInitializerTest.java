package com.mapzen.android.graphics;

import com.mapzen.android.core.CoreDI;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

import android.content.Context;

import static com.mapzen.TestHelper.getMockContext;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.net.ssl.*")
@SuppressStaticInitializationFor("com.mapzen.tangram.MapController")
public class MapInitializerTest {

  private MapInitializer mapInitializer;

  @Before public void setUp() throws Exception {
    CoreDI.init(getMockContext());
    mapInitializer = new MapInitializer(mock(Context.class), mock(TileHttpHandler.class),
        new MapDataManager(), new MapStateManager());
  }

  @Test public void shouldNotBeNull() throws Exception {
    assertThat(mapInitializer).isNotNull();
  }

  @Test public void init_shouldReturnMapzenMap() throws Exception {
    final TestCallback callback = new TestCallback();
    final TestMapView mapView = new TestMapView();
    mapInitializer.init(mapView, "test_api_key", callback);
    assertThat(callback.map).isInstanceOf(MapzenMap.class);
  }
}
