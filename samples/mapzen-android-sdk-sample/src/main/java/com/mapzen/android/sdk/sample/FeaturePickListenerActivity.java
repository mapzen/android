package com.mapzen.android.sdk.sample;

import com.mapzen.android.graphics.FeaturePickListener;
import com.mapzen.android.graphics.MapFragment;
import com.mapzen.android.graphics.MapzenMap;
import com.mapzen.android.graphics.OnMapReadyCallback;
import com.mapzen.tangram.LngLat;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Demonstrates how to use the {@link FeaturePickListener}.
 */
public class FeaturePickListenerActivity extends BaseDemoActivity {

  private static final String PROP_SEARCH_INDEX = "searchIndex";
  private static final String PROP_STATE = "state";
  private static final String PROP_NAME = "name";

  MapzenMap map;

  FeaturePickListener listener = new FeaturePickListener() {
    @Override
    public void onFeaturePick(Map<String, String> properties, float positionX, float positionY) {
      String title;
      if (properties.containsKey(PROP_SEARCH_INDEX)) {
        title =
            getString(R.string.feature) + " " + properties.get(PROP_SEARCH_INDEX) + " " + properties
                .get(PROP_STATE);
      } else {
        title = properties.get(PROP_NAME);
      }
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
    LngLat result3 = new LngLat(-122.392799, 37.782511);
    LngLat result4 = new LngLat(-122.397477, 37.788243);
    LngLat result5 = new LngLat(-122.393528, 37.789227);
    List<LngLat> results = new ArrayList();
    results.add(result3);
    results.add(result4);
    results.add(result5);
    map.drawSearchResults(results, 1);
  }

}
