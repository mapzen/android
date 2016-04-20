package com.mapzen.android;

import com.mapzen.android.dagger.DI;
import com.mapzen.tangram.MapController;

import javax.inject.Inject;

/**
 * Class responsible for initializing the map.
 */
public class MapInitializer {
    private static final String DEFAULT_SCENE_FILE = "style/bubble-wrap.yaml";

    @Inject TileHttpHandler httpHandler;

    /**
     * Creates a new instance.
     */
    @Inject MapInitializer() {
        DI.component().inject(this);
    }

    /**
     * Initialize map for the current {@link MapView} and notify via
     * {@link MapView.OnMapReadyCallback}.
     */
    public void init(final MapView mapView, final MapView.OnMapReadyCallback callback) {
        loadMap(mapView, callback);
    }

    /**
     * Initialize map for the current {@link MapView} with given API key and notify via
     * {@link MapView.OnMapReadyCallback}.
     */
    public void init(final MapView mapView, final MapView.OnMapReadyCallback callback, String key) {
        httpHandler.setApiKey(key);
        loadMap(mapView, callback);
    }

    private TangramMap getTangramView(final MapView mapView) {
        return mapView.getTangramMap();
    }

    private void loadMap(final MapView mapView, final MapView.OnMapReadyCallback callback) {
        getTangramView(mapView).getMapAsync(new com.mapzen.tangram.MapView.OnMapReadyCallback() {
            @Override public void onMapReady(MapController mapController) {
                mapController.setHttpHandler(httpHandler);
                mapView.mapController = mapController;
                callback.onMapReady(mapController);
            }
        }, DEFAULT_SCENE_FILE);
    }
}
