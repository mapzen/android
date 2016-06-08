package com.mapzen.android.model;

import com.mapzen.android.MapzenRouter;
import com.mapzen.valhalla.Router;

import java.util.HashMap;

/**
 * Converts distance units between {@link MapzenRouter.DistanceUnits} and
 * {@link Router.DistanceUnits}.
 */
public class Converter {

  public static final HashMap<MapzenRouter.DistanceUnits,
      Router.DistanceUnits> UNITS_TO_ROUTER_UNITS = new HashMap<>();
  static {
    UNITS_TO_ROUTER_UNITS.put(MapzenRouter.DistanceUnits.MILES, Router.DistanceUnits.MILES);
    UNITS_TO_ROUTER_UNITS.put(MapzenRouter.DistanceUnits.KILOMETERS,
        Router.DistanceUnits.KILOMETERS);
  }
}
