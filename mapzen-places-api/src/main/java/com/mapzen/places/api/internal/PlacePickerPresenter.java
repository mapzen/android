package com.mapzen.places.api.internal;

import com.mapzen.tangram.LngLat;

import java.util.Map;

/**
 * Interface for managing state associated with the {@link PlacePickerViewController}.
 */
public interface PlacePickerPresenter {
  /**
   * Sets the presenter's controller. Must be called before any other methods in the interface
   * can be called.
   * @param controller
   */
  void setController(PlacePickerViewController controller);

  /**
   * Called when a POI on the map has been selected by the user.
   * @param properties
   */
  void onLabelPicked(LngLat coordinates, Map<String, String> properties);

  /**
   * Called when a user confirms their selection in the alert dialog that is displayed after
   * a POI has been picked.
   */
  void onPlaceConfirmed();
}
