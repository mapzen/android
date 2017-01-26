package com.mapzen.places.api.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mapzen.places.api.Place.TYPE_ADMINISTRATIVE_AREA_LEVEL_1;
import static com.mapzen.places.api.Place.TYPE_COUNTRY;
import static com.mapzen.places.api.Place.TYPE_ESTABLISHMENT;
import static com.mapzen.places.api.Place.TYPE_LOCALITY;
import static com.mapzen.places.api.Place.TYPE_NEIGHBORHOOD;
import static com.mapzen.places.api.Place.TYPE_POINT_OF_INTEREST;
import static com.mapzen.places.api.Place.TYPE_POLITICAL;
import static com.mapzen.places.api.Place.TYPE_ROUTE;
import static com.mapzen.places.api.Place.TYPE_STREET_ADDRESS;
import static com.mapzen.places.api.Place.TYPE_SUBLOCALITY;
import static com.mapzen.places.api.Place.TYPE_SUBLOCALITY_LEVEL_1;
import static com.mapzen.places.api.internal.PeliasLayerConsts.PELIAS_LAYER_ADDRESS;
import static com.mapzen.places.api.internal.PeliasLayerConsts.PELIAS_LAYER_BOROUGH;
import static com.mapzen.places.api.internal.PeliasLayerConsts.PELIAS_LAYER_COUNTRY;
import static com.mapzen.places.api.internal.PeliasLayerConsts.PELIAS_LAYER_COUNTY;
import static com.mapzen.places.api.internal.PeliasLayerConsts.PELIAS_LAYER_LOCALITY;
import static com.mapzen.places.api.internal.PeliasLayerConsts.PELIAS_LAYER_LOCAL_ADMIN;
import static com.mapzen.places.api.internal.PeliasLayerConsts.PELIAS_LAYER_MACRO_COUNTY;
import static com.mapzen.places.api.internal.PeliasLayerConsts.PELIAS_LAYER_MACRO_REGION;
import static com.mapzen.places.api.internal.PeliasLayerConsts.PELIAS_LAYER_NEIGHBOURHOOD;
import static com.mapzen.places.api.internal.PeliasLayerConsts.PELIAS_LAYER_REGION;
import static com.mapzen.places.api.internal.PeliasLayerConsts.PELIAS_LAYER_STREET;
import static com.mapzen.places.api.internal.PeliasLayerConsts.PELIAS_LAYER_VENUE;

/**
 * Converts a "layer" returned from Pelias into a list of {@link com.mapzen.places.api.Place} types
 * for {@link PeliasFeatureToPlaceConverter}.
 */
class PeliasLayerToPlaceTypeConverter {

  private static final Map<String, List<Integer>> PELIAS_LAYER_TO_PLACE_TYPE;
  static {
      PELIAS_LAYER_TO_PLACE_TYPE = new HashMap();
      PELIAS_LAYER_TO_PLACE_TYPE.put(PELIAS_LAYER_LOCALITY, listOf(TYPE_LOCALITY, TYPE_POLITICAL));
      PELIAS_LAYER_TO_PLACE_TYPE.put(PELIAS_LAYER_COUNTRY, listOf(TYPE_COUNTRY, TYPE_POLITICAL));
      PELIAS_LAYER_TO_PLACE_TYPE.put(PELIAS_LAYER_VENUE, listOf(TYPE_POINT_OF_INTEREST,
          TYPE_ESTABLISHMENT));
      PELIAS_LAYER_TO_PLACE_TYPE.put(PELIAS_LAYER_ADDRESS, listOf(TYPE_STREET_ADDRESS));
      PELIAS_LAYER_TO_PLACE_TYPE.put(PELIAS_LAYER_MACRO_COUNTY, listOf(TYPE_LOCALITY,
          TYPE_POLITICAL));
      PELIAS_LAYER_TO_PLACE_TYPE.put(PELIAS_LAYER_COUNTY, listOf(TYPE_LOCALITY, TYPE_POLITICAL));
      PELIAS_LAYER_TO_PLACE_TYPE.put(PELIAS_LAYER_LOCAL_ADMIN, listOf(TYPE_LOCALITY,
          TYPE_POLITICAL));
      PELIAS_LAYER_TO_PLACE_TYPE.put(PELIAS_LAYER_BOROUGH, listOf(TYPE_SUBLOCALITY,
          TYPE_SUBLOCALITY_LEVEL_1, TYPE_POLITICAL));
      PELIAS_LAYER_TO_PLACE_TYPE.put(PELIAS_LAYER_NEIGHBOURHOOD, listOf(TYPE_NEIGHBORHOOD,
          TYPE_POLITICAL));
      PELIAS_LAYER_TO_PLACE_TYPE.put(PELIAS_LAYER_STREET, listOf(TYPE_ROUTE));
      PELIAS_LAYER_TO_PLACE_TYPE.put(PELIAS_LAYER_REGION, listOf(TYPE_ADMINISTRATIVE_AREA_LEVEL_1,
          TYPE_POLITICAL));
      PELIAS_LAYER_TO_PLACE_TYPE.put(PELIAS_LAYER_MACRO_REGION, listOf(
          TYPE_ADMINISTRATIVE_AREA_LEVEL_1, TYPE_POLITICAL));
  }

  /**
   * Returns a list of {@link Place} types corresponding to a given Pelias layer.
   * @param layer
   * @return
   */
  List<Integer> getPlaceTypes(String layer) {
    return PELIAS_LAYER_TO_PLACE_TYPE.get(layer);
  }

  private static List<Integer> listOf(int... args) {
    List<Integer> list = new ArrayList<>();
    for (int i = 0; i < args.length; ++i) {
      list.add(args[i]);
    }
    return list;
  }
}
