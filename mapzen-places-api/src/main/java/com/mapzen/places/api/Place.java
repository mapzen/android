package com.mapzen.places.api;

import android.net.Uri;
import android.os.Parcelable;

import java.util.List;
import java.util.Locale;

/**
 * Represents a physical place selected from a {@link com.mapzen.android.graphics.MapzenMap}.
 */
public interface Place extends Parcelable {

  int TYPE_COUNTRY = 1005;
  int TYPE_LOCALITY = 1009;
  int TYPE_POINT_OF_INTEREST = 1013;
  int TYPE_ESTABLISHMENT = 34;
  int TYPE_STREET_ADDRESS = 1021;
  int TYPE_POLITICAL = 1012;
  int TYPE_SUBLOCALITY = 1022;
  int TYPE_SUBLOCALITY_LEVEL_1 = 1023;
  int TYPE_NEIGHBORHOOD = 1011;
  int TYPE_ROUTE = 1020;
  int TYPE_ADMINISTRATIVE_AREA_LEVEL_1 = 1001;


  /**
   * Returns human readable address for this place.
   * @return
   */
  abstract CharSequence getAddress();

  /**
   * Returns the attributions to be shown to the user if data from the Place is used.
   *
   * Warning: this property is not yet implemented and will throw an
   * {@link UnsupportedOperationException} if accessed
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
   *
   * Warning: this property is not yet implemented and will throw an
   * {@link UnsupportedOperationException} if accessed
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
   *
   * Warning: this property is not yet implemented and will throw an
   * {@link UnsupportedOperationException} if accessed
   * @return
   */
  abstract int getPriceLevel();

  /**
   * Returns the place's rating, from 1.0 to 5.0, based on aggregated user reviews.
   *
   * Warning: this property is not yet implemented and will throw an
   * {@link UnsupportedOperationException} if accessed
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
   *
   * Warning: this property is not yet implemented and will throw an
   * {@link UnsupportedOperationException} if accessed
   * @return
   */
  abstract Uri getWebsiteUri();
}
