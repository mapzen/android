package com.mapzen.places.api.internal;

import com.mapzen.places.api.Place;
import com.mapzen.tangram.LngLat;

import java.util.Map;

/**
 * Interface for fetching details about a place.
 */
public interface PlaceDetailFetcher {

  /**
   * Called when details for a place should be retrieved. Currently, Pelias is used as the
   * underlying data source but this will soon be migrated to WOF.
   * @param coordinates
   * @param properties
   * @param listener
   */
  void fetchDetails(LngLat coordinates, Map<String, String> properties,
      OnPlaceDetailsFetchedListener listener);

  /**
   * Return a {@link Place} for the fetched details.
   * @return
   */
  Place getFetchedPlace();
}
