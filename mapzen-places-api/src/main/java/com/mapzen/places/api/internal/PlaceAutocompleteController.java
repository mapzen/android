package com.mapzen.places.api.internal;

import com.mapzen.android.lost.api.Status;
import com.mapzen.places.api.AutocompleteFilter;
import com.mapzen.places.api.LatLngBounds;
import com.mapzen.places.api.Place;

/**
 * Interface for place autocomplete wrapper Activity.
 */
interface PlaceAutocompleteController {

  /**
   * Set result code and data to return to calling activity/fragment.
   * @param result Selected{@code Place} object.
   * @param status Selection status.
   */
  void setResult(Place result, String details, Status status);

  /**
   * Finish place autocomplete wrapper activity and return to calling application.
   */
  void finish();

  /**
   * Return the bounds for this controller so that it can be converted to a
   * {@link com.mapzen.pelias.BoundingBox} by the presenter.
   * @return
   */
  LatLngBounds getBounds();

  /**
   * Return the filter that should be used to limit autocomplete results.
   * @return
   */
  AutocompleteFilter getAutocompleteFilter();
}
