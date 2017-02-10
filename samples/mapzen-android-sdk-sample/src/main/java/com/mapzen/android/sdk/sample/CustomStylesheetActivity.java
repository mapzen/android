package com.mapzen.android.sdk.sample;

import com.mapzen.android.graphics.MapFragment;
import com.mapzen.android.graphics.MapzenMap;
import com.mapzen.android.graphics.OnMapReadyCallback;
import com.mapzen.android.graphics.model.MapStyle;

import android.os.Bundle;

/**
 * Demonstrates how to set a custom stylesheet.
 */
public class CustomStylesheetActivity extends BaseDemoActivity {

  public static final MapStyle TRON_STYLE = new MapStyle("styles/tron/tron.yaml");

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sample_mapzen);

    final MapFragment mapFragment =
        (MapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);

    final Bundle state = savedInstanceState;
    mapFragment.getMapAsync(TRON_STYLE, new OnMapReadyCallback() {
      @Override public void onMapReady(MapzenMap map) {
        if (state == null) {
          map.setStyle(TRON_STYLE);
        }
        map.setPersistMapState(true);
      }
    });
  }
}
