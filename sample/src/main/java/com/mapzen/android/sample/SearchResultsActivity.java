package com.mapzen.android.sample;

import com.mapzen.android.graphics.MapFragment;
import com.mapzen.android.graphics.MapzenMap;
import com.mapzen.android.graphics.OnMapReadyCallback;
import com.mapzen.tangram.LngLat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * Demonstrates displaying search results on the map.
 */
public class SearchResultsActivity extends AppCompatActivity {

  MapzenMap map;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_pins);

    Button drawPinsBtn = (Button) findViewById(R.id.draw_pins_btn);
    drawPinsBtn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        showSearchResults();
      }
    });

    Button clearPinsBtn = (Button) findViewById(R.id.clear_pins_btn);
    clearPinsBtn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        map.clearSearchResults();
      }
    });

    final MapFragment mapFragment =
        (MapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
    mapFragment.getMapAsync(new OnMapReadyCallback() {
      @Override public void onMapReady(MapzenMap map) {
        SearchResultsActivity.this.map = map;
        map.setZoom(15f);
        map.setPosition(new LngLat(-122.394046, 37.789747));
        showSearchResults();
      }
    });
  }

  private void showSearchResults() {
    LngLat result1 = new LngLat(-122.392158, 37.791171);
    LngLat result2 = new LngLat(-122.395720, 37.788365);
    map.drawSearchResult(result1);
    map.drawSearchResult(result2, false);

    LngLat result3 = new LngLat(-122.392799, 37.782511);
    LngLat result4 = new LngLat(-122.397477, 37.788243);
    LngLat result5 = new LngLat(-122.393528, 37.789227);
    List<LngLat> results = new ArrayList();
    results.add(result3);
    results.add(result4);
    results.add(result5);
    map.drawSearchResults(results, 1, 2);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    map.clearSearchResults();
  }
}
