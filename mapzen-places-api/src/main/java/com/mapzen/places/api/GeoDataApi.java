package com.mapzen.places.api;

import com.mapzen.android.lost.api.LostApiClient;
import com.mapzen.android.lost.api.PendingResult;

/**
 * Main entry point for the Mapzen Places Geo Data API.
 */
public interface GeoDataApi {
  PendingResult<AutocompletePredictionBuffer> getAutocompletePredictions(LostApiClient client,
      String query, LatLngBounds bounds, AutocompleteFilter filter);
}
