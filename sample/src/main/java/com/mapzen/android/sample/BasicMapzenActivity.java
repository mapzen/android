package com.mapzen.android.sample;

import com.mapzen.android.MapFragment;
import com.mapzen.android.MapManager;
import com.mapzen.android.MapView;
import com.mapzen.tangram.MapController;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Basic SDK demo, tracks user's current location on map.
 */
public class BasicMapzenActivity extends AppCompatActivity {

    MapFragment mapFragment;
    MapController mapController;
    MapManager mapManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_mapzen);

        mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        mapFragment.getMapAsync(new MapView.OnMapReadyCallback() {
            @Override public void onMapReady(MapController mapController) {
                BasicMapzenActivity.this.mapController = mapController;
                configureMap();
            }
        });
    }

    private void configureMap() {
        mapManager = mapFragment.getMapManager();
        mapManager.setMyLocationEnabled(true);
    }
}
