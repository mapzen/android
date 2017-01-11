package com.mapzen.places.api.ui;

import com.mapzen.android.lost.api.Status;
import com.mapzen.places.api.AutocompleteFilter;
import com.mapzen.places.api.LatLngBounds;
import com.mapzen.places.api.Place;
import com.mapzen.places.api.internal.PlaceAutocompleteActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import static com.mapzen.places.api.internal.PlaceIntentConsts.EXTRA_BOUNDS;
import static com.mapzen.places.api.internal.PlaceIntentConsts.EXTRA_PLACE;

/**
 * Main entry point for the autocomplete API. A search widget is presented and users can select
 * autocomplete results. Unlike the {@link PlacePicker} API, a map is not shown.
 */
public class PlaceAutocomplete {

  public static final int MODE_FULLSCREEN = 1;
  public static final int MODE_OVERLAY = 2;

  public static final String EXTRA_STATUS = "extra_status";
  public static final String EXTRA_FILTER = "extra_filter";

  /**
   * Returns a {@link Place} object.
   * @param context
   * @param intent
   * @return
   */
  public static Place getPlace(Context context, Intent intent) {
    if (intent == null) {
      return null;
    }
    return intent.getParcelableExtra(EXTRA_PLACE);
  }

  public static Status getStatus(Context context, Intent intent) {
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
    private int mode;

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
    public Intent build(Activity activity) {
      Intent intent = new Intent(activity, PlaceAutocompleteActivity.class);
      intent.putExtra(EXTRA_BOUNDS, bounds);
      intent.putExtra(EXTRA_FILTER, filter);
      return intent;
    }

    /**
     * Set the bounds that the Place Picker map should be centered on. If not set, the map will be
     * centered on the user's current location.
     * @param latLngBounds
     * @return
     */
    public IntentBuilder setBoundsBias(LatLngBounds latLngBounds) {
      this.bounds = latLngBounds;
      return this;
    }

    public IntentBuilder setFilter(AutocompleteFilter filter) {
      this.filter = filter;
      return this;
    }
  }
}
