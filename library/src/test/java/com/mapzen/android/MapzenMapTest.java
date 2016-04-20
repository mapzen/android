package com.mapzen.android;

import com.mapzen.tangram.MapController;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(PowerMockRunner.class)
@SuppressStaticInitializationFor("com.mapzen.tangram.MapController")
public class MapzenMapTest {

    private MapzenMap map;

    @Before public void setUp() throws Exception {
        map = new MapzenMap(mock(MapController.class));
    }

    @Test public void shouldNotBeNull() throws Exception {
        assertThat(map).isNotNull();
    }

    @Test public void getMapController_shouldNotBeNull() throws Exception {
        assertThat(map.getMapController()).isNotNull();
    }
}
