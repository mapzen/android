package com.mapzen.places.api.internal;

import com.mapzen.android.graphics.LabelPickListener;
import com.mapzen.android.graphics.MapView;
import com.mapzen.android.graphics.MapzenMap;
import com.mapzen.android.graphics.OnMapReadyCallback;
import com.mapzen.places.api.LatLng;
import com.mapzen.places.api.LatLngBounds;
import com.mapzen.places.api.Place;
import com.mapzen.places.api.R;
import com.mapzen.places.api.ui.PlaceAutocompleteView;
import com.mapzen.places.api.ui.PlacePicker;
import com.mapzen.tangram.LabelPickResult;
import com.mapzen.tangram.LngLat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Activity which displays a map and a dialog when the user selects a place on the map.
 */
public class PlacePickerActivity extends Activity implements
    PlacePickerViewController, OnMapReadyCallback, LabelPickListener,
    DialogInterface.OnClickListener {

  PlacePickerPresenter presenter;
  PlaceAutocompleteView autocompleteView;
  MapView mapView;
  MapzenMap map;
  AlertDialog dialog;
  String dialogPlaceId;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.place_picker_activity);

    autocompleteView = (PlaceAutocompleteView) findViewById(R.id.autocomplete_view);
    autocompleteView.setActivity(this);

    //TODO inject
    presenter = new PlacePickerPresenterImpl(new PeliasPlaceDetailFetcher());
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
    if (i == DialogInterface.BUTTON_POSITIVE) {
      presenter.onPlaceConfirmed();
    }
    dialogPlaceId = null;
  }

  @Override public void finishWithPlace(Place place) {
    if (place instanceof PlaceImpl) {
      PlaceImpl placeImpl = (PlaceImpl) place;
      Intent intent = new Intent();
      intent.putExtra(PlacePicker.EXTRA_PLACE, placeImpl);
      setResult(RESULT_OK, intent);
    }
    finish();
  }

  private void initializeMap() {
    //TODO center on either curr location or given bounds
    map.setMyLocationEnabled(true);
    map.setLabelPickListener(this);

    Bundle extras = getIntent().getExtras();
    if (extras != null) {
      LatLngBounds bounds = (LatLngBounds) extras.get(PlacePicker.EXTRA_BOUNDS);
      if (bounds != null) {
        LatLng center = bounds.getCenter();
        double lng = center.getLongitude();
        double lat = center.getLatitude();
        LngLat boundsCenter = new LngLat(lng, lat);
        map.setPosition(boundsCenter);

        double width = Math.abs(bounds.getNortheast().getLongitude() -
            bounds.getSouthwest().getLongitude());
        double height = Math.abs(bounds.getNortheast().getLatitude() -
            bounds.getSouthwest().getLatitude());

        Point screen = new Point();
        getWindowManager().getDefaultDisplay().getSize(screen);
        LngLat viewMin = map.screenPositionToLngLat(new PointF(0.0f, 0.0f));
        LngLat viewMax = map.screenPositionToLngLat(new PointF(screen.x, screen.y));

        double scaleX = width / Math.abs(viewMax.longitude - viewMin.longitude);
        double scaleY = height / Math.abs(viewMax.latitude - viewMin.latitude);
        double zoomDelta = -Math.log(Math.max(scaleX, scaleY)) / Math.log(2.0);
        float z = map.getZoom() + (float) zoomDelta;
        map.setZoom(z);
      } else {
        mapView.getFindMe().setActivated(true);
      }
    } else {
      mapView.getFindMe().setActivated(true);
    }
  }
}
