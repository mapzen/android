package com.mapzen.android.sdk.sample;

import com.mapzen.android.graphics.MapFragment;
import com.mapzen.android.graphics.MapzenMap;
import com.mapzen.android.graphics.OnMapReadyCallback;
import com.mapzen.android.graphics.model.BubbleWrapStyle;
import com.mapzen.android.graphics.model.CinnabarStyle;
import com.mapzen.android.graphics.model.RefillStyle;
import com.mapzen.android.graphics.model.ThemedMapStyle;
import com.mapzen.android.graphics.model.WalkaboutStyle;
import com.mapzen.android.graphics.model.ZincStyle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Simple activity to display spinner of all available styles. Super class for
 * {@link CustomMarkerActivity} and {@link OverlayActivity}.
 */
public abstract class SimpleStyleSwitcherActivity extends BaseDemoActivity {

  MapzenMap mapzenMap;

  /**
   * Layout must have a {@link MapFragment} with id "fragment" and {@link Spinner} with id
   * "spinner".
   * @return
   */
  abstract int getLayoutId();

  /**
   * Called after {@link MapFragment#getMapAsync(OnMapReadyCallback)} completes.
   */
  abstract void configureMap();

  private AdapterView.OnItemSelectedListener styleSelectedListener =
      new AdapterView.OnItemSelectedListener() {
        @Override public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
          handleStyleSelected(i);
        }

        @Override public void onNothingSelected(AdapterView<?> adapterView) {

        }
      };

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayoutId());

    Spinner spinner = findViewById(R.id.spinner);
    ArrayAdapter<CharSequence> adapter =
        ArrayAdapter.createFromResource(this, R.array.style_array, R.layout.simple_spinner_item);
    adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
    spinner.setAdapter(adapter);
    spinner.setOnItemSelectedListener(styleSelectedListener);

    MapFragment fragment = (MapFragment) getSupportFragmentManager().findFragmentById(
        R.id.fragment);
    fragment.getMapAsync(new OnMapReadyCallback() {
      @Override public void onMapReady(MapzenMap mapzenMap) {
        SimpleStyleSwitcherActivity.this.mapzenMap = mapzenMap;
        configureMap();
      }
    });
  }

  private void handleStyleSelected(int position) {
    ThemedMapStyle style;
    switch (position) {
      case 0:
        style = new BubbleWrapStyle();
        break;
      case 1:
        style = new CinnabarStyle();
        break;
      case 2:
        style = new RefillStyle();
        break;
      case 3:
        style = new WalkaboutStyle();
        break;
      case 4:
        style = new ZincStyle();
        break;
      default:
        style = new BubbleWrapStyle();
        break;
    }
    if (mapzenMap != null) {
      mapzenMap.setStyleAsync(style, null);
    }
  }
}
