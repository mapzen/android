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

    private MapFragment mapFragment;
    private MapzenMap mapzenMap;

    private Button bubbleBtn;
    private Button cinnabarBtn;
    private Button refillBtn;
    private Button outdoorBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_style);

        bubbleBtn = (Button) findViewById(R.id.btn_bubble);
        bubbleBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                changeMapStyle(MapStyle.BUBBLE_WRAP);
            }
        });

        cinnabarBtn = (Button) findViewById(R.id.btn_cinnabar);
        cinnabarBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                changeMapStyle(MapStyle.CINNABAR);
            }
        });

        refillBtn = (Button) findViewById(R.id.btn_refill);
        refillBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                changeMapStyle(MapStyle.REFILL);
            }
        });

        outdoorBtn = (Button) findViewById(R.id.btn_outdoor);
        outdoorBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                changeMapStyle(MapStyle.OUTDOOR);
            }
        });

        mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        mapFragment.getMapAsync(MapStyle.BUBBLE_WRAP, new OnMapReadyCallback() {
            @Override public void onMapReady(MapzenMap mapzenMap) {
                SwitchStyleActivity.this.mapzenMap = mapzenMap;
            }
        });
    }

    private void changeMapStyle(MapStyle style) {
        mapzenMap.setStyle(style);
    }
}
