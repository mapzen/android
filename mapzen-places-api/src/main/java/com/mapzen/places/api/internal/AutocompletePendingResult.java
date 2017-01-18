package com.mapzen.places.api.internal;

import com.mapzen.android.lost.api.PendingResult;
import com.mapzen.android.lost.api.ResultCallback;
import com.mapzen.android.lost.api.Status;
import com.mapzen.pelias.Pelias;
import com.mapzen.pelias.gson.Feature;
import com.mapzen.pelias.gson.Result;
import com.mapzen.places.api.AutocompleteFilter;
import com.mapzen.places.api.AutocompletePrediction;
import com.mapzen.places.api.AutocompletePredictionBuffer;
import com.mapzen.places.api.LatLng;
import com.mapzen.places.api.LatLngBounds;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Object returned by {@link GeoDataApiImpl#getAutocompletePredictions(
 * com.mapzen.android.lost.api.LostApiClient, String, LatLngBounds, AutocompleteFilter)}.
 */
class AutocompletePendingResult extends PendingResult<AutocompletePredictionBuffer> {

  private final Pelias pelias;
  private final String query;
  private final LatLngBounds bounds;
  private final AutocompleteFilter filter;

  /**
   * Constructs a new object given a pelias instance, a query, a lat/lng bounds, and an autocomplete
   * filter.
   * @param pelias
   * @param query
   * @param bounds
   * @param filter
   */
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
      @Override public void onResponse(Call<Result> call, Response<Result> response) {
        Status status = new Status(Status.SUCCESS);

        final ArrayList<AutocompletePrediction> predictions = new ArrayList<>();
        final List<Feature> features = response.body().getFeatures();
        for (Feature feature : features) {
          AutocompletePrediction prediction = new AutocompletePrediction(feature.properties.gid,
              feature.properties.name);
          predictions.add(prediction);
        }

        AutocompletePredictionBuffer buffer = new AutocompletePredictionBuffer(status, predictions);
        callback.onResult(buffer);
      }

      @Override public void onFailure(Call<Result> call, Throwable t) {
        Status status = new Status(Status.INTERNAL_ERROR);
        AutocompletePredictionBuffer buffer = new AutocompletePredictionBuffer(status, null);
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
