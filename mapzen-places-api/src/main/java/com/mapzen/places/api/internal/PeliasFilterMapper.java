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

/**
 * Maps internal filter values to {@link com.mapzen.places.api.AutocompleteFilter} values for the
 * {@link PlaceAutocompletePresenter}.
 */
public class PeliasFilterMapper implements FilterMapper {

  private static final String PELIAS_FILTER_ADDRESS = "address";
  private static final String PELIAS_FILTER_LOCALITY = "locality";
  private static final String PELIAS_FILTER_VENUE = "venue";
  private static final String PELIAS_FILTER_COARSE = "coarse";
  private static final String PELIAS_FILTER_NONE = "";
  private static final String PELIAS_FILTER_COUNTRY = "country";
  private static final String PELIAS_FILTER_MACRO_COUNTY = "macrocounty";
  private static final String PELIAS_FILTER_COUNTY = "county";
  private static final String PELIAS_FILTER_LOCAL_ADMIN = "localadmin";
  private static final String PELIAS_FILTER_BOROUGH = "borough";
  private static final String PELIAS_FILTER_NEIGHBOURHOOD = "neighbourhood";

  private static final Map<Integer, String> MAPZEN_PLACES_TO_PELIAS_FILTERS;
  static {
    MAPZEN_PLACES_TO_PELIAS_FILTERS = new HashMap();
    MAPZEN_PLACES_TO_PELIAS_FILTERS.put(TYPE_FILTER_ADDRESS, PELIAS_FILTER_ADDRESS);
    MAPZEN_PLACES_TO_PELIAS_FILTERS.put(TYPE_FILTER_CITIES, PELIAS_FILTER_LOCALITY);
    MAPZEN_PLACES_TO_PELIAS_FILTERS.put(TYPE_FILTER_ESTABLISHMENT, PELIAS_FILTER_VENUE);
    MAPZEN_PLACES_TO_PELIAS_FILTERS.put(TYPE_FILTER_GEOCODE, PELIAS_FILTER_COARSE);
    MAPZEN_PLACES_TO_PELIAS_FILTERS.put(TYPE_FILTER_NONE, PELIAS_FILTER_NONE);
    List<String> regionFilters = new ArrayList<String>() { {
      add(PELIAS_FILTER_COUNTRY);
      add(PELIAS_FILTER_MACRO_COUNTY);
      add(PELIAS_FILTER_LOCALITY);
      add(PELIAS_FILTER_COUNTY);
      add(PELIAS_FILTER_LOCAL_ADMIN);
      add(PELIAS_FILTER_BOROUGH);
      add(PELIAS_FILTER_NEIGHBOURHOOD);
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
