package com.mapzen.places.api;

import com.mapzen.android.lost.api.Status;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AutocompletePredictionBufferTest {

  AutocompletePredictionBuffer buffer;
  Status status;
  List<AutocompletePrediction> predictions;

  @Before public void setup() {
    status = new Status(Status.SUCCESS);
    predictions = new ArrayList<>();
    predictions.add(new AutocompletePrediction("test", "test"));
    buffer = new AutocompletePredictionBuffer(status, predictions);
  }

  @Test public void getStatus_shouldReturnStatus() {
    Status s = buffer.getStatus();
    assertThat(s).isEqualTo(status);
  }

  @Test public void getCount_shouldReturnCorrectCount() {
    int count = buffer.getCount();
    assertThat(count).isEqualTo(1);
  }

  @Test public void getCount_shouldReturnCorrectCount_nullPredictions() {
    buffer = new AutocompletePredictionBuffer(status, null);
    int count = buffer.getCount();
    assertThat(count).isEqualTo(0);
  }

  @Test public void get_shouldReturnCorrectPrediction() {
    AutocompletePrediction prediction = buffer.get(0);
    assertThat(prediction).isEqualTo(predictions.get(0));
  }

  @Test public void get_shouldReturnCorrectPrediction_nullPredictions() {
    buffer = new AutocompletePredictionBuffer(status, null);
    AutocompletePrediction prediction = buffer.get(0);
    assertThat(prediction).isNull();
  }

  @Test public void get_shouldReturnCorrectPrediction_badIndex() {
    AutocompletePrediction prediction = buffer.get(-1);
    assertThat(prediction).isNull();
    prediction = buffer.get(100);
    assertThat(prediction).isNull();
  }
}
