package com.mapzen.android.sample;

import com.mapzen.android.MapFragment;
import com.mapzen.android.MapzenMap;
import com.mapzen.android.OnMapReadyCallback;
import com.mapzen.android.OverlayManager;
import com.mapzen.tangram.MapController;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Basic SDK demo, tracks user's current location on map.
 */
public class BasicMapzenActivity extends AppCompatActivity {

    MapFragment mapFragment;
    MapController mapController;
    OverlayManager overlayManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_mapzen);

        mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override public void onMapReady(MapzenMap map) {
                BasicMapzenActivity.this.mapController = map.getMapController();
                configureMap();
            }
        });
    }

    private void configureMap() {
        overlayManager = mapFragment.getOverlayManager();
        overlayManager.setMyLocationEnabled(true);
    }
}
