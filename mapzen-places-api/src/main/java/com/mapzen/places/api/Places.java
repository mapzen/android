package com.mapzen.places.api;

import com.mapzen.places.api.internal.GeoDataApiImpl;

/**
 * Main entry point for Mapzen Places API.
 */
public class Places {
  public static final GeoDataApi GeoDataApi = new GeoDataApiImpl();
}
