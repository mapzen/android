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

import java.util.ArrayList;
import java.util.List;

/**
 * Demonstrates drawing a routing line on the map.
 */
public class RouteLineActivity extends AppCompatActivity {

  MapzenMap map;

  List<LngLat> points = new ArrayList();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_clear_btn);

    Button clearPinBtn = (Button) findViewById(R.id.clear_btn);
    clearPinBtn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        map.clearRouteLine();
        map.clearRouteLocationMarker();
        points.clear();
      }
    });

    final MapFragment mapFragment =
        (MapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
    mapFragment.getMapAsync(new OnMapReadyCallback() {
      @Override public void onMapReady(MapzenMap map) {
        RouteLineActivity.this.map = map;
        map.setZoom(15f);
        map.setPosition(new LngLat(-122.394046, 37.789747));
        map.setTapResponder(new TouchInput.TapResponder() {
          @Override public boolean onSingleTapUp(float x, float y) {
            addLineSegmentToRoute(x, y);
            return false;
          }

          @Override public boolean onSingleTapConfirmed(float x, float y) {
            return false;
          }
        });
      }
    });

    Toast.makeText(this, R.string.route_line_instruction, Toast.LENGTH_LONG).show();
  }

  private void addLineSegmentToRoute(float x, float y) {
    LngLat point = map.coordinatesAtScreenPosition(x, y);
    points.add(point);
    map.drawRouteLine(points);

    map.drawRouteLocationMarker(points.get(0));
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    map.clearRouteLine();
    map.clearRouteLocationMarker();
  }
}
