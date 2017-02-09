package com.mapzen.places.api.internal;

import java.util.Map;

/**
 * Interface for fetching details about a place.
 */
interface PlaceDetailFetcher {

  String PROP_ID = "id";
  String PROP_AREA = "area";

  String PELIAS_GID_BASE = "openstreetmap:venue:";
  String PELIAS_GID_NODE = "node:";
  String PELIAS_GID_WAY = "way:";

  /**
   * Called when details for a place selected from the map should be retrieved.
   *
   * @param properties map properties for the given feature.
   * @param listener callback to be notified of success or failure.
   */
  void fetchDetails(Map<String, String> properties, OnPlaceDetailsFetchedListener listener);

  /**
   * Called when details for a place selected from autocomplete should be retrieved.
   *
   * @param gid global ID for the given feature
   * @param listener callback to be notified of success or failure.
   */
  void fetchDetails(String gid, OnPlaceDetailsFetchedListener listener);
}
