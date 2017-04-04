package com.mapzen.android.sdk.sample;

import com.mapzen.android.graphics.MapFragment;
import com.mapzen.android.graphics.MapzenMap;
import com.mapzen.android.graphics.MarkerPickListener;
import com.mapzen.android.graphics.OnMapReadyCallback;
import com.mapzen.android.graphics.model.BitmapMarker;
import com.mapzen.android.graphics.model.MarkerOptions;
import com.mapzen.tangram.LngLat;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * Custom marker demo.
 */
public class CustomMarkerActivity extends BaseDemoActivity implements MarkerPickListener {

  private MapzenMap map;
  private BitmapMarker bitmapMarker;

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
    map.setCompassButtonEnabled(true);
    map.setPersistMapState(true);
    map.setPosition(new LngLat(-73.985428, 40.748817));
    map.setZoom(16);
    map.setMarkerPickListener(this);

    findViewById(R.id.add_marker_btn).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        final MarkerOptions markerOptions = new MarkerOptions()
            .position(new LngLat(-73.985428, 40.748817))
            .icon(R.drawable.mapzen);
        bitmapMarker = map.addBitmapMarker(markerOptions);
      }
    });

    findViewById(R.id.remove_marker_btn).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (bitmapMarker != null) {
          bitmapMarker.remove();
        }
      }
    });
  }

  @Override public void onMarkerPick(BitmapMarker marker) {
    Toast.makeText(this, marker.toString(), Toast.LENGTH_SHORT).show();
  }
}
