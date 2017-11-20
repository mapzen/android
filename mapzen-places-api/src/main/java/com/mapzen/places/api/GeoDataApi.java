package com.mapzen.places.api;

import com.mapzen.android.lost.api.LostApiClient;
import com.mapzen.android.lost.api.PendingResult;

import android.support.annotation.NonNull;

/**
 * Main entry point for the Mapzen Places Geo Data API.
 */
public interface GeoDataApi {
  /**
   * Returns an object which can be used to retrieve autocomplete results.
   * @param client
   * @param query
   * @param bounds
   * @param filter
   * @return
   */
  @NonNull PendingResult<AutocompletePredictionBuffer> getAutocompletePredictions(
      @NonNull LostApiClient client, @NonNull String query, @NonNull LatLngBounds bounds,
      @NonNull AutocompleteFilter filter);
}
