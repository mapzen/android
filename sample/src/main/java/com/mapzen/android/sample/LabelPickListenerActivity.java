package com.mapzen.android.sample;

import com.mapzen.android.graphics.LabelPickListener;
import com.mapzen.android.graphics.MapFragment;
import com.mapzen.android.graphics.MapzenMap;
import com.mapzen.android.graphics.OnMapReadyCallback;
import com.mapzen.android.graphics.model.Marker;
import com.mapzen.tangram.LabelPickResult;
import com.mapzen.tangram.LngLat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.Map;

/**
 * Demonstrates how to use {@link com.mapzen.android.graphics.LabelPickListener}.
 */
public class LabelPickListenerActivity extends AppCompatActivity {

  MapzenMap map;

  LabelPickListener labelPickListener = new LabelPickListener() {
    @Override public void onLabelPicked(LabelPickResult result, float positionX, float positionY) {
      if (result == null) {
        return;
      }
      double longitude = result.getCoordinates().longitude;
      double latitude = result.getCoordinates().latitude;
      map.removeMarker();
      map.addMarker(new Marker(longitude, latitude));

      Map<String, String> properties = result.getProperties();
      String name = properties.get("name");
      if (!name.isEmpty()) {
        Toast.makeText(LabelPickListenerActivity.this, name, Toast.LENGTH_SHORT).show();
      }
    }
  };

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sample_mapzen);

    final MapFragment mapFragment =
        (MapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
    mapFragment.getMapAsync(new OnMapReadyCallback() {
      @Override public void onMapReady(MapzenMap map) {
        LabelPickListenerActivity.this.map = map;
        map.setZoom(15f);
        map.setPosition(new LngLat(-122.394046, 37.789747));
        map.setLabelPickListener(labelPickListener);
      }
    });
  }
}
