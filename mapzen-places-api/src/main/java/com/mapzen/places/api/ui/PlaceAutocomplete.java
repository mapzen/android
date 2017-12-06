package com.mapzen.places.api.ui;

import com.mapzen.android.lost.api.Status;
import com.mapzen.places.api.AutocompleteFilter;
import com.mapzen.places.api.LatLngBounds;
import com.mapzen.places.api.Place;
import com.mapzen.places.api.internal.PlaceAutocompleteActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.mapzen.places.api.internal.PlaceIntentConsts.EXTRA_BOUNDS;
import static com.mapzen.places.api.internal.PlaceIntentConsts.EXTRA_FILTER;
import static com.mapzen.places.api.internal.PlaceIntentConsts.EXTRA_MODE;
import static com.mapzen.places.api.internal.PlaceIntentConsts.EXTRA_PLACE;
import static com.mapzen.places.api.internal.PlaceIntentConsts.EXTRA_STATUS;

/**
 * Main entry point for the autocomplete API. A search widget is presented and users can select
 * autocomplete results. Unlike the {@link PlacePicker} API, a map is not shown.
 */
public class PlaceAutocomplete {

  public static final int MODE_FULLSCREEN = 1;
  public static final int MODE_OVERLAY = 2;

  /**
   * Retrieves a {@link Place} object from the {@link Intent}.
   * @param context
   * @param intent
   * @return
   */
  @Nullable public static Place getPlace(@Nullable Context context, @Nullable Intent intent) {
    if (intent == null) {
      return null;
    }
    return intent.getParcelableExtra(EXTRA_PLACE);
  }

  /**
   * Retrieves a {@link Status} object from the {@link Intent}.
   * @param context
   * @param intent
   * @return
   */
  @Nullable public static Status getStatus(@Nullable Context context, @Nullable Intent intent) {
    if (intent == null) {
      return null;
    }
    return intent.getParcelableExtra(EXTRA_STATUS);
  }

  /**
   * Handles constructing an intent to launch the PlaceAutocomplete UI.
   */
  public static class IntentBuilder {

    private LatLngBounds bounds;
    private AutocompleteFilter filter;
    private int mode = MODE_OVERLAY;

    /**
     * Construct builder with either {@link PlaceAutocomplete#MODE_FULLSCREEN} or
     * {@link PlaceAutocomplete#MODE_OVERLAY}.
     * @param mode
     */
    public IntentBuilder(int mode) {
      this.mode = mode;
    }

    /**
     * Construct an intent which will launch the PlaceAutocomplete UI.
     * @param activity
     * @return
     */
    @NonNull public Intent build(@NonNull Activity activity) {
      Intent intent = new Intent(activity, PlaceAutocompleteActivity.class);
      intent.putExtra(EXTRA_BOUNDS, bounds);
      intent.putExtra(EXTRA_FILTER, filter);
      intent.putExtra(EXTRA_MODE, mode);
      return intent;
    }

    /**
     * Set the bounds that the Place Picker map should be centered on. If not set, you should
     * request android.permission.ACCESS_COARSE_LOCATION in your manifest so that the map will be
     * centered on the user's current location. If this permission is not present results will not
     * be limited by any bounds.
     * @param latLngBounds
     * @return
     */
    @NonNull public IntentBuilder setBoundsBias(@Nullable LatLngBounds latLngBounds) {
      this.bounds = latLngBounds;
      return this;
    }

    /**
     * Set a filter to be used to limit autocomplete results.
     * @param filter
     * @return
     */
    @NonNull public IntentBuilder setFilter(@Nullable AutocompleteFilter filter) {
      this.filter = filter;
      return this;
    }
  }
}
