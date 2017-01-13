package com.mapzen.places.api.internal;

import com.mapzen.places.api.Place;

/**
 * Listener that is invoked by {@link PlaceDetailFetcher} when details for a place are retrieved.
 */
interface OnPlaceDetailsFetchedListener {
  /**
   * Called when details for a place have been fetched.
   * @param details
   */
  void onFetchSuccess(Place place, String details);

  /**
   * Called when details for a place fail to be fetched.
   */
  void onFetchFailure();
}
