package com.mapzen.android;

import com.mapzen.tangram.MapController;
import com.mapzen.tangram.MapView;

import org.powermock.api.mockito.PowerMockito;

import android.app.Activity;

class TestMapFactory extends MapFactory {
    @Override MapController getMap(Activity activity, MapView mapView) {
        return PowerMockito.mock(MapController.class);
    }
}
