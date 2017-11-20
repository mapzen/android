package com.mapzen.android.search;

import com.mapzen.android.core.CoreDI;
import com.mapzen.android.core.http.CallRequest;
import com.mapzen.android.core.http.Request;
import com.mapzen.pelias.BoundingBox;
import com.mapzen.pelias.Pelias;
import com.mapzen.pelias.PeliasLocationProvider;
import com.mapzen.pelias.gson.Result;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import retrofit2.Callback;

/**
 * This class is the main class for interaction with the Mapzen search api.
 */
public class MapzenSearch {

  @Inject SearchInitializer searchInitializer;

  private Pelias internalSearch;

  /**
   * Creates a new {@link MapzenSearch} with api key set from mapzen.xml.
   */
  public MapzenSearch(@NonNull Context context) {
    initDI(context);
    internalSearch = new Pelias();
    searchInitializer.initSearch(this);
  }

  /**
   * Creates a new {@link MapzenSearch} with the {@link Pelias} object used for underlying
   * search/autocomplete queries.
   */
  public MapzenSearch(@NonNull Context context, @NonNull Pelias pelias) {
    initDI(context);
    internalSearch = pelias;
    searchInitializer.initSearch(this);
  }

  private void initDI(Context context) {
    CoreDI.init(context);
    CoreDI.component().inject(this);
  }

  /**
   * Returns autocomplete suggestions given a query string. The query will use the
   * {@link PeliasLocationProvider} to retrieve a lat/lon to use as a focus point for the request.
   * The callback will be notified upon success or failure of query.
   * @param query
   * @param callback
   */
  public Request suggest(@NonNull String query, @Nullable Callback<Result> callback) {
    return new CallRequest(internalSearch.suggest(query, callback));
  }

  /**
   * Requests autocomplete suggestions given a query and lat/lon. The lat/lon is used as a focus
   * point for results The callback will be notified upon success or failure of the query.
   * @param query
   * @param callback
   */
  public Request suggest(@NonNull String query, double lat, double lon,
      @Nullable Callback<Result> callback) {
    return new CallRequest(internalSearch.suggest(query, lat, lon, callback));
  }

  /**
   * Requests search results given a query. The {@link PeliasLocationProvider} will be used to
   * generate a bounding box for results. The callback will be notified upon success or failure of
   * the query.
   * @param query
   * @param callback
   */
  public Request search(@NonNull String query, @Nullable Callback<Result> callback) {
      return new CallRequest(internalSearch.search(query, callback));
  }

  /**
   * Requests search results given a query. The {@link BoundingBox} will be used to
   * generate relevant results. The callback will be notified upon success or failure of
   * the query.
   * @param query
   * @param callback
   */
  public Request search(@NonNull String query, @NonNull BoundingBox box,
      @Nullable Callback<Result> callback) {
    return new CallRequest(internalSearch.search(query, box, callback));
  }

  /**
   * Requests search results given a query. The lat/lon will be used as a focus point to
   * generate relevant results. The callback will be notified upon success or failure of
   * the query.
   * @param query
   * @param callback
   */
  public Request search(@NonNull String query, double lat, double lon,
      @Nullable Callback<Result> callback) {
      return new CallRequest(internalSearch.search(query, lat, lon, callback));
  }

  /**
   * Issues a reverse geocode request given the lat/lon. The callback will be notified upon success
   * or failure of the query.
   * @param lat
   * @param lon
   * @param callback
   */
  public Request reverse(double lat, double lon, @Nullable Callback<Result> callback) {
    return new CallRequest(internalSearch.reverse(lat, lon, callback));
  }

  /**
   * Issues a place request for a given global identifier. The callback will be notified upon
   * success or failure of the query.
   * @param gid
   * @param callback
   */
  public Request place(@NonNull String gid, @Nullable Callback<Result> callback) {
    return new CallRequest(internalSearch.place(gid, callback));
  }

  /**
   * Set a location provider to be used in search and suggest requests. This will be used to return
   * more relevant results for given positions and areas
   * @param locationProvider
   */
  public void setLocationProvider(@NonNull PeliasLocationProvider locationProvider) {
    internalSearch.setLocationProvider(locationProvider);
  }

  /**
   * Sets the router's http handler for adding custom headers and parameters to
   * requests.
   * @param handler
   */
  public void setHttpHandler(@Nullable MapzenSearchHttpHandler handler) {
    internalSearch.setRequestHandler(handler.searchHandler());
  }

  /**
   * Return the underlying {@link Pelias} object.
   * @return
   */
  @NonNull public Pelias getPelias() {
    return internalSearch;
  }
}
