package com.mapzen.places.api.internal;

import com.mapzen.places.api.Place;

/**
 * Interface for use with the {@link PlacePickerPresenter} and
 * {@link PlacePickerActivity}.
 */
interface PlacePickerViewController {
  /**
   * Show an alert dialog to represent the place selected by the user.
   * @param id the selected {@link Place}'s id.
   * @param title the dialog title.
   */
  void showDialog(String id, String title);

  /**
   * Update the visible dialog with more details about the selected place.
   * @param id the selected {@link Place}'s id.
   * @param message the dialog message.
   */
  void updateDialog(String id, String message);

  /**
   * When the user has confirmed that they would like to select a place, this method handles
   * constructing an intent to pass back to the activity that started the
   * {@link PlacePickerActivity}.
   */
  void finishWithPlace(Place place);

  /**
   * Toggles the connection to the location services layer. Location services should be disabled
   * when the place picker is not visible on the screen.
   *
   * @param enabled {@code true} to enable the location services layer, {@code false} to disable it.
   */
  void setMyLocationEnabled(boolean enabled);
}
