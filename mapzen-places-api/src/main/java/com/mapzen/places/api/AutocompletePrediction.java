package com.mapzen.places.api;

import android.support.annotation.Nullable;
import android.text.style.CharacterStyle;

import java.util.List;

public class AutocompletePrediction {

  private final String placeId;
  private final String primaryText;

  public AutocompletePrediction(String id, String text) {
    placeId = id;
    primaryText = text;
  }

  public CharSequence getFullText(@Nullable CharacterStyle characterStyle) {
    throw new RuntimeException("Not implemented yet");
  }

  public CharSequence getPrimaryText(@Nullable CharacterStyle characterStyle) {
    return primaryText;
  }

  public CharSequence getSecondaryText(@Nullable CharacterStyle characterStyle) {
    throw new RuntimeException("Not implemented yet");
  }

  @Nullable
  public String getPlaceId() {
    return placeId;
  }

  @Nullable
  public List<Integer> getPlaceTypes() {
    throw new RuntimeException("Not implemented yet");
  }
}