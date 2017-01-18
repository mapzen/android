package com.mapzen.places.api;

import android.net.Uri;
import android.os.Parcelable;

import java.util.List;
import java.util.Locale;

/**
 * Represents a physical place selected from a {@link com.mapzen.android.graphics.MapzenMap}.
 */
public interface Place extends Parcelable {

  /**
   * Returns human readable address for this place.
   * @return
   */
  abstract CharSequence getAddress();

  /**
   * Returns the attributions to be shown to the user if data from the Place is used.
   * @return
   */
  abstract CharSequence getAttributions();

  /**
   * Returns the unique id of this Place.
   * @return
   */
  abstract String getId();

  /**
   * Returns the location of this Place.
   * @return
   */
  abstract LatLng getLatLng();

  /**
   * Returns the locale in which the names and addresses were localized.
   * @return
   */
  abstract Locale getLocale();

  /**
   * Returns the name of this Place.
   * @return
   */
  abstract CharSequence getName();

  /**
   * Returns the place's phone number in international format.
   * @return
   */
  abstract CharSequence getPhoneNumber();

  /**
   * Returns a list of place types for this Place.
   * @return
   */
  abstract List<Integer> getPlaceTypes();

  /**
   * Returns the price level for this place on a scale from 0 (cheapest) to 4.
   * @return
   */
  abstract int getPriceLevel();

  /**
   * Returns the place's rating, from 1.0 to 5.0, based on aggregated user reviews.
   * @return
   */
  abstract float getRating();

  /**
   * Returns a viewport for displaying this Place.
   * @return
   */
  abstract LatLngBounds getViewport();

  /**
   * Returns website uri for this place.
   * @return
   */
  abstract Uri getWebsiteUri();
}
