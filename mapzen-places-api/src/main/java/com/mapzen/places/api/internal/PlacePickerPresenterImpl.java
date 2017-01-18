package com.mapzen.places.api.internal;

import com.mapzen.places.api.Place;
import com.mapzen.tangram.LngLat;

import java.util.Map;

/**
 * Implementation of {@link PlacePickerPresenter}.
 */
class PlacePickerPresenterImpl implements PlacePickerPresenter {

  public static final String PROPERTY_ID = "id";
  public static final String PROPERTY_NAME = "name";

  PlacePickerViewController controller;
  PlaceDetailFetcher detailFetcher;
  Place place;

  /**
   * Construct a new object.
   */
  public PlacePickerPresenterImpl(PlaceDetailFetcher fetcher) {
    detailFetcher = fetcher;
  }

  @Override public void setController(PlacePickerViewController controller) {
    this.controller = controller;
  }

  @Override public void onLabelPicked(LngLat coordinates, Map<String, String> properties) {
    final String id = properties.get(PROPERTY_ID);
    final String title = properties.get(PROPERTY_NAME);

    detailFetcher.fetchDetails(coordinates, properties,
        new OnPlaceDetailsFetchedListener() {
          @Override public void onFetchSuccess(Place place, String details) {
            PlacePickerPresenterImpl.this.place = place;
            controller.updateDialog(id, details);
          }

          @Override public void onFetchFailure() {

          }
        }
    );

    controller.showDialog(id, title);
  }

  @Override public void onPlaceConfirmed() {
    controller.finishWithPlace(place);
  }

  @Override public void onAutocompletePlacePicked(Place place, String details) {
    this.place = place;
    controller.showDialog(place.getId(), details);
    //TODO:dialog change location should bring back autocomplete ui
    //TODO:hide map ui and just have dialog visible
  }
}
