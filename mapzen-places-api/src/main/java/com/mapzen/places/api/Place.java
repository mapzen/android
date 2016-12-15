package com.mapzen.places.api;

import android.net.Uri;

import java.util.List;
import java.util.Locale;

/**
 * Represents a physical place selected from a {@link com.mapzen.android.graphics.MapzenMap}.
 */
public class Place {

  private final CharSequence address;
  private final CharSequence attributions;
  private final String id;
  private final LatLng latLng;
  private final Locale locale;
  private final CharSequence name;
  private final CharSequence phoneNumber;
  private final List<Integer> placeTypes;
  private final int priceLevel;
  private final float rating;
  private final LatLngBounds viewport;
  private final Uri websiteUri;

  /**
   * Constructs a new place.
   * @param address
   * @param attributions
   * @param id
   * @param latLng
   * @param locale
   * @param name
   * @param phoneNumber
   * @param placeTypes
   * @param priceLevel
   * @param rating
   * @param viewport
   * @param websiteUri
   */
  public Place(CharSequence address, CharSequence attributions, String id, LatLng latLng,
      Locale locale, CharSequence name, CharSequence phoneNumber, List<Integer> placeTypes,
      int priceLevel, float rating, LatLngBounds viewport, Uri websiteUri) {
    this.address = address;
    this.attributions = attributions;
    this.id = id;
    this.latLng = latLng;
    this.locale = locale;
    this.name = name;
    this.phoneNumber = phoneNumber;
    this.placeTypes = placeTypes;
    this.priceLevel = priceLevel;
    this.rating = rating;
    this.viewport = viewport;
    this.websiteUri = websiteUri;
  }

  /**
   * Returns human readable address for this place.
   * @return
   */
  public CharSequence getAddress() {
    return address;
  }

  /**
   * Returns the attributions to be shown to the user if data from the Place is used.
   * @return
   */
  public CharSequence getAttributions() {
    return attributions;
  }

  /**
   * Returns the unique id of this Place.
   * @return
   */
  public String getId() {
    return id;
  }

  /**
   * Returns the location of this Place.
   * @return
   */
  public LatLng getLatLng() {
    return latLng;
  }

  /**
   * Returns the locale in which the names and addresses were localized.
   * @return
   */
  public Locale getLocale() {
    return locale;
  }

  /**
   * Returns the name of this Place.
   * @return
   */
  public CharSequence getName() {
    return name;
  }

  /**
   * Returns the place's phone number in international format.
   * @return
   */
  public CharSequence getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * Returns a list of place types for this Place.
   * @return
   */
  public List<Integer> getPlaceTypes() {
    return placeTypes;
  }

  /**
   * Returns the price level for this place on a scale from 0 (cheapest) to 4.
   * @return
   */
  public int getPriceLevel() {
    return priceLevel;
  }

  /**
   * Returns the place's rating, from 1.0 to 5.0, based on aggregated user reviews.
   * @return
   */
  public float getRating() {
    return rating;
  }

  /**
   * Returns a viewport for displaying this Place.
   * @return
   */
  public LatLngBounds getViewport() {
    return viewport;
  }

  /**
   * Returns website uri for this place.
   * @return
   */
  public Uri getWebsiteUri() {
    return websiteUri;
  }

}
