package com.mapzen.android.routing;

import com.mapzen.helpers.DistanceFormatter;
import com.mapzen.valhalla.Router;

/**
 * Formatter for dealing with {@link MapzenRouter.DistanceUnits}.
 */
public class MapzenDistanceFormatter {

  /**
   * Format distance for display using specified distance units.
   *
   * @param distanceInMeters the actual distance in meters.
   * @param realTime boolean flag for navigation vs. list view.
   * @param units miles or kilometers.
   * @return distance string formatted according to the rules of the formatter.
   */
  public static String format(int distanceInMeters, boolean realTime,
      MapzenRouter.DistanceUnits units) {
    Router.DistanceUnits routerUnits = Converter.UNITS_TO_ROUTER_UNITS.get(units);
    return DistanceFormatter.format(distanceInMeters, realTime, routerUnits);
  }

}
