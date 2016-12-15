package com.mapzen.places.api.internal.ui;

import com.mapzen.android.graphics.LabelPickListener;
import com.mapzen.android.graphics.MapView;
import com.mapzen.android.graphics.MapzenMap;
import com.mapzen.android.graphics.OnMapReadyCallback;
import com.mapzen.places.api.R;
import com.mapzen.places.api.internal.PlacePickerPresenter;
import com.mapzen.places.api.internal.PlacePickerPresenterImpl;
import com.mapzen.places.api.internal.PlacePickerViewController;
import com.mapzen.tangram.LabelPickResult;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Activity which displays a map and a dialog when the user selects a place on the map.
 */
public class PlacePickerActivity extends Activity implements
    PlacePickerViewController, OnMapReadyCallback, LabelPickListener,
    DialogInterface.OnClickListener {

  PlacePickerPresenter presenter;
  MapView mapView;
  MapzenMap map;
  AlertDialog dialog;
  String dialogPlaceId;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.mz_fragment_map);

    //TODO inject
    presenter = new PlacePickerPresenterImpl();
    presenter.setController(this);

    mapView = (MapView) findViewById(R.id.mz_map_view);
    mapView.getMapAsync(this);
  }

  @Override public void onMapReady(MapzenMap mapzenMap) {
    map = mapzenMap;
    initializeMap();
  }

  @Override public void onLabelPicked(LabelPickResult result, float positionX, float positionY) {
    if (result == null) {
      return;
    }
    presenter.onLabelPicked(result.getCoordinates(), result.getProperties());
  }

  @Override public void showDialog(String id, String title) {
    dialog = new AlertDialog.Builder(this)
        .setTitle(R.string.use_this_place)
        .setMessage(title)
        .setNegativeButton(R.string.change_location, this)
        .setPositiveButton(R.string.select, this)
        .create();
    dialog.show();
    dialogPlaceId = id;
  }

  @Override public void updateDialog(String id, String detail) {
    if (dialogPlaceId != null && dialogPlaceId.equals(id)) {
      dialog.setMessage(detail);
    }
  }

  @Override public void onClick(DialogInterface dialogInterface, int i) {
    if (i == R.string.select) {
      presenter.onPlaceConfirmed();
    }
    dialogPlaceId = null;
  }

  @Override public void finishWithPlace() {
    setResult(RESULT_OK);
    finish();
  }

  private void initializeMap() {
    //TODO center on either curr location or given bounds
    map.setMyLocationEnabled(true);
    map.setLabelPickListener(this);
  }

}
