package com.mapzen.places.api.internal;

interface PlaceAutocompleteController {

  /**
   * Set result code and data to return to calling activity/fragment.
   * @param result Name of venue selected. To be replaced by fully populated {@code Place} object.
   */
  void setResult(String result);

  /**
   * Finish place autocomplete wrapper activity and return to calling application.
   */
  void finish();

}
