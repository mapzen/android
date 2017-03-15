package com.mapzen.places.api.internal;

import com.mapzen.places.api.Place;

import android.util.Log;

import java.util.Map;

/**
 * Implementation of {@link PlacePickerPresenter}.
 */
class PlacePickerPresenterImpl implements PlacePickerPresenter {
  private static final String TAG = "PlacePicker";

  private static final String PROPERTY_ID = "id";
  private static final String PROPERTY_NAME = "name";

  private PlacePickerViewController controller;
  private PlaceDetailFetcher detailFetcher;

  /**
   * Construct a new object.
   */
  PlacePickerPresenterImpl(PlaceDetailFetcher fetcher) {
    detailFetcher = fetcher;
  }

  @Override public void setController(PlacePickerViewController controller) {
    this.controller = controller;
  }

  @Override public void onLabelPicked(Map<String, String> properties) {
    final String id = properties.get(PROPERTY_ID);
    final String title = properties.get(PROPERTY_NAME);

    detailFetcher.fetchDetails(properties, new OnPlaceDetailsFetchedListener() {
      @Override public void onFetchSuccess(Place place, String details) {
        PlaceStore.instance().setCurrentSelectedPlace(place);
        controller.updateDialog(id, details);
      }

      @Override public void onFetchFailure() {
        Log.e(TAG, "Unable to fetch details for place: " + id);
      }
    });

    controller.showDialog(id, title);
  }

  @Override public void onPlaceConfirmed() {
    controller.finishWithPlace(PlaceStore.instance().getCurrentSelectedPlace());
  }

  @Override public void onAutocompletePlacePicked(Place place, String details) {
    PlaceStore.instance().setCurrentSelectedPlace(place);
    controller.showDialog(place.getId(), details);
    //TODO:dialog change location should bring back autocomplete ui
    //TODO:hide map ui and just have dialog visible
  }

  @Override public void onHideView() {
    controller.setMyLocationEnabled(false);
  }

  @Override public void onShowView() {
    controller.setMyLocationEnabled(true);
  }
}
