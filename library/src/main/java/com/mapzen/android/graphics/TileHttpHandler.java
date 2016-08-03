package com.mapzen.android.graphics;

import com.mapzen.tangram.HttpHandler;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;

/**
 * A handler responsible for appending an API key to vector tile requests.
 */
public class TileHttpHandler extends HttpHandler {
  static final String PARAM_API_KEY = "?api_key=";

  private String apiKey;

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
    final String urlWithKey = url + PARAM_API_KEY + apiKey;
    return super.onRequest(urlWithKey, cb);
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
