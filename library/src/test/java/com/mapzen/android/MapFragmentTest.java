package com.mapzen.android;

import com.mapzen.tangram.MapController;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

import android.app.Activity;
import android.content.res.Resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@SuppressStaticInitializationFor("com.mapzen.tangram.MapController")
public class MapFragmentTest {

    private MapFragment mapFragment;

    @Before
    public void setUp() throws Exception {
        final Resources resources = PowerMockito.mock(Resources.class);
        when(resources.getIdentifier((String) any(), (String) any(), (String) any())).thenReturn(0);

        final Activity activity = PowerMockito.mock(Activity.class);
        when(activity.getResources()).thenReturn(resources);
        when(activity.getPackageName()).thenReturn("");

        mapFragment = new MapFragment(new TestMapFactory());
        mapFragment.onAttach(activity);
    }

    @Test
    public void shouldNotBeNull() throws Exception {
        assertThat(mapFragment).isNotNull();
    }

    @Test
    public void getMap_shouldReturnMapController() throws Exception {
        assertThat(mapFragment.getMap()).isInstanceOf(MapController.class);
    }

    @Test
    public void getMap_shouldSetHttpHandler() throws Exception {
        MapController mapController = mapFragment.getMap();
        Mockito.verify(mapController, times(1)).setHttpHandler((TileHttpHandler) Mockito.any());
    }
}
