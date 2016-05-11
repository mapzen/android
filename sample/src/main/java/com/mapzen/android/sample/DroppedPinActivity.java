package com.mapzen.android.sample;

import com.mapzen.android.MapFragment;
import com.mapzen.android.MapzenMap;
import com.mapzen.android.OnMapReadyCallback;
import com.mapzen.tangram.LngLat;
import com.mapzen.tangram.TouchInput;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Demonstrates droppoing a pin on the map.
 */
public class DroppedPinActivity extends AppCompatActivity {

    MapzenMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear_btn);

        Button clearPinBtn = (Button) findViewById(R.id.clear_btn);
        clearPinBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                map.clearDroppedPin();
            }
        });

        final MapFragment mapFragment =
                (MapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override public void onMapReady(MapzenMap map) {
                DroppedPinActivity.this.map = map;
                map.setZoom(15f);
                map.setPosition(new LngLat(-122.394046, 37.789747));
                map.setLongPressResponder(new TouchInput.LongPressResponder() {
                    @Override public void onLongPress(float x, float y) {
                        drawDroppedPin(x, y);
                    }
                });
            }
        });

        Toast.makeText(this, R.string.drop_pin_instruction, Toast.LENGTH_LONG).show();
    }

    private void drawDroppedPin(float x, float y) {
        LngLat point = map.coordinatesAtScreenPosition(x, y);
        map.drawDroppedPin(point);
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        map.clearDroppedPin();
    }
}
