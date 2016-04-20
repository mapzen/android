package com.mapzen.android;

import com.mapzen.tangram.MapController;

import org.mockito.Mockito;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;

public class TestTangramMapView extends TangramMapView {

    public TestTangramMapView(Context context) {
        super(context);
    }

    public TestTangramMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void getMapAsync(@NonNull
    final OnMapReadyCallback callback,
            @NonNull final String sceneFilePath) {
        MapController mapController = Mockito.mock(MapController.class);
        callback.onMapReady(mapController);
    }

}
