package com.mapzen.android.sdk.sample;

import com.mapzen.android.graphics.FeaturePickListener;
import com.mapzen.android.graphics.MapFragment;
import com.mapzen.android.graphics.MapzenMap;
import com.mapzen.android.graphics.OnMapReadyCallback;
import com.mapzen.android.graphics.model.Polygon;
import com.mapzen.tangram.LngLat;

import android.os.Bundle;
import android.widget.Toast;

import java.util.Map;

/**
 * Demonstrates how to use the {@link FeaturePickListener}.
 */
public class FeaturePickListenerActivity extends BaseDemoActivity {

  MapzenMap map;

  FeaturePickListener listener = new FeaturePickListener() {
    @Override
    public void onFeaturePick(Map<String, String> properties, float positionX, float positionY) {
      if (positionX == 0.0 && positionY == 0.0) {
        return;
      }
      String title = "Polygon";
      Toast.makeText(FeaturePickListenerActivity.this, title, Toast.LENGTH_SHORT).show();
    }
  };

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sample_mapzen);

    final MapFragment mapFragment =
        (MapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
    mapFragment.getMapAsync(new OnMapReadyCallback() {
      @Override public void onMapReady(MapzenMap map) {
        FeaturePickListenerActivity.this.map = map;
        map.setZoom(15f);
        map.setPosition(new LngLat(-122.394046, 37.789747));
        map.setFeaturePickListener(listener);
        showSearchResults();
      }
    });
  }

  private void showSearchResults() {
    LngLat result1 = new LngLat(-122.392799, 37.782511);
    LngLat result2 = new LngLat(-122.397477, 37.788243);
    LngLat result3 = new LngLat(-122.393528, 37.789227);
    Polygon polygon = new Polygon.Builder()
        .add(result1)
        .add(result2)
        .add(result3)
        .add(result1)
        .build();
    map.addPolygon(polygon);
  }

}
