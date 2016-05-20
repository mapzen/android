package com.mapzen.android.sample;

import com.mapzen.android.MapFragment;
import com.mapzen.android.MapzenMap;
import com.mapzen.android.OnMapReadyCallback;
import com.mapzen.android.model.Polygon;
import com.mapzen.tangram.LngLat;
import com.mapzen.tangram.MapData;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Polygon SDK demo.
 */
public class PolygonMapzenActivity extends AppCompatActivity {

    private MapzenMap map;
    private MapData polygonData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_mapzen);

        final MapFragment mapFragment =
                (MapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override public void onMapReady(MapzenMap map) {
                PolygonMapzenActivity.this.map = map;
                configureMap();
            }
        });
    }

    private void configureMap() {
        Polygon polygon = new Polygon.Builder()
                .add(new LngLat(-73.9903, 40.74433))
                .add(new LngLat(-73.984770, 40.734807))
                .add(new LngLat(-73.998674, 40.732172))
                .add(new LngLat(-73.996142, 40.741050))
                .build();
        polygonData = map.addPolygon(polygon);

        map.setZoom(15f);
        map.setPosition(new LngLat(-73.9918, 40.73633));
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        cleanupMap();
    }

    private void cleanupMap() {
        polygonData.clear();
    }
}
