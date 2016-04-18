package com.mapzen.android.sample;

import com.mapzen.android.MapFragment;
import com.mapzen.android.OverlayManager;
import com.mapzen.android.model.Marker;
import com.mapzen.tangram.LngLat;
import com.mapzen.tangram.MapController;
import com.mapzen.tangram.MapData;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Show map markers
 */
public class MarkerMapzenActivity extends AppCompatActivity {
    MapFragment mapFragment;
    MapController mapController;
    OverlayManager mapManager;
    MapData marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_mapzen);

        mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        mapFragment.getMapAsync(new com.mapzen.android.MapView.OnMapReadyCallback() {
            @Override public void onMapReady(MapController mapController) {
                MarkerMapzenActivity.this.mapController = mapController;
                configureMap();
            }
        });
    }

    private void configureMap() {
        mapManager = mapFragment.getOverlayManager();
        marker = mapManager.addMarker(new Marker(-73.9903, 40.74433));
        mapManager.addMarker(new Marker(-73.984770, 40.734807));
        mapManager.addMarker(new Marker(-73.998674, 40.732172));
        mapManager.addMarker(new Marker(-73.996142, 40.741050));

        mapController.setZoom(15f);
        mapController.setPosition(new LngLat(-73.9918, 40.73633));
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        cleanupMap();
    }

    private void cleanupMap() {
        marker.remove();
    }
}
