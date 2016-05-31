package com.mapzen.android.sample;

import com.mapzen.android.MapFragment;
import com.mapzen.android.MapzenMap;
import com.mapzen.android.OnMapReadyCallback;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Basic SDK demo, tracks user's current location on map.
 */
public class BasicMapzenActivity extends AppCompatActivity
    implements AdapterView.OnItemSelectedListener {

  private MapzenMap map;

  /**
   * To conserve resources, {@link MapzenMap#setMyLocationEnabled} is set to false when
   * the activity is paused and re-enabled when the activity resumes.
   */
  private boolean enableLocationOnResume = false;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_spinner);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayShowTitleEnabled(false);

    Spinner spinner = (Spinner) findViewById(R.id.spinner);
    ArrayAdapter<CharSequence> adapter =
        ArrayAdapter.createFromResource(this, R.array.enabled_disabled_array,
            R.layout.simple_spinner_item);
    adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
    spinner.setAdapter(adapter);
    spinner.setOnItemSelectedListener(this);

    final MapFragment mapFragment =
        (MapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
    mapFragment.getMapAsync(new OnMapReadyCallback() {
      @Override public void onMapReady(MapzenMap map) {
        BasicMapzenActivity.this.map = map;
        configureMap();
      }
    });
  }

  private void configureMap() {
    map.setMyLocationEnabled(true);
    map.setFindMeOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Toast.makeText(BasicMapzenActivity.this, R.string.custom_listener, Toast.LENGTH_SHORT)
            .show();
      }
    });
  }

  @Override protected void onPause() {
    super.onPause();
    if (map.isMyLocationEnabled()) {
      map.setMyLocationEnabled(false);
      enableLocationOnResume = true;
    }
  }

  @Override protected void onResume() {
    super.onResume();
    if (enableLocationOnResume) {
      map.setMyLocationEnabled(true);
    }
  }

  @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    if (map == null) {
      return;
    }

    switch (position) {
      case 0:
        map.setMyLocationEnabled(true);
        break;
      case 1:
        map.setMyLocationEnabled(false);
        break;
      default:
        map.setMyLocationEnabled(true);
        break;
    }
  }

  @Override public void onNothingSelected(AdapterView<?> parent) {

  }
}
