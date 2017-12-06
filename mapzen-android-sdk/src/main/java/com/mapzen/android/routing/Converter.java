package com.mapzen.android.routing;

import com.mapzen.valhalla.Router;

import android.support.annotation.NonNull;

import java.util.HashMap;

/**
 * Converts distance units between {@link MapzenRouter.DistanceUnits} and
 * {@link Router.DistanceUnits}.
 */
public class Converter {

  @NonNull public static final HashMap<MapzenRouter.DistanceUnits,
      Router.DistanceUnits> UNITS_TO_ROUTER_UNITS = new HashMap<>();
  static {
    UNITS_TO_ROUTER_UNITS.put(MapzenRouter.DistanceUnits.MILES, Router.DistanceUnits.MILES);
    UNITS_TO_ROUTER_UNITS.put(MapzenRouter.DistanceUnits.KILOMETERS,
        Router.DistanceUnits.KILOMETERS);
  }
}
