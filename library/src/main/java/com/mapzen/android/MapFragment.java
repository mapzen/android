package com.mapzen.android;

import com.mapzen.android.R;
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

    private MapView mapView;
    private MapFactory mapFactory;
    private Activity activity;

    /**
     * Creates a map fragment using the default {@link MapFactory}.
     */
    public MapFragment() {
        this(new MapFactory());
    }

    /**
     * Creates a map fragment using the specified {@link MapFactory}.
     */
    MapFragment(MapFactory mapFactory) {
        this.mapFactory = mapFactory;
    }

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
     * Synchronously creates the map and configures the vector tiles API key using the string
     * resource declared in the client application.
     * @return a newly created {@link MapController} instance.
     */
    public MapController getMap() {
        final Resources res = activity.getResources();
        final MapController mapController = mapFactory.getMap(getActivity(), mapView);
        final int apiKeyId = res.getIdentifier(RES_NAME, RES_TYPE, activity.getPackageName());
        final String apiKey = res.getString(apiKeyId);

        mapController.setHttpHandler(new TileHttpHandler(apiKey));
        return mapController;
    }
}
