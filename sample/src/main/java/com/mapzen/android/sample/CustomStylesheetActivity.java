package com.mapzen.android.sample;

import com.mapzen.android.MapFragment;
import com.mapzen.android.MapzenMap;
import com.mapzen.android.OnMapReadyCallback;
import com.mapzen.android.model.MapStyle;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

/**
 * Demonstrates how to set a custom stylesheet.
 */
public class CustomStylesheetActivity extends AppCompatActivity {

    MapzenMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_mapzen);

        final MapFragment mapFragment =
                (MapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        MapStyle zincStyle = new MapStyle("styles/zinc/zinc.yaml");
        mapFragment.getMapAsync(zincStyle, new OnMapReadyCallback() {
            @Override public void onMapReady(MapzenMap map) {
                CustomStylesheetActivity.this.map = map;
                changeStylesheet();
            }
        });
    }

    private void changeStylesheet() {
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                MapStyle tronStyle = new MapStyle("styles/tron/tron.yaml");
                map.setStyle(tronStyle);
            }
        }, 5000);
    }
}
