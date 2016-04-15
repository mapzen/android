package com.mapzen.android;

import com.mapzen.tangram.MapController;

import android.content.Context;
import android.content.res.Resources;

import javax.inject.Inject;

/**
 * Class responsible for initializing the map.
 */
class MapInitializer {
    private static final String DEFAULT_SCENE_FILE = "style/bubble-wrap.yaml";
    private static final String API_KEY_RES_NAME = "vector_tiles_key";
    private static final String API_KEY_RES_TYPE = "string";

    private final Context context;
    private final Resources res;

    /**
     * Creates a new instance.
     */
    @Inject MapInitializer(Context context, Resources res) {
        this.context = context;
        this.res = res;
    }

    /**
     * Initialize map for the current {@link MapView} and notify via
     * {@link MapView.OnMapReadyCallback}.
     */
    public void init(final MapView mapView, final MapView.OnMapReadyCallback callback) {
        final String packageName = context.getPackageName();
        final int apiKeyId = res.getIdentifier(API_KEY_RES_NAME, API_KEY_RES_TYPE, packageName);
        final String apiKey = res.getString(apiKeyId);

        mapView.getMapAsync(new com.mapzen.tangram.MapView.OnMapReadyCallback() {
            @Override public void onMapReady(MapController mapController) {
                mapController.setHttpHandler(new TileHttpHandler(apiKey));
                mapView.mapController = mapController;
                callback.onMapReady(mapController);
            }
        }, DEFAULT_SCENE_FILE);
    }
}
