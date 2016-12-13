package com.mapzen.places.api;

import com.mapzen.android.lost.api.Result;
import com.mapzen.android.lost.api.Status;

import java.util.List;

/**
 * Represents a list of autocomplete results
 */
public class AutocompletePredictionBuffer implements Result, DataBuffer<AutocompletePrediction> {

  private final Status status;
  private final List<AutocompletePrediction> predictions;

  public AutocompletePredictionBuffer(Status status, List<AutocompletePrediction> predictions) {
    this.status = status;
    this.predictions = predictions;
  }

  @Override public Status getStatus() {
    return status;
  }

  @Override public int getCount() {
    if (predictions == null) {
      return 0;
    }
    return predictions.size();
  }

  @Override public AutocompletePrediction get(int index) {
    if (predictions == null || index < 0 || index > predictions.size() - 1) {
      return null;
    }
    return predictions.get(index);
  }
}
