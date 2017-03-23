package com.mapzen.android.sdk.sample;

import com.mapzen.android.graphics.MapFragment;
import com.mapzen.android.graphics.MapzenMap;
import com.mapzen.android.graphics.MarkerPickListener;
import com.mapzen.android.graphics.OnMapReadyCallback;
import com.mapzen.android.graphics.model.BitmapMarker;
import com.mapzen.android.graphics.model.MarkerOptions;
import com.mapzen.tangram.LngLat;

import android.os.Bundle;
import android.widget.Toast;

/**
 * Custom marker demo.
 */
public class CustomMarkerActivity extends BaseDemoActivity implements MarkerPickListener {

  private MapzenMap map;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_custom_marker);
    final MapFragment mapFragment =
        (MapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
    mapFragment.getMapAsync(new OnMapReadyCallback() {
      @Override public void onMapReady(MapzenMap map) {
        CustomMarkerActivity.this.map = map;
        configureMap();
      }
    });
  }

  private void configureMap() {
    map.setMyLocationEnabled(true);
    map.setCompassButtonEnabled(true);
    map.setZoomButtonsEnabled(true);
    map.setPersistMapState(true);

    final MarkerOptions markerOptions = new MarkerOptions()
        .position(new LngLat(-73.985428, 40.748817))
        .icon(R.drawable.mapzen);

    map.addBitmapMarker(markerOptions);
    map.setPosition(new LngLat(-73.985428, 40.748817));
    map.setZoom(16);
    map.setMarkerPickListener(this);
  }

  @Override public void onMarkerPick(BitmapMarker marker) {
    Toast.makeText(this, marker.toString(), Toast.LENGTH_SHORT).show();
  }
}
