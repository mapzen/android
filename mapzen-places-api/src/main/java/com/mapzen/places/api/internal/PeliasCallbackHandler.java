package com.mapzen.places.api.internal;

import com.mapzen.pelias.gson.Feature;
import com.mapzen.pelias.gson.Result;
import com.mapzen.places.api.Place;

import java.util.List;

import retrofit2.Response;

/**
 * Handles choosing which {@link Feature} should be used to notify listeners of successful
 * {@link Place} retrieval as well as when to notify listeners that a {@link Place} has failed to be
 * retrieved.
 */
class PeliasCallbackHandler {

  private PeliasFeatureToPlaceConverter converter;

  /**
   * Constructor.
   */
  PeliasCallbackHandler() {
    converter = new PeliasFeatureToPlaceConverter();
  }

  /**
   * Creates a {@link Place} from a {@link Feature} with a name that matches the given title and
   * notifies the listener that a place has been successfully retrieved.
   * @param title
   * @param features
   * @param listener
   */
  public void handleSuccess(String title, List<Feature> features,
      OnPlaceDetailsFetchedListener listener) {
    for (Feature feature : features) {
      if (feature.properties.name.equals(title)) {
        Place place = converter.getFetchedPlace(feature);
        String details = getDetails(feature, title);
        listener.onFetchSuccess(place, details);
      }
    }
  }

  /**
   * Creates a {@link Place} from the first feature in the response (there should only be one
   * feature in the response) and notifies the listener that a place has been successfully
   * retrieved. If the response body or response body features do not exist, the listener is
   * notified of a fetch failure.
   * @param response
   * @param listener
   */
  public void handleSuccess(Response<Result> response, OnPlaceDetailsFetchedListener listener) {
    if (response.body() == null || response.body().getFeatures() == null ||
        response.body().getFeatures().isEmpty()) {
      listener.onFetchFailure();
      return;
    }
    Feature feature = response.body().getFeatures().get(0);
    String title = feature.properties.name;
    Place place = converter.getFetchedPlace(feature);
    String details = getDetails(feature, title);
    listener.onFetchSuccess(place, details);
  }

  /**
   * Notify the listener of a fetch failure.
   * @param listener
   */
  public void handleFailure(OnPlaceDetailsFetchedListener listener) {
    listener.onFetchFailure();
  }

  private String getDetails(Feature feature, String title) {
    String label = feature.properties.label;
    label = label.replace(title + ",", "").trim();
    return title + "\n" + label;
  }
}
