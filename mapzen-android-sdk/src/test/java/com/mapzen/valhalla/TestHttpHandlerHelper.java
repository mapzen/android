package com.mapzen.valhalla;


import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Used to get access to internal variables for
 * {@link com.mapzen.android.routing.MapzenRouterHttpHandlerTest}.
 */
public class TestHttpHandlerHelper {

  public static String getEndpoint(HttpHandler httpHandler) {
    return httpHandler.endpoint;
  }

  public static HttpLoggingInterceptor.Level getLogLevel(HttpHandler httpHandler) {
    return httpHandler.logLevel;
  }
}
