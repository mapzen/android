package com.mapzen.android.sample;

import com.mapzen.android.MapFragment;
import com.mapzen.android.MapzenMap;
import com.mapzen.android.OnMapReadyCallback;
import com.mapzen.android.model.MapStyle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Demonstrates switching the map's style.
 */
public class SwitchStyleActivity extends AppCompatActivity {

    MapFragment mapFragment;
    MapzenMap mapzenMap;

    Button outdoorBtn;
    Button bubbleBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_style);

        outdoorBtn = (Button) findViewById(R.id.btn_outdoor_style);
        outdoorBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                changeMapStyle(MapStyle.OUTDOOR);
            }
        });

        bubbleBtn = (Button) findViewById(R.id.btn_bubble_style);
        bubbleBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                changeMapStyle(MapStyle.BUBBLE_WRAP);
            }
        });

        mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        mapFragment.getMapAsync(MapStyle.OUTDOOR, new OnMapReadyCallback() {
            @Override public void onMapReady(MapzenMap mapzenMap) {
                SwitchStyleActivity.this.mapzenMap = mapzenMap;
            }
        });
    }

    private void changeMapStyle(MapStyle style) {
        mapzenMap.setStyle(style);
    }

}
