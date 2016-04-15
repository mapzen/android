package com.mapzen.android.sample;

import com.mapzen.android.MapFragment;
import com.mapzen.android.OverlayManager;
import com.mapzen.android.model.LatLng;
import com.mapzen.android.model.Polygon;
import com.mapzen.tangram.MapController;
import com.mapzen.tangram.MapData;
import com.mapzen.tangram.MapView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Polygon SDK demo.
 */
public class PolygonMapzenActivity extends AppCompatActivity {

    MapFragment mapFragment;
    MapController mapController;
    OverlayManager overlayManager;
    MapData polygonData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_mapzen);

        mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        mapFragment.getMapAsync(new MapView.OnMapReadyCallback() {
            @Override public void onMapReady(MapController mapController) {
                PolygonMapzenActivity.this.mapController = mapController;
                configureMap();
            }
        });
    }

    private void configureMap() {
        overlayManager = mapFragment.getMapManager();
        Polygon polygon = new Polygon.Builder()
                .add(new LatLng(40.74433, -73.9903))
                .add(new LatLng(40.734807, -73.984770))
                .add(new LatLng(40.732172, -73.998674))
                .add(new LatLng(40.741050, -73.996142))
                .build();
        polygonData = overlayManager.addPolygon(polygon);

        //mapController.setMapZoom(17f);
        overlayManager.setMyLocationEnabled(true);
    }


    @Override protected void onDestroy() {
        super.onDestroy();
        cleanupMap();
    }

    private void cleanupMap() {
        polygonData.remove();
    }
}
