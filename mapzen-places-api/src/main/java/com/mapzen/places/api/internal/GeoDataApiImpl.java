package com.mapzen.places.api.internal;

import com.mapzen.android.lost.api.LostApiClient;
import com.mapzen.android.lost.api.PendingResult;
import com.mapzen.pelias.Pelias;
import com.mapzen.places.api.AutocompleteFilter;
import com.mapzen.places.api.AutocompletePredictionBuffer;
import com.mapzen.places.api.GeoDataApi;
import com.mapzen.places.api.LatLngBounds;

/**
 * {@link GeoDataApi} implementation for {@link com.mapzen.places.api.Places}.
 */
public class GeoDataApiImpl implements GeoDataApi {

  private Pelias pelias = new Pelias();

  @Override public PendingResult<AutocompletePredictionBuffer> getAutocompletePredictions(
      LostApiClient client, String query, LatLngBounds bounds, AutocompleteFilter filter) {
    return new AutocompletePendingResult(pelias, query, bounds, filter);
  }
}
