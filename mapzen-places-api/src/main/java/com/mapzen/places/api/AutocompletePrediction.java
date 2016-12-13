package com.mapzen.places.api;

import android.support.annotation.Nullable;
import android.text.style.CharacterStyle;

import java.util.List;

/**
 * Represents a place returned from {@link GeoDataApi#getAutocompletePredictions(
 * com.mapzen.android.lost.api.LostApiClient, String, LatLngBounds, AutocompleteFilter)}.
 */
public class AutocompletePrediction {

  private final String placeId;
  private final String primaryText;

  /**
   * Constructs a new prediction given an id an primary text.
   * @param id
   * @param text
   */
  public AutocompletePrediction(String id, String text) {
    placeId = id;
    primaryText = text;
  }

  /**
   * Not implemented yet.
   * @param characterStyle
   * @return
   */
  public CharSequence getFullText(@Nullable CharacterStyle characterStyle) {
    throw new RuntimeException("Not implemented yet");
  }

  /**
   * Returns the prediction's primary text.
   * @param characterStyle
   * @return
   */
  public CharSequence getPrimaryText(@Nullable CharacterStyle characterStyle) {
    return primaryText;
  }

  /**
   * Not implemented yet.
   * @param characterStyle
   * @return
   */
  public CharSequence getSecondaryText(@Nullable CharacterStyle characterStyle) {
    throw new RuntimeException("Not implemented yet");
  }

  /**
   * Return's the prediction's id.
   * @return
   */
  @Nullable
  public String getPlaceId() {
    return placeId;
  }

  /**
   * Not implemented yet.
   * @return
   */
  @Nullable
  public List<Integer> getPlaceTypes() {
    throw new RuntimeException("Not implemented yet");
  }
}
