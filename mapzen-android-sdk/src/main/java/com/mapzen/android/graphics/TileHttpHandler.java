package com.mapzen.android.graphics;

import com.mapzen.tangram.HttpHandler;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;

/**
 * A handler responsible for appending an API key to vector tile requests.
 */
public class TileHttpHandler extends HttpHandler {
  private static final String TAG = TileHttpHandler.class.getSimpleName();
  private static final boolean DEBUG_REQUESTS = false;

  static final String PARAM_API_KEY = "?api_key=";

  private String apiKey;
  private HashMap<String, Long> urlToMillis = new HashMap<>();

  /**
   * Creates a new HTTP handler.
   *
   * @param apiKey key to append to all requests.
   */
  public TileHttpHandler(String apiKey) {
    this.apiKey = apiKey;
  }

  /**
   * Creates a new HTTP handler with the given {@link Request.Builder}.
   *
   * @param apiKey key to append to all requests.
   * @param okRequestBuilder instance that should be used to build HTTP requests.
   */
  TileHttpHandler(String apiKey, Request.Builder okRequestBuilder) {
    this.apiKey = apiKey;
    this.okRequestBuilder = okRequestBuilder;
  }

  @Override public boolean onRequest(String url, Callback cb) {
    final Callback fnlCb = cb;
    final String urlWithKey = url + PARAM_API_KEY + apiKey;
    Callback internalCallback = new Callback() {
      @Override public void onFailure(Request request, IOException e) {
        String requestUrl = request.urlString();
        urlToMillis.remove(requestUrl);
        fnlCb.onFailure(request, e);
      }

      @Override public void onResponse(Response response) throws IOException {
        String requestUrl = response.request().urlString();
        if (DEBUG_REQUESTS) {
          Long endMillis = System.currentTimeMillis();
          Long startMillis = urlToMillis.get(requestUrl);
          Long elapsedMillis = endMillis - startMillis;
          boolean fromCache = response.cacheResponse() != null;
          if (fromCache) {
            Log.d(TAG, "loaded from cache [" + elapsedMillis + "] millis");
          } else {
            Log.d(TAG, "loaded from network [" + elapsedMillis + "] millis");
          }
        }
        urlToMillis.remove(requestUrl);
        fnlCb.onResponse(response);
      }
    };
    urlToMillis.put(urlWithKey, System.currentTimeMillis());
    return super.onRequest(urlWithKey, internalCallback);
  }

  @Override public void onCancel(String url) {
    final String urlWithKey = url + PARAM_API_KEY + apiKey;
    super.onCancel(urlWithKey);
  }

  void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

  String getApiKey() {
    return apiKey;
  }
}
