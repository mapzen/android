package com.mapzen.places.api.ui;

import com.mapzen.places.api.LatLngBounds;
import com.mapzen.places.api.Place;
import com.mapzen.places.api.internal.ui.PlacePickerActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Entry point for interaction with the PlacePicker API which allows the user to select a place on
 * the map and receive detailed information about it.
 */
public class PlacePicker {

  /**
   * Returns the place's attributions.
   * @param intent intent returned in {@link Activity#onActivityResult(int, int, Intent)}
   * @return
   */
  public static String getAttributions(Intent intent) {
    throw new RuntimeException("Not implemented yet");
  }

  /**
   * Returns the place's bounds.
   * @param intent intent returned in {@link Activity#onActivityResult(int, int, Intent)}
   * @return
   */
  public static LatLngBounds getLatLngBounds(Intent intent) {
    throw new RuntimeException("Not implemented yet");
  }

  /**
   * Returns the {@link Place object}.
   * @param context
   * @param intent intent returned in {@link Activity#onActivityResult(int, int, Intent)}
   * @return
   */
  public static Place getPlace(Context context, Intent intent) {
    throw new RuntimeException("Not implemented yet");
  }

  /**
   * Handles constructing an intent to launch the Place Picker UI.
   */
  public static class IntentBuilder {

    private LatLngBounds bounds;

    /**
     * Construct an intent which will launch the Place Picker UI.
     * @param activity
     * @return
     */
    public Intent build(Activity activity) {
      Intent intent = new Intent(activity, PlacePickerActivity.class);
      //TODO: add bounds if not null
      return intent;
    }

    /**
     * Set the bounds that the Place Picker map should be centered on. If not set, the map will be
     * centered on the user's current location.
     * @param latLngBounds
     * @return
     */
    public PlacePicker.IntentBuilder setLatLngBounds(LatLngBounds latLngBounds) {
      this.bounds = latLngBounds;
      return this;
    }
  }
}
