package com.mapzen.android.routing;

import com.mapzen.valhalla.HttpHandler;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

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
  public TurnByTurnHttpHandler(HttpLoggingInterceptor.Level logLevel) {
    configure(DEFAULT_URL, logLevel);
  }

  /**
   * Construct handler with url and log levels.
   */
  public TurnByTurnHttpHandler(String endpoint, HttpLoggingInterceptor.Level logLevel) {
    configure(endpoint, logLevel);
  }

  /**
   * Set the api key to be sent with every request.
   */
  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

  @Override protected Response onRequest(Interceptor.Chain chain) throws IOException {
    final HttpUrl url = chain.request()
        .url()
        .newBuilder()
        .addQueryParameter(NAME_API_KEY, apiKey)
        .build();
    return chain.proceed(chain.request().newBuilder().url(url).build());
  }
}
