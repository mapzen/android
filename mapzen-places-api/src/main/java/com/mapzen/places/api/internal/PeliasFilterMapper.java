package com.mapzen.places.api.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mapzen.places.api.AutocompleteFilter.TYPE_FILTER_ADDRESS;
import static com.mapzen.places.api.AutocompleteFilter.TYPE_FILTER_CITIES;
import static com.mapzen.places.api.AutocompleteFilter.TYPE_FILTER_ESTABLISHMENT;
import static com.mapzen.places.api.AutocompleteFilter.TYPE_FILTER_GEOCODE;
import static com.mapzen.places.api.AutocompleteFilter.TYPE_FILTER_NONE;
import static com.mapzen.places.api.AutocompleteFilter.TYPE_FILTER_REGIONS;
import static com.mapzen.places.api.internal.PeliasLayerConsts.PELIAS_LAYER_ADDRESS;
import static com.mapzen.places.api.internal.PeliasLayerConsts.PELIAS_LAYER_BOROUGH;
import static com.mapzen.places.api.internal.PeliasLayerConsts.PELIAS_LAYER_COARSE;
import static com.mapzen.places.api.internal.PeliasLayerConsts.PELIAS_LAYER_COUNTRY;
import static com.mapzen.places.api.internal.PeliasLayerConsts.PELIAS_LAYER_COUNTY;
import static com.mapzen.places.api.internal.PeliasLayerConsts.PELIAS_LAYER_LOCALITY;
import static com.mapzen.places.api.internal.PeliasLayerConsts.PELIAS_LAYER_LOCAL_ADMIN;
import static com.mapzen.places.api.internal.PeliasLayerConsts.PELIAS_LAYER_MACRO_COUNTY;
import static com.mapzen.places.api.internal.PeliasLayerConsts.PELIAS_LAYER_NEIGHBOURHOOD;
import static com.mapzen.places.api.internal.PeliasLayerConsts.PELIAS_LAYER_NONE;
import static com.mapzen.places.api.internal.PeliasLayerConsts.PELIAS_LAYER_VENUE;

/**
 * Maps internal filter values to {@link com.mapzen.places.api.AutocompleteFilter} values for the
 * {@link PlaceAutocompletePresenter}.
 */
public class PeliasFilterMapper implements FilterMapper {

  private static final Map<Integer, String> MAPZEN_PLACES_TO_PELIAS_FILTERS;
  static {
    MAPZEN_PLACES_TO_PELIAS_FILTERS = new HashMap();
    MAPZEN_PLACES_TO_PELIAS_FILTERS.put(TYPE_FILTER_ADDRESS, PELIAS_LAYER_ADDRESS);
    MAPZEN_PLACES_TO_PELIAS_FILTERS.put(TYPE_FILTER_CITIES, PELIAS_LAYER_LOCALITY);
    MAPZEN_PLACES_TO_PELIAS_FILTERS.put(TYPE_FILTER_ESTABLISHMENT, PELIAS_LAYER_VENUE);
    MAPZEN_PLACES_TO_PELIAS_FILTERS.put(TYPE_FILTER_GEOCODE, PELIAS_LAYER_COARSE);
    MAPZEN_PLACES_TO_PELIAS_FILTERS.put(TYPE_FILTER_NONE, PELIAS_LAYER_NONE);
    List<String> regionFilters = new ArrayList<String>() { {
      add(PELIAS_LAYER_COUNTRY);
      add(PELIAS_LAYER_MACRO_COUNTY);
      add(PELIAS_LAYER_LOCALITY);
      add(PELIAS_LAYER_COUNTY);
      add(PELIAS_LAYER_LOCAL_ADMIN);
      add(PELIAS_LAYER_BOROUGH);
      add(PELIAS_LAYER_NEIGHBOURHOOD);
    } };
    String peliasRegionFilters = buildCommaSeparated(regionFilters);
    MAPZEN_PLACES_TO_PELIAS_FILTERS.put(TYPE_FILTER_REGIONS, peliasRegionFilters);
  }

  @Override public String getInternalFilter(int autocompleteFilter) {
    return MAPZEN_PLACES_TO_PELIAS_FILTERS.get(autocompleteFilter);
  }

  private static String buildCommaSeparated(List<String> strings) {
    StringBuilder builder = new StringBuilder(strings.size() * 2 - 1);
    for (int i = 0; i < strings.size() - 2; i++) {
      builder.append(strings.get(i));
      builder.append(",");
    }
    builder.append(strings.get(strings.size() - 1));
    return builder.toString();
  }
}
