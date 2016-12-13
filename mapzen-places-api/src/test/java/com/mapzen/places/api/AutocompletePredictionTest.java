package com.mapzen.places.api;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AutocompletePredictionTest {

  AutocompletePrediction prediction;
  String id = "abc123";
  String primaryText = "Roberta's Pizza";

  @Before public void setup() {
    prediction = new AutocompletePrediction(id, primaryText);
  }

  @Test(expected = RuntimeException.class)
  public void getFullText_shouldThrowException() {
    prediction.getFullText(null);
  }

  @Test public void getPrimaryText_shouldReturnCorrectText() {
    CharSequence text = prediction.getPrimaryText(null);
    assertThat(text.toString()).isEqualTo(primaryText);
  }

  @Test(expected = RuntimeException.class)
  public void getSecondaryText_shouldThrowException() {
    prediction.getSecondaryText(null);
  }

  @Test public void getPlaceId_shouldReturnCorrectId() {
    String placeId = prediction.getPlaceId();
    assertThat(placeId).isEqualTo(id);
  }

  @Test(expected = RuntimeException.class)
  public void getPlaceTypes_shouldThrowException() {
    prediction.getPlaceTypes();
  }
}
