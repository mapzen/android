package com.mapzen.android.sample;

import com.mapzen.android.MapFragment;
import com.mapzen.android.OverlayManager;
import com.mapzen.android.model.Polyline;
import com.mapzen.tangram.LngLat;
import com.mapzen.tangram.MapController;
import com.mapzen.tangram.MapData;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Polyline SDK demo.
 */
public class PolylineMapzenActivity extends AppCompatActivity {

    MapFragment mapFragment;
    MapController mapController;
    OverlayManager overlayManager;
    MapData polylineData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_mapzen);

        mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        mapFragment.getMapAsync(new com.mapzen.android.MapView.OnMapReadyCallback() {
            @Override public void onMapReady(MapController mapController) {
                PolylineMapzenActivity.this.mapController = mapController;
                configureMap();
            }
        });
    }

    private void configureMap() {
        overlayManager = mapFragment.getOverlayManager();
        Polyline polyline = new Polyline.Builder()
                .add(new LngLat(-73.9903, 40.74433))
                .add(new LngLat(-73.984770, 40.734807))
                .add(new LngLat(-73.998674, 40.732172))
                .add(new LngLat(-73.996142, 40.741050))
                .build();
        polylineData = overlayManager.addPolyline(polyline);

        mapController.setZoom(15f);
        mapController.setPosition(new LngLat(-73.9918, 40.73633));
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        cleanupMap();
    }

    private void cleanupMap() {
        polylineData.remove();
    }
}
