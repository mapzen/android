package com.mapzen.places.api.internal;

import com.mapzen.android.lost.api.Status;
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
}
