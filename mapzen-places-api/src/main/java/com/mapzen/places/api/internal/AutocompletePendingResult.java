package com.mapzen.places.api.internal;

import com.mapzen.android.lost.api.PendingResult;
import com.mapzen.android.lost.api.ResultCallback;
import com.mapzen.android.lost.api.Status;
import com.mapzen.pelias.Pelias;
import com.mapzen.pelias.gson.Result;
import com.mapzen.places.api.AutocompleteFilter;
import com.mapzen.places.api.AutocompletePredictionBuffer;
import com.mapzen.places.api.LatLng;
import com.mapzen.places.api.LatLngBounds;

import android.support.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AutocompletePendingResult extends PendingResult<AutocompletePredictionBuffer> {

  private final Pelias pelias;
  private final String query;
  private final LatLngBounds bounds;
  private final AutocompleteFilter filter;

  public AutocompletePendingResult(Pelias pelias, String query, LatLngBounds bounds,
      AutocompleteFilter filter) {
    this.pelias = pelias;
    this.query = query;
    this.bounds = bounds;
    this.filter = filter;
  }

  @NonNull @Override public AutocompletePredictionBuffer await() {
    throw new RuntimeException("Not implemented yet");
  }

  @NonNull @Override
  public AutocompletePredictionBuffer await(long time, @NonNull TimeUnit timeUnit) {
    throw new RuntimeException("Not implemented yet");
  }

  @Override public void cancel() {
    throw new RuntimeException("Not implemented yet");
  }

  @Override public boolean isCanceled() {
    throw new RuntimeException("Not implemented yet");
  }

  @Override public void setResultCallback(
      @NonNull final ResultCallback<? super AutocompletePredictionBuffer> callback) {
    LatLng center = bounds.getCenter();
    pelias.suggest(query, center.getLatitude(), center.getLongitude(), new Callback<Result>() {
      @Override public void success(Result result, Response response) {
        Status status = new Status(Status.SUCCESS);
        AutocompletePredictionBuffer buffer = new AutocompletePredictionBuffer(status);
        callback.onResult(buffer);
      }

      @Override public void failure(RetrofitError error) {
        Status status = new Status(Status.INTERNAL_ERROR);
        AutocompletePredictionBuffer buffer = new AutocompletePredictionBuffer(status);
        callback.onResult(buffer);
      }
    });
  }

  @Override public void setResultCallback(
      @NonNull ResultCallback<? super AutocompletePredictionBuffer> callback, long time,
      @NonNull TimeUnit timeUnit) {
    throw new RuntimeException("Not implemented yet");
  }
}