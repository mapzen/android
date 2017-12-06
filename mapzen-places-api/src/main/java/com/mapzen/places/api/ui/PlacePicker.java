package com.mapzen.places.api.ui;

import com.mapzen.places.api.LatLngBounds;
import com.mapzen.places.api.Place;
import com.mapzen.places.api.internal.PlacePickerActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.mapzen.places.api.internal.PlaceIntentConsts.EXTRA_BOUNDS;
import static com.mapzen.places.api.internal.PlaceIntentConsts.EXTRA_PLACE;

/**
 * Entry point for interaction with the PlacePicker API which allows the user to select a place on
 * the map and receive detailed information about it.
 */
public class PlacePicker {

  /**
   * Returns the place's attributions.
   *
   * Warning: this property is not yet implemented and will throw an
   * {@link UnsupportedOperationException} if accessed
   * @param intent intent returned in {@link Activity#onActivityResult(int, int, Intent)}
   * @return
   */
  @Nullable public static String getAttributions(@Nullable Intent intent) {
    if (intent == null) {
      return null;
    }
    Place place = intent.getParcelableExtra(EXTRA_PLACE);
    if (place.getAttributions() == null) {
      return null;
    }
    return place.getAttributions().toString();
  }

  /**
   * Returns the place's bounds.
   * @param intent intent returned in {@link Activity#onActivityResult(int, int, Intent)}
   * @return
   */
  @Nullable public static LatLngBounds getLatLngBounds(@Nullable Intent intent) {
    if (intent == null) {
      return null;
    }
    Place place = intent.getParcelableExtra(EXTRA_PLACE);
    return place.getViewport();
  }

  /**
   * Returns the {@link Place object}.
   * @param context
   * @param intent intent returned in {@link Activity#onActivityResult(int, int, Intent)}
   * @return
   */
  @Nullable public static Place getPlace(@Nullable Context context, @Nullable Intent intent) {
    if (intent == null) {
      return null;
    }
    return intent.getParcelableExtra(EXTRA_PLACE);
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
    @Nullable public Intent build(@NonNull Activity activity) {
      Intent intent = new Intent(activity, PlacePickerActivity.class);
      intent.putExtra(EXTRA_BOUNDS, bounds);
      return intent;
    }

    /**
     * Set the bounds that the Place Picker map should be centered on. If not set, the map will be
     * centered on the user's current location.
     * @param latLngBounds
     * @return
     */
    @NonNull public PlacePicker.IntentBuilder setLatLngBounds(@Nullable LatLngBounds latLngBounds) {
      this.bounds = latLngBounds;
      return this;
    }
  }
}
