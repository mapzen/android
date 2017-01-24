package com.mapzen.android.sdk.sample;

import com.mapzen.android.graphics.MapFragment;
import com.mapzen.android.graphics.MapzenMap;
import com.mapzen.android.graphics.OnMapReadyCallback;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

  private static final String KEY_LOCATION_ENABLED = "enabled";
  private static final String TAG = BasicMapzenActivity.class.getSimpleName();

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

    final boolean enabled = (savedInstanceState == null ||
        savedInstanceState.getBoolean(KEY_LOCATION_ENABLED));

    final long millis = System.currentTimeMillis();

    final MapFragment mapFragment =
        (MapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
    mapFragment.getMapAsync(new OnMapReadyCallback() {
      @Override public void onMapReady(MapzenMap map) {
        long now = System.currentTimeMillis();
        long elapsedTime = now - millis;
        Log.d(TAG, "onMapReady [" + elapsedTime + "] millis");
        BasicMapzenActivity.this.map = map;
        configureMap(enabled);
      }
    });
  }

  private void configureMap(boolean enabled) {
    if (enabled) {
      setMyLocationEnabled(true);
    }
    map.setFindMeOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Toast.makeText(BasicMapzenActivity.this, R.string.custom_listener, Toast.LENGTH_SHORT)
            .show();
      }
    });

    map.setCompassButtonEnabled(true);
    map.setZoomButtonsEnabled(true);
  }

  @Override protected void onPause() {
    super.onPause();
    if (map != null && map.isMyLocationEnabled()) {
      map.setMyLocationEnabled(false);
    }
  }

  @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    if (map == null) {
      return;
    }

    switch (position) {
      case 0:
        setMyLocationEnabled(true);
        break;
      case 1:
        setMyLocationEnabled(false);
        break;
      default:
        setMyLocationEnabled(true);
        break;
    }
  }

  @Override public void onNothingSelected(AdapterView<?> parent) {

  }

  private void setMyLocationEnabled(boolean enabled) {
    map.setMyLocationEnabled(enabled);
    enableLocationOnResume = enabled;
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    outState.putBoolean(KEY_LOCATION_ENABLED, enableLocationOnResume);
  }
}
