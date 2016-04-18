package com.mapzen.android;

import com.mapzen.tangram.MapController;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

import android.content.Context;
import android.content.res.Resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@SuppressStaticInitializationFor("com.mapzen.tangram.MapController")
public class MapInitializerTest {

    private Context context = mock(Context.class);
    private Resources res = mock(Resources.class);
    private MapInitializer mapInitializer = new MapInitializer(context, res);

    @Test public void shouldNotBeNull() throws Exception {
        assertThat(mapInitializer).isNotNull();
    }

    @Test
    public void init_shouldReturnMapController() throws Exception {
        final TestCallback callback = new TestCallback();
        final TestMapView mapView = new TestMapView();
        mapInitializer.init(mapView, callback);
        assertThat(callback.map).isInstanceOf(MapController.class);
    }

    @Test
    public void init_shouldSetHttpHandler() throws Exception {
        final TestCallback callback = new TestCallback();
        final TestMapView mapView = new TestMapView();
        mapInitializer.init(mapView, callback);
        verify(callback.map, times(1)).setHttpHandler((TileHttpHandler) Mockito.any());
    }
}
