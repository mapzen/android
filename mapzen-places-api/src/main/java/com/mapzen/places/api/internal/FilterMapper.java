package com.mapzen.places.api.internal;

/**
 * Maps internal filter values to external, {@link com.mapzen.places.api.AutocompleteFilter} values.
 */
public interface FilterMapper {
  /**
   * Given a type filter defined in {@link com.mapzen.places.api.AutocompleteFilter}, return the
   * internal filter value so that autocomplete results can be properly limited.
   * @param autocompleteFilter
   * @return
   */
  String getInternalFilter(int autocompleteFilter);
}
