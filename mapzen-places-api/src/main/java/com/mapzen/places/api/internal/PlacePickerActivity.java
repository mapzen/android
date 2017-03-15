package com.mapzen.places.api.internal;

import com.mapzen.android.graphics.LabelPickListener;
import com.mapzen.android.graphics.MapView;
import com.mapzen.android.graphics.MapzenMap;
import com.mapzen.android.graphics.OnMapReadyCallback;
import com.mapzen.android.search.MapzenSearch;
import com.mapzen.places.api.LatLng;
import com.mapzen.places.api.LatLngBounds;
import com.mapzen.places.api.Place;
import com.mapzen.places.api.R;
import com.mapzen.places.api.ui.PlaceAutocomplete;
import com.mapzen.tangram.LabelPickResult;
import com.mapzen.tangram.LngLat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Bundle;

import static com.mapzen.places.api.internal.PlaceIntentConsts.EXTRA_BOUNDS;
import static com.mapzen.places.api.internal.PlaceIntentConsts.EXTRA_DETAILS;
import static com.mapzen.places.api.internal.PlaceIntentConsts.EXTRA_PLACE;

/**
 * Activity which displays a map and a dialog when the user selects a place on the map.
 */
public class PlacePickerActivity extends Activity implements
    PlacePickerViewController, OnMapReadyCallback, LabelPickListener,
    PlaceDialogFragment.PlaceDialogListener {

  PlacePickerPresenter presenter;
  PlaceAutocompleteView autocompleteView;
  MapView mapView;
  MapzenMap map;
  PlaceDialogFragment dialog;
  String dialogPlaceId;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.place_picker_activity);

    autocompleteView = (PlaceAutocompleteView) findViewById(R.id.autocomplete_view);
    autocompleteView.setActivity(this);

    //TODO inject
    MapzenSearch mapzenSearch = new MapzenSearch(this);
    PeliasCallbackHandler callbackHandler = new PeliasCallbackHandler();
    presenter = new PlacePickerPresenterImpl(new PeliasPlaceDetailFetcher(mapzenSearch.getPelias(),
        callbackHandler));
    presenter.setController(this);

    mapView = (MapView) findViewById(R.id.mz_map_view);
    mapView.getMapAsync(this);

    final PlaceDialogFragment fragment =
        (PlaceDialogFragment) getFragmentManager().findFragmentByTag(PlaceDialogFragment.TAG);
    if (fragment != null) {
      fragment.setListener(this);
    }
  }

  @Override protected void onPause() {
    super.onPause();
    presenter.onHideView();
  }

  @Override protected void onResume() {
    super.onResume();
    presenter.onShowView();
  }

  @Override public void setMyLocationEnabled(boolean enabled) {
    if (map != null) {
      map.setMyLocationEnabled(enabled);
    }
  }

  @Override public void onMapReady(MapzenMap mapzenMap) {
    map = mapzenMap;
    initializeMap();
  }

  @Override public void onLabelPicked(LabelPickResult result, float positionX, float positionY) {
    if (result == null) {
      return;
    }
    presenter.onLabelPicked(result.getProperties());
  }

  @Override public void showDialog(String id, String title) {
    dialog = PlaceDialogFragment.newInstance(title);
    dialog.show(getFragmentManager(), PlaceDialogFragment.TAG);
    dialogPlaceId = id;
    dialog.setListener(this);
  }

  @Override public void updateDialog(String id, String detail) {
    if (dialogPlaceId != null && dialogPlaceId.equals(id)) {
      dialog.setMessage(detail);
    }
  }

  @Override public void onPlaceConfirmed() {
    presenter.onPlaceConfirmed();
    dialogPlaceId = null;
  }

  @Override public void onPlaceDismissed() {
    dialogPlaceId = null;
  }

  @Override public void finishWithPlace(Place place) {
    if (place instanceof PlaceImpl) {
      PlaceImpl placeImpl = (PlaceImpl) place;
      Intent intent = new Intent();
      intent.putExtra(EXTRA_PLACE, placeImpl);
      setResult(RESULT_OK, intent);
    }
    finish();
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode == RESULT_OK) {
      Place place = PlaceAutocomplete.getPlace(this, data);
      String details = data.getExtras().getString(EXTRA_DETAILS);
      presenter.onAutocompletePlacePicked(place, details);
    }
  }

  private void initializeMap() {
    map.setMyLocationEnabled(true);
    map.setLabelPickListener(this);

    Bundle extras = getIntent().getExtras();
    if (extras != null) {
      LatLngBounds bounds = (LatLngBounds) extras.get(EXTRA_BOUNDS);
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
