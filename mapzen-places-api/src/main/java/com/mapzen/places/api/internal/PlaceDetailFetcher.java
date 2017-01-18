package com.mapzen.places.api.internal;

import com.mapzen.tangram.LngLat;

import java.util.Map;

/**
 * Interface for fetching details about a place.
 */
interface PlaceDetailFetcher {

  /**
   * Called when details for a place selected from the map should be retrieved. Currently, Pelias is
   * used as the underlying data source but this will soon be migrated to WOF.
   * @param coordinates
   * @param properties
   * @param listener
   */
  void fetchDetails(LngLat coordinates, Map<String, String> properties,
      OnPlaceDetailsFetchedListener listener);

  /**
   * Called when details for a place selected from autocomplete should be retrieved. Currently,
   * Pelias is used as the underlying data source but this will soon be migrated to WOF.
   * @param gid
   * @param listener
   */
  void fetchDetails(String gid, OnPlaceDetailsFetchedListener listener);
}
