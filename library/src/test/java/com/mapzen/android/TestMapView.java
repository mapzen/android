package com.mapzen.android;

import com.mapzen.tangram.MapController;

import android.support.annotation.NonNull;

import static com.mapzen.android.TestHelper.getMockContext;
import static org.mockito.Mockito.mock;

public class TestMapView extends MapView {
    TestMapView() {
        super(getMockContext());
    }

    @Override public void getMapAsync(
            @NonNull com.mapzen.tangram.MapView.OnMapReadyCallback callback,
            @NonNull final String sceneFilePath) {
        callback.onMapReady(mock(MapController.class));
    }
}
