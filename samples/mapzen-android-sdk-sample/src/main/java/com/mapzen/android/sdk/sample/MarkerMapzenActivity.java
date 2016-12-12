package com.mapzen.android.sdk.sample;

import com.mapzen.android.graphics.MapFragment;
import com.mapzen.android.graphics.MapzenMap;
import com.mapzen.android.graphics.OnMapReadyCallback;
import com.mapzen.android.graphics.model.Marker;
import com.mapzen.tangram.LngLat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Show map markers.
 */
public class MarkerMapzenActivity extends AppCompatActivity {

  private MapzenMap map;

  @Override protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sample_mapzen);

    final MapFragment mapFragment =
        (MapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
    mapFragment.getMapAsync(new OnMapReadyCallback() {
      @Override public void onMapReady(MapzenMap map) {
        MarkerMapzenActivity.this.map = map;
        configureMap();
      }
    });
  }

  private void configureMap() {
    map.addMarker(new Marker(-73.9903, 40.74433));
    map.addMarker(new Marker(-73.984770, 40.734807));
    map.addMarker(new Marker(-73.998674, 40.732172));
    map.addMarker(new Marker(-73.996142, 40.741050));

    map.setZoom(15f);
    map.setPosition(new LngLat(-73.9918, 40.73633));
  }

  @Override protected void onDestroy() {
    map.removeMarker();
    super.onDestroy();
  }
}
