package com.mapzen.android.sdk.sample;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Example activity for toggling transit, bike, and path overlays.
 */
public class OverlayActivity extends SwitchStyleActivity {

  private AdapterView.OnItemSelectedListener itemSelectedListener =
      new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
      switch (position) {
        case 0:
          // do nothing
          break;
        case 1:
          enableTransitOverlay();
          break;
        case 2:
          enableBikeOverlay();
          break;
        case 3:
          enablePathOverlay();
          break;
        case 4:
          disableOverlays();
        default:
          break;
      }
    }

    @Override public void onNothingSelected(AdapterView<?> parent) {

    }
  };

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Spinner spinner = (Spinner) findViewById(R.id.overlay_spinner);
    ArrayAdapter<CharSequence> adapter =
        ArrayAdapter.createFromResource(this, R.array.overlay_array, R.layout.simple_spinner_item);
    adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
    spinner.setAdapter(adapter);
    spinner.setOnItemSelectedListener(itemSelectedListener);
  }

  @Override void configureMap() {
    super.configureMap();
    mapzenMap.setMyLocationEnabled(true);
  }

  @Override protected void onDestroy() {
    mapzenMap.setMyLocationEnabled(false);
    super.onDestroy();
  }

  int getLayoutId() {
    return R.layout.activity_overlay;
  }

  /**
   * Enables transit overlay on the map.
   */
  void enableTransitOverlay() {
    if (mapzenMap == null) {
      return;
    }
    mapzenMap.setTransitOverlayEnabled(true);
  }

  /**
   * Enables bike overlay on the map.
   */
  void enableBikeOverlay() {
    if (mapzenMap == null) {
      return;
    }
    mapzenMap.setBikeOverlayEnabled(true);
  }

  /**
   * Enables path overlay on the map.
   */
  void enablePathOverlay() {
    if (mapzenMap == null) {
      return;
    }
    mapzenMap.setPathOverlayEnabled(true);
  }

  /**
   * Disables transit overlay on the map.
   */
  void disableOverlays() {
    if (mapzenMap == null) {
      return;
    }
    mapzenMap.setTransitOverlayEnabled(false);
    mapzenMap.setBikeOverlayEnabled(false);
    mapzenMap.setPathOverlayEnabled(false);
  }
}
