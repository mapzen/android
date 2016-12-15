package com.mapzen.places.api.internal;

/**
 * Listener that is invoked by {@link PlaceDetailFetcher} when details for a place are retrieved.
 */
public interface OnPlaceDetailsFetchedListener {
  void onPlaceDetailsFetched(String details);
}
