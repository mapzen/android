package com.mapzen.android;

import com.mapzen.valhalla.HttpHandler;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Handles appending api keys for all turn-by-turn requests.
 */
public class TurnByTurnHttpHandler extends HttpHandler {

  private static final String NAME_API_KEY = "api_key";

  private String apiKey;

  /**
   * Construct handler with default url and log levels.
   */
  public TurnByTurnHttpHandler() {
    configure(DEFAULT_URL, DEFAULT_LOG_LEVEL);
  }

  /**
   * Construct handler with url and default log levels.
   */
  public TurnByTurnHttpHandler(String endpoint) {
    configure(endpoint, DEFAULT_LOG_LEVEL);
  }

  /**
   * Construct handler with log levels and default url.
   */
  public TurnByTurnHttpHandler(RestAdapter.LogLevel logLevel) {
    configure(DEFAULT_URL, logLevel);
  }

  /**
   * Construct handler with url and log levels.
   */
  public TurnByTurnHttpHandler(String endpoint, RestAdapter.LogLevel logLevel) {
    configure(endpoint, logLevel);
  }

  /**
   * Set the api key to be sent with every request.
   */
  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

  @Override
  protected void onRequest(RequestInterceptor.RequestFacade requestFacade) {
    requestFacade.addQueryParam(NAME_API_KEY, this.apiKey);
  }
}
