package com.mapzen.android.sample;

import com.mapzen.android.graphics.MapFragment;
import com.mapzen.android.graphics.MapzenMap;
import com.mapzen.android.graphics.OnMapReadyCallback;
import com.mapzen.android.graphics.model.MapStyle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Demonstrates how to set a custom stylesheet.
 */
public class CustomStylesheetActivity extends AppCompatActivity
    implements AdapterView.OnItemSelectedListener {

  MapzenMap map;

  MapStyle zincStyle = new MapStyle("styles/zinc/zinc.yaml");
  MapStyle tronStyle = new MapStyle("styles/tron/tron.yaml");

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_spinner);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayShowTitleEnabled(false);

    Spinner spinner = (Spinner) findViewById(R.id.spinner);
    ArrayAdapter<CharSequence> adapter =
        ArrayAdapter.createFromResource(this, R.array.custom_style_array,
            R.layout.simple_spinner_item);
    adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
    spinner.setAdapter(adapter);
    spinner.setOnItemSelectedListener(this);

    final MapFragment mapFragment =
        (MapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);

    mapFragment.getMapAsync(zincStyle, new OnMapReadyCallback() {
      @Override public void onMapReady(MapzenMap map) {
        CustomStylesheetActivity.this.map = map;
      }
    });
  }

  @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    if (map == null) {
      return;
    }
    switch (position) {
      case 0:
        map.setStyle(zincStyle);
        break;
      case 1:
        map.setStyle(tronStyle);
        break;
      default:
        map.setStyle(zincStyle);
        break;
    }
  }

  @Override public void onNothingSelected(AdapterView<?> parent) {

  }
}
