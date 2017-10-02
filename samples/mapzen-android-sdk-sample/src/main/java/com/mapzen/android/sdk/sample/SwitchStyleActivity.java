package com.mapzen.android.sdk.sample;

import com.mapzen.android.graphics.MapFragment;
import com.mapzen.android.graphics.MapzenMap;
import com.mapzen.android.graphics.OnMapReadyCallback;
import com.mapzen.android.graphics.OnStyleLoadedListener;
import com.mapzen.android.graphics.model.BubbleWrapStyle;
import com.mapzen.android.graphics.model.CinnabarStyle;
import com.mapzen.android.graphics.model.RefillStyle;
import com.mapzen.android.graphics.model.ThemeColor;
import com.mapzen.android.graphics.model.ThemedMapStyle;
import com.mapzen.android.graphics.model.WalkaboutStyle;
import com.mapzen.android.graphics.model.ZincStyle;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

/**
 * Demonstrates switching the map's style.
 */
public class SwitchStyleActivity extends BaseDemoActivity {

  MapzenMap mapzenMap;
  private ThemedMapStyle currentStyle;

  private MapFragment mapFragment;
  private Spinner styleSpinner;
  private Spinner lodSpinner;
  private Spinner labelLevelSpinner;
  private Spinner colorSpinner;
  private Button updateBtn;

  AdapterView.OnItemSelectedListener styleSelectedListener =
      new AdapterView.OnItemSelectedListener() {
        @Override public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
          handleStyleSelected(i);
          updateLODSpinner();
          updateLabelLevelSpinner();
          updateColorSpinner();
        }

        @Override public void onNothingSelected(AdapterView<?> adapterView) {

        }
  };


  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayoutId());

    styleSpinner = findViewById(R.id.styleSpinner);
    ArrayAdapter<CharSequence> adapter =
        ArrayAdapter.createFromResource(this, R.array.style_array, R.layout.gray_spinner_item);
    adapter.setDropDownViewResource(R.layout.gray_spinner_dropdown_item);
    styleSpinner.setAdapter(adapter);
    styleSpinner.setOnItemSelectedListener(styleSelectedListener);
    styleSpinner.setSelection(0);

    lodSpinner = findViewById(R.id.lodSpinner);

    labelLevelSpinner = findViewById(R.id.labelLevelSpinner);

    colorSpinner = findViewById(R.id.colorSpinner);

    updateBtn = findViewById(R.id.updateMapBtn);
    updateBtn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        updateMap();
      }
    });

    mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
    mapFragment.getMapAsync(new OnMapReadyCallback() {
      @Override public void onMapReady(MapzenMap mapzenMap) {
        SwitchStyleActivity.this.mapzenMap = mapzenMap;
        configureMap();
      }
    });
  }

  @Override protected void onDestroy() {
    styleSpinner.setOnItemSelectedListener(null);
    updateBtn.setOnClickListener(null);
    super.onDestroy();
  }

  int getLayoutId() {
    return R.layout.activity_switch_style;
  }

  /**
   * Configure the map after it has loaded the style. Override in subclass.
   */
  void configureMap() {
    mapzenMap.setPersistMapState(true);
    mapzenMap.setMyLocationEnabled(true);
  }

  private void updateMap() {
    if (mapzenMap != null) {
      int labelLevel = labelLevelSpinner.getSelectedItem() != null ?
          (int) labelLevelSpinner.getSelectedItem() : currentStyle.getDefaultLabelLevel();
      int lod = lodSpinner.getSelectedItem() != null ?
          (int) lodSpinner.getSelectedItem() : currentStyle.getDefaultLod();
      ThemeColor color = colorSpinner.getSelectedItem() != null ?
          (ThemeColor) colorSpinner.getSelectedItem() : currentStyle.getDefaultColor();
      updateBtn.setEnabled(false);
      mapzenMap.setStyleLabelLevelLodThemeColorAsync(currentStyle, labelLevel, lod, color,
          new OnStyleLoadedListener() {
            @Override public void onStyleLoaded() {
              updateBtn.setEnabled(true);
            }
          });
    }
  }

  private void handleStyleSelected(int position) {
    ThemedMapStyle style = null;
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
    currentStyle = style;
  }

  private void updateLODSpinner() {
    ArrayAdapter<Integer> adapter =
        new ArrayAdapter(this, R.layout.gray_spinner_item);
    adapter.setDropDownViewResource(R.layout.gray_spinner_dropdown_item);
    if (currentStyle != null) {
      for (int i = 0; i < currentStyle.getLodCount(); i++) {
        adapter.add(i);
      }
    }
    lodSpinner.setAdapter(adapter);
  }

  private void updateLabelLevelSpinner() {
    ArrayAdapter<Integer> adapter =
        new ArrayAdapter(this, R.layout.gray_spinner_item);
    adapter.setDropDownViewResource(R.layout.gray_spinner_dropdown_item);
    if (currentStyle != null) {
      for (int i = 0; i < currentStyle.getLabelCount(); i++) {
        adapter.add(i);
      }
    }
    labelLevelSpinner.setAdapter(adapter);
  }

  private void updateColorSpinner() {
    ArrayAdapter<ThemeColor> adapter =
        new ArrayAdapter(this, R.layout.gray_spinner_item);
    adapter.setDropDownViewResource(R.layout.gray_spinner_dropdown_item);
    if (currentStyle != null && currentStyle.getColors() != null) {
      for (ThemeColor color : currentStyle.getColors()) {
        adapter.add(color);
      }
    }
    colorSpinner.setAdapter(adapter);
  }
}
