package com.mapzen.places.api.internal;

import com.mapzen.android.graphics.MapView;
import com.mapzen.android.graphics.OnMapReadyCallback;
import com.mapzen.places.api.Place;

import java.util.Map;

/**
 * Interface for managing state associated with the {@link PlacePickerViewController}.
 */
interface PlacePickerPresenter {

  /**
   * Get a reference to {@link com.mapzen.android.graphics.MapzenMap}.
   * @param mapView
   */
  void getMapAsync(MapView mapView, OnMapReadyCallback callback);

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
  void onLabelPicked(Map<String, String> properties);

  /**
   * Called when a user confirms their selection in the alert dialog that is displayed after
   * a POI has been picked.
   */
  void onPlaceConfirmed();

  /**
   * Called when a {@link Place} is selected from the PlaceAutocomplete UI.
   * @param place
   */
  void onAutocompletePlacePicked(Place place, String details);

  /**
   * Invoked when view is hidden (i.e. Activity is sent to background or finished).
   */
  void onHideView();

  /**
   * Invoked when view is shown (i.e. Activity is resumed).
   */
  void onShowView();
}
