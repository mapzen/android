package com.mapzen.places.api.internal;

import com.mapzen.tangram.LngLat;

import java.util.Map;

/**
 * Interface for fetching details about a place.
 */
public interface PlaceDetailFetcher {

  void fetchDetails(LngLat coordinates, Map<String, String> properties,
      OnPlaceDetailsFetchedListener listener);
}
