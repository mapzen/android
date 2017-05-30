package com.mapzen.places.api.internal;

import com.mapzen.pelias.gson.Feature;
import com.mapzen.pelias.gson.Result;
import com.mapzen.places.api.Place;

import retrofit2.Response;

/**
 * Handles choosing which {@link Feature} should be used to notify listeners of successful
 * {@link Place} retrieval as well as when to notify listeners that a {@link Place} has failed to be
 * retrieved.
 */
class PeliasCallbackHandler {

  private PeliasFeatureConverter converter;

  /**
   * Constructor.
   */
  PeliasCallbackHandler() {
    converter = new PeliasFeatureConverter();
  }

  /**
   * Creates a {@link Place} from a {@link Feature} with a name that matches the given title and
   * notifies the listener that a place has been successfully retrieved. If the response body or
   * response body features do not exist, the listener is notified of a fetch failure.
   *
   * @param title the name of the POI to match.
   * @param response response returned from the Pelias service.
   * @param listener object to be notified of success or failure.
   */
  void handleSuccess(String title, Response<Result> response,
      OnPlaceDetailsFetchedListener listener) {
    if (!isValidResponse(response)) {
      listener.onFetchFailure();
      return;
    }

    for (Feature feature : response.body().getFeatures()) {
      if (feature.properties.name.equals(title)) {
        Place place = converter.getFetchedPlace(feature);
        String details = converter.getDetails(feature, title);
        listener.onFetchSuccess(place, details);
      }
    }
  }

  /**
   * Creates a {@link Place} from the first feature in the response (there should only be one
   * feature in the response) and notifies the listener that a place has been successfully
   * retrieved. If the response body or response body features do not exist, the listener is
   * notified of a fetch failure.
   *
   * @param response response returned from the Pelias service.
   * @param listener object to be notified of success or failure.
   */
  void handleSuccess(Response<Result> response, OnPlaceDetailsFetchedListener listener) {
    if (!isValidResponse(response)) {
      listener.onFetchFailure();
      return;
    }

    Feature feature = response.body().getFeatures().get(0);
    Place place = converter.getFetchedPlace(feature);
    String details = converter.getDetails(feature);
    listener.onFetchSuccess(place, details);
  }

  /**
   * Verifies whether the response body returned by the Pelias service is valid (contains features).
   *
   * @param response Pelias service response.
   * @return {@code true} if the response is valid; {@code false} otherwise.
   */
  private boolean isValidResponse(Response<Result> response) {
    return response.body() != null &&
        response.body().getFeatures() != null &&
        !response.body().getFeatures().isEmpty();
  }

  /**
   * Notify the listener of a fetch failure.
   * @param listener
   */
  void handleFailure(OnPlaceDetailsFetchedListener listener) {
    listener.onFetchFailure();
  }
}
