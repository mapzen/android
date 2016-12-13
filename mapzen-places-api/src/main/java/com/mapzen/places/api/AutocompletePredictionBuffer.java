package com.mapzen.places.api;

import com.mapzen.android.lost.api.Result;
import com.mapzen.android.lost.api.Status;

/**
 * Represents a list of autocomplete results
 */
public class AutocompletePredictionBuffer implements Result {

  private final Status status;

  public AutocompletePredictionBuffer(Status status) {
    this.status = status;
  }

  @Override public Status getStatus() {
    return status;
  }
}
