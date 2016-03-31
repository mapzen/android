package com.mapzen.android.app;

import com.mapzen.android.R;
import com.mapzen.tangram.MapController;
import com.mapzen.tangram.MapView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MapFragment extends Fragment {

    public MapFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        final MapView mapView = (MapView) inflater.inflate(R.layout.fragment_map, container, false);
        new MapController(getActivity(), mapView);
        return mapView;
    }
}
