package com.mapzen.places.api.internal;

import com.mapzen.places.api.Place;

/**
 * Interface for use with the {@link PlacePickerPresenter} and
 * {@link PlacePickerActivity}.
 */
interface PlacePickerViewController {
  /**
   * Show an alert dialog to represent the place selected by the user.
   * @param id
   * @param title
   */
  void showDialog(String id, String title);

  /**
   * Update the visible dialog with more details about the selected place.
   * @param id
   * @param message
   */
  void updateDialog(String id, String message);

  /**
   * When the user has confirmed that they would like to select a place, this method handles
   * constructing an intent to pass back to the activity that started the
   * {@link PlacePickerActivity}.
   */
  void finishWithPlace(Place place);
}
