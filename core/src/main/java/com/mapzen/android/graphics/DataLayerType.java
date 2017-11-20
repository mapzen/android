package com.mapzen.android.graphics;

import android.support.annotation.NonNull;

/**
 * Default data layers for bundled Mapzen stylesheets including Bubble Wrap, Cinnabar, and Refill.
 * Used to add client data layers to the map and optionally persist them on configuration change.
 */
public enum DataLayerType {
  CURRENT_LOCATION("mz_current_location"),
  POLYLINE("mz_default_line"),
  POLYGON("mz_default_polygon"),
  MARKER("mz_default_point"),
  ROUTE_START_PIN("mz_route_start"),
  ROUTE_END_PIN("mz_route_destination"),
  DROPPED_PIN("mz_dropped_pin"),
  SEARCH_RESULT_PIN("mz_search_result"),
  ROUTE_PIN("mz_route_location"),
  ROUTE_LINE("mz_route_line"),
  TRANSIT_ROUTE_LINE("mz_route_line_transit"),
  TRANSIT_ROUTE_LINE_STATION_ICON("mz_route_transit_stop");

  private final String name;

  /**
   * Constructs enum with name.
   * @param s
   */
  DataLayerType(String s) {
    name = s;
  }

  /**
   * Returns the enum's name.
   * @return
   */
  @NonNull public String toString() {
    return name;
  }
}

