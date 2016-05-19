package com.mapzen.android.sample;

import com.mapzen.android.MapFragment;
import com.mapzen.android.MapzenMap;
import com.mapzen.android.OnMapReadyCallback;
import com.mapzen.android.model.ViewCompleteListener;
import com.mapzen.tangram.LngLat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Demonstrates queueing an event to run animations on the map.
 */
public class QueueEventActivity extends AppCompatActivity {

    private MapzenMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_mapzen);

        final MapFragment mapFragment =
                (MapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override public void onMapReady(MapzenMap map) {
                QueueEventActivity.this.map = map;
                configureMap();
            }
        });
    }

    private void configureMap() {
        map.setViewCompleteListener(new ViewCompleteListener() {
            @Override public void onViewComplete() {
                Toast.makeText(QueueEventActivity.this, R.string.view_complete,
                        Toast.LENGTH_SHORT).show();
            }
        });
        map.queueEvent(new Runnable() {
            @Override public void run() {
                map.setZoom(17, 600);
                map.setPosition(new LngLat(-73.9918, 40.73633), 600);
            }
        });
    }
}
