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
            @NonNull OnMapReadyCallback callback,
            @NonNull String sceneFilePath) {
        callback.onMapReady(mock(MapController.class));
    }

    @Override public TangramMap getTangramMap() {
        return new TestTangramMapView(mock(Context.class));
    }
}
