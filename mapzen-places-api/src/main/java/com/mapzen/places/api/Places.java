package com.mapzen.places.api;

import com.mapzen.places.api.internal.GeoDataApiImpl;

import android.support.annotation.NonNull;

/**
 * Main entry point for Mapzen Places API.
 */
public class Places {
  @NonNull public static final GeoDataApi GeoDataApi = new GeoDataApiImpl();
}
