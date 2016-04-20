package com.mapzen.android;

import com.mapzen.android.dagger.DI;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

import static com.mapzen.android.TestHelper.getMockContext;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@SuppressStaticInitializationFor("com.mapzen.tangram.MapController")
public class MapInitializerTest {

    private MapInitializer mapInitializer;

    @Before
    public void setUp() throws Exception {
        DI.init(getMockContext());
        mapInitializer = new MapInitializer();
    }

    @Test public void shouldNotBeNull() throws Exception {
        assertThat(mapInitializer).isNotNull();
    }

    @Test public void init_shouldReturnMapzenMap() throws Exception {
        final TestCallback callback = new TestCallback();
        final TestMapView mapView = new TestMapView();
        mapInitializer.init(mapView, callback);
        assertThat(callback.map).isInstanceOf(MapzenMap.class);
    }

    @Test public void init_shouldSetHttpHandler() throws Exception {
        final TestCallback callback = new TestCallback();
        final TestMapView mapView = new TestMapView();
        mapInitializer.init(mapView, callback);
        verify(callback.map.getMapController(), times(1)).setHttpHandler((TileHttpHandler) any());
    }

    @Test public void init_shouldSetHttpHandlerWithGivenApiKey() throws Exception {
        final TestCallback callback = new TestCallback();
        final TestMapView mapView = new TestMapView();
        final String key = "vector-tiles-test-key";
        mapInitializer.init(mapView, key, callback);
        assertThat(mapInitializer.httpHandler.getApiKey()).isEqualTo(key);
    }
}
