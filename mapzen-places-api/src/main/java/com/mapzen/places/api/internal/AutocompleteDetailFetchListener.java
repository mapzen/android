package com.mapzen.places.api.internal;

import com.mapzen.android.lost.api.Status;
import com.mapzen.places.api.Place;

/**
 * Handles notifying the {@link PlaceAutocompleteController} after a place's details have either
 * been successfully retrieved or failed to be retrieved.
 */
class AutocompleteDetailFetchListener implements OnPlaceDetailsFetchedListener {

  final PlaceAutocompleteController controller;

  /**
   * Construct new {@link AutocompleteDetailFetchListener} and set its controller.
   * @param controller
   */
  AutocompleteDetailFetchListener(PlaceAutocompleteController controller) {
    this.controller = controller;
  }

  @Override public void onFetchSuccess(Place place, String details) {
    Status status = new Status(Status.SUCCESS);
    setResultAndFinish(place, details, status);
  }

  @Override public void onFetchFailure() {
    Status status = new Status(Status.INTERNAL_ERROR);
    setResultAndFinish(null, null, status);
  }

  private void setResultAndFinish(Place place, String details, Status status) {
    controller.setResult(place, details, status);
    controller.finish();
  }
}
