package com.mapzen.android.sample;

import com.mapzen.android.MapFragment;
import com.mapzen.android.MapManager;
import com.mapzen.android.model.Marker;
import com.mapzen.tangram.MapController;
import com.mapzen.tangram.MapData;
import com.mapzen.tangram.MapView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Show map markers
 */
public class MarkerMapzenActivity extends AppCompatActivity {
    MapFragment mapFragment;
    MapController mapController;
    MapManager mapManager;
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
        mapManager = mapFragment.getMapManager();
        marker = mapManager.addMarker(new Marker(40.74433, -73.9903));
        mapManager.addMarker(new Marker(40.734807, -73.984770));
        mapManager.addMarker(new Marker(40.732172, -73.998674));
        mapManager.addMarker(new Marker(40.741050, -73.996142));
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        cleanupMap();
    }

    private void cleanupMap() {
        marker.remove();
    }
}
