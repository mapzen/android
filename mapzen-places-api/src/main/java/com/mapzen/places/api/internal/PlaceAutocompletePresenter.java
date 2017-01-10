package com.mapzen.places.api.internal;

import com.mapzen.pelias.gson.Result;

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
    // TODO: Fetch place details and populate full Place object.
    controller.setResult(response.body().getFeatures().get(0).properties.name);
    controller.finish();
  }
}
