package com.mapzen.places.api.internal;

import com.mapzen.pelias.Pelias;
import com.mapzen.pelias.gson.Result;
import com.mapzen.tangram.LngLat;

import java.util.Map;

import static com.mapzen.places.api.internal.PlacePickerPresenterImpl.PROPERTY_NAME;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Detail fetcher which uses pelias as the backing data source.
 */
class PeliasPlaceDetailFetcher implements PlaceDetailFetcher {

  private Pelias pelias;
  private PeliasCallbackHandler callbackHandler;

  /**
   * Constructs a new object.
   */
  public PeliasPlaceDetailFetcher(Pelias pelias, PeliasCallbackHandler callbackHandler) {
    this.pelias = pelias;
    this.callbackHandler = callbackHandler;
    pelias.setDebug(true);
  }

  @Override public void fetchDetails(LngLat coordinates, final Map<String, String> properties,
      final OnPlaceDetailsFetchedListener listener) {
    pelias.reverse(coordinates.latitude, coordinates.longitude, new Callback<Result>() {
          @Override public void onResponse(Call<Result> call, Response<Result> response) {
            callbackHandler.handleSuccess(properties.get(PROPERTY_NAME), response, listener);
          }

          @Override public void onFailure(Call<Result> call, Throwable t) {
            callbackHandler.handleFailure(listener);
          }
        }
    );
  }

  @Override public void fetchDetails(String gid, final OnPlaceDetailsFetchedListener listener) {
    pelias.place(gid, new Callback<Result>() {
      @Override public void onResponse(Call<Result> call, Response<Result> response) {
        callbackHandler.handleSuccess(response, listener);
      }

      @Override public void onFailure(Call<Result> call, Throwable t) {
        callbackHandler.handleFailure(listener);
      }
    });
  }
}
