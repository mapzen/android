package com.mapzen.android.sample;

import com.mapzen.android.MapFragment;
import com.mapzen.android.MapzenMap;
import com.mapzen.android.OnMapReadyCallback;
import com.mapzen.android.model.Marker;
import com.mapzen.tangram.LngLat;
import com.mapzen.tangram.MapData;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Show map markers.
 */
public class MarkerMapzenActivity extends AppCompatActivity {

    private MapzenMap map;
    private MapData markers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_mapzen);

        final MapFragment mapFragment =
                (MapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override public void onMapReady(MapzenMap map) {
                MarkerMapzenActivity.this.map = map;
                configureMap();
            }
        });
    }

    private void configureMap() {
        markers = map.addMarker(new Marker(-73.9903, 40.74433));
        map.addMarker(new Marker(-73.984770, 40.734807));
        map.addMarker(new Marker(-73.998674, 40.732172));
        map.addMarker(new Marker(-73.996142, 40.741050));

        map.setZoom(15f);
        map.setPosition(new LngLat(-73.9918, 40.73633));
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        cleanupMap();
    }

    private void cleanupMap() {
        markers.remove();
    }
}
