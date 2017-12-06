package com.mapzen.places.api.ui;

import com.mapzen.android.lost.api.Status;
import com.mapzen.places.api.Place;

import android.support.annotation.NonNull;

/**
 * Interface for receiving information about a {@link Place} after it is selected from a
 * {@link PlaceAutocompleteFragment}.
 */
public interface PlaceSelectionListener {

  /**
   * Called when the user selects a {@link Place}.
   * @param place
   */
  void onPlaceSelected(@NonNull Place place);

  /**
   * Called when an error occurs while trying to select a {@link Place}.
   * @param status
   */
  void onError(@NonNull Status status);
}
