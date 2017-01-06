package com.mapzen.places.api.internal;

/**
 * Listener that is invoked by {@link PlaceDetailFetcher} when details for a place are retrieved.
 */
interface OnPlaceDetailsFetchedListener {
  /**
   * Called when details for a place have been fetched.
   * @param details
   */
  void onPlaceDetailsFetched(String details);
}
