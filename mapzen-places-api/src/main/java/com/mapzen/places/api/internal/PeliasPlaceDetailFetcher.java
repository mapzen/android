package com.mapzen.places.api.internal;

import com.mapzen.pelias.Pelias;
import com.mapzen.pelias.gson.Result;

import java.util.Map;

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
  PeliasPlaceDetailFetcher(Pelias pelias, PeliasCallbackHandler callbackHandler) {
    this.pelias = pelias;
    this.callbackHandler = callbackHandler;
    pelias.setDebug(true);
  }

  @Override public void fetchDetails(final Map<String, String> properties,
      final OnPlaceDetailsFetchedListener listener) {
    final long id = parseId(properties);
    final String nodeOrWay = parseNodeOrWay(properties);
    final String gid = PELIAS_GID_BASE + nodeOrWay + id;
    fetchDetails(gid, listener);
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

  private long parseId(final Map<String, String> properties) {
    if (properties.containsKey(PROP_ID)) {
      String idString = properties.get(PROP_ID);
      if (idString.contains(".")) {
        idString = idString.split("\\.")[0];
      }
      return Long.parseLong(idString);
    }

    return 0;
  }

  private String parseNodeOrWay(final Map<String, String> properties) {
    if (properties.containsKey(PROP_AREA)) {
      return PELIAS_GID_WAY;
    } else {
      return PELIAS_GID_NODE;
    }
  }
}
