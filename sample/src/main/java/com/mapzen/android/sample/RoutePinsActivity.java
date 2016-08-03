package com.mapzen.android.sample;

import com.mapzen.android.graphics.MapFragment;
import com.mapzen.android.graphics.MapzenMap;
import com.mapzen.android.graphics.OnMapReadyCallback;
import com.mapzen.tangram.LngLat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Demonstrates drawing start and end routing pins on the map.
 */
public class RoutePinsActivity extends AppCompatActivity {

  MapzenMap map;
  static boolean routePinsDrawn = true;

  @Override protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_pins);

    Button drawPinsBtn = (Button) findViewById(R.id.draw_pins_btn);
    drawPinsBtn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        routePinsDrawn = true;
        drawRoutePins();
      }
    });

    Button clearPinsBtn = (Button) findViewById(R.id.clear_pins_btn);
    clearPinsBtn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        routePinsDrawn = false;
        map.clearRoutePins();
      }
    });

    final MapFragment mapFragment =
        (MapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
    mapFragment.getMapAsync(new OnMapReadyCallback() {
      @Override public void onMapReady(MapzenMap map) {
        RoutePinsActivity.this.map = map;
        map.setZoom(15f);
        map.setPosition(new LngLat(-122.394046, 37.789747));
        if (savedInstanceState == null || routePinsDrawn) {
          drawRoutePins();
        }
      }
    });
  }

  private void drawRoutePins() {
    LngLat start = new LngLat(-122.392158, 37.791171);
    LngLat end = new LngLat(-122.395720, 37.788365);
    map.drawRoutePins(start, end);
  }
}
