package com.mapzen.android.sample;

import com.mapzen.android.MapFragment;
import com.mapzen.android.MapzenMap;
import com.mapzen.android.OnMapReadyCallback;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Basic SDK demo, tracks user's current location on map.
 */
public class BasicMapzenActivity extends AppCompatActivity {

    private MapzenMap map;

    /**
     *  To conserve resources, {@link MapzenMap#setMyLocationEnabled} is set to false when
     *  the activity is paused and re-enabled when the activity resumes.
     */
    private boolean enableLocationOnResume = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_mapzen);

        final MapFragment mapFragment =
                (MapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override public void onMapReady(MapzenMap map) {
                BasicMapzenActivity.this.map = map;
                configureMap();
            }
        });
    }

    private void configureMap() {
        map.setMyLocationEnabled(true);
    }

    @Override protected void onPause() {
        super.onPause();
        if (map.isMyLocationEnabled()) {
            map.setMyLocationEnabled(false);
            enableLocationOnResume = true;
        }
    }

    @Override protected void onResume() {
        super.onResume();
        if (enableLocationOnResume) {
            map.setMyLocationEnabled(true);
        }
    }
}
