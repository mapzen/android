package com.mapzen.android;

import com.mapzen.tangram.MapController;

import android.content.Context;
import android.support.annotation.NonNull;

import static com.mapzen.android.TestHelper.getMockContext;
import static org.mockito.Mockito.mock;

public class TestMapView extends MapView {
    TestMapView() {
        super(getMockContext());
    }

    @Override public void getMapAsync(
            @NonNull String sceneFilePath,
            @NonNull OnMapReadyCallback callback) {
        callback.onMapReady(new MapzenMap(mock(MapController.class), mock(OverlayManager.class)));
    }

    @Override public TangramMapView getTangramMapView() {
        return new TestTangramMapView(mock(Context.class));
    }
}
