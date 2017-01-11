package com.mapzen.places.api.internal;

import com.mapzen.android.lost.api.Status;
import com.mapzen.pelias.gson.Properties;
import com.mapzen.pelias.gson.Result;
import com.mapzen.places.api.Place;

import retrofit2.Response;

/**
 * Place autocomplete presenter to handle non-Android logic.
 */
class PlaceAutocompletePresenter {
  private final PlaceAutocompleteController controller;

  /**
   * Creates a new instance with reference to controller.
   * @param controller place autocomplete wrapper Activity.
   */
  PlaceAutocompletePresenter(PlaceAutocompleteController controller) {
    this.controller = controller;
  }

  /**
   * Invoked when the {@code PeliasSearchView} returns a successful response.
   * @param response parsed result returned by the service.
   */
  void onResponse(Response<Result> response) {
    // TODO: Fetch place details.
    Properties properties = response.body().getFeatures().get(0).properties;
    String name = properties.name;
    String address = properties.label;
    Place place = new PlaceImpl.Builder()
        .setName(name)
        .setAddress(address)
        .build();
    Status status = new Status(Status.SUCCESS);
    controller.setResult(place, status);
    controller.finish();
  }
}
