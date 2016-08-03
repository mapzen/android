package com.mapzen.android.sample;

import com.mapzen.android.graphics.MapFragment;
import com.mapzen.android.graphics.MapzenMap;
import com.mapzen.android.graphics.OnMapReadyCallback;
import com.mapzen.android.graphics.model.BubbleWrapStyle;
import com.mapzen.android.graphics.model.CinnabarStyle;
import com.mapzen.android.graphics.model.MapStyle;
import com.mapzen.android.graphics.model.RefillStyle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Demonstrates switching the map's style.
 */
public class SwitchStyleActivity extends AppCompatActivity
    implements AdapterView.OnItemSelectedListener {

  private MapFragment mapFragment;
  private MapzenMap mapzenMap;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_spinner);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayShowTitleEnabled(false);

    Spinner spinner = (Spinner) findViewById(R.id.spinner);
    ArrayAdapter<CharSequence> adapter =
        ArrayAdapter.createFromResource(this, R.array.style_array, R.layout.simple_spinner_item);
    adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
    spinner.setAdapter(adapter);
    spinner.setOnItemSelectedListener(this);

    if (savedInstanceState == null) {
      MapzenMap.setPersistMapState(false);
    }
    mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
    mapFragment.getMapAsync(new BubbleWrapStyle(), new OnMapReadyCallback() {
      @Override public void onMapReady(MapzenMap mapzenMap) {
        SwitchStyleActivity.this.mapzenMap = mapzenMap;
      }
    });
  }

  private void changeMapStyle(MapStyle style) {
    if (mapzenMap != null) {
      mapzenMap.setStyle(style);
    }
  }

  @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    switch (position) {
      case 0:
        changeMapStyle(new BubbleWrapStyle());
        break;
      case 1:
        changeMapStyle(new CinnabarStyle());
        break;
      case 2:
        changeMapStyle(new RefillStyle());
        break;
      default:
        changeMapStyle(new BubbleWrapStyle());
        break;
    }
  }

  @Override public void onNothingSelected(AdapterView<?> parent) {
    // Do nothing.
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    MapzenMap.setPersistMapState(true);
  }
}
