package com.mapzen.places.api.internal;

import com.mapzen.pelias.gson.Result;

import retrofit2.Response;

class PlaceAutocompletePresenter {
  private final PlaceAutocompleteController controller;

  PlaceAutocompletePresenter(PlaceAutocompleteController controller) {
    this.controller = controller;
  }

  void onResponse(Response<Result> response) {
    // TODO: Fetch place details and populate full Place object.
    controller.setResult(response.body().getFeatures().get(0).properties.name);
    controller.finish();
  }
}
