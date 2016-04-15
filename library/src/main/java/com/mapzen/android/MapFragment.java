package com.mapzen.android;

import com.mapzen.tangram.MapController;
import com.mapzen.tangram.MapView;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A map component in an app. This fragment is the simplest way to place a map in an application.
 * It's a wrapper around a view of a map to automatically handle the necessary life cycle needs.
 */
public class MapFragment extends Fragment {
    private static final String RES_NAME = "vector_tiles_key";
    private static final String RES_TYPE = "string";

    private Activity activity;
    private OverlayManager mapManager;
    private MapController mapController;

    MapView mapView;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mapView = (MapView) inflater.inflate(R.layout.fragment_map, container, false);
        return mapView;
    }

    @Override public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    /**
     * Asynchronously creates the map and configures the vector tiles API key using the string
     * resource declared in the client application. Uses default stylesheet (bubble wrap).
     */
    public void getMapAsync(final MapView.OnMapReadyCallback callback) {
        final Resources res = activity.getResources();
        final int apiKeyId = res.getIdentifier(RES_NAME, RES_TYPE, activity.getPackageName());
        final String apiKey = res.getString(apiKeyId);

        mapView.getMapAsync(new MapView.OnMapReadyCallback() {
            @Override public void onMapReady(MapController mapController) {
                MapFragment.this.mapController = mapController;
                mapController.setHttpHandler(new TileHttpHandler(apiKey));
                callback.onMapReady(mapController);
            }
        }, "style/bubble-wrap.yaml");
    }

    /**
     * Synchronously creates map manager for interaction with map and location manager.
     *
     * @return newly created {@link OverlayManager} instance or existing instance
     */
    public OverlayManager getMapManager() {
        if (mapController == null) {
            return null;
        }
        if (mapManager != null) {
            return mapManager;
        }
        mapManager = new OverlayManager(getContext(), mapController);
        return mapManager;
    }

    @Override public void onDestroy() {
        super.onDestroy();
        if (mapManager != null) {
            mapManager.onDestroy();
        }
    }
}
