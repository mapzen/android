package com.mapzen.android.routing;

import com.mapzen.android.core.GenericHttpHandler;
import com.mapzen.valhalla.HttpHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.mapzen.android.core.GenericHttpHandler.LogLevel.BASIC;
import static com.mapzen.android.core.GenericHttpHandler.LogLevel.BODY;
import static com.mapzen.android.core.GenericHttpHandler.LogLevel.HEADERS;
import static com.mapzen.android.core.GenericHttpHandler.LogLevel.NONE;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Base class for HTTP requests made by {@link MapzenRouter}.
 */
public abstract class MapzenRouterHttpHandler implements GenericHttpHandler {

  public static final String DEFAULT_URL = "https://valhalla.mapzen.com/";
  public static final LogLevel DEFAULT_LOG_LEVEL = MapzenRouterHttpHandler.getDefaultLogLevel();
  private TurnByTurnHttpHandler handler;
  ChainProceder chainProceder = new ChainProceder();

  /**
   * Construct handler with default url and log levels.
   */
  public MapzenRouterHttpHandler() {
    handler = new TurnByTurnHttpHandler(DEFAULT_URL, DEFAULT_LOG_LEVEL);
  }

  /**
   * Construct handler with custom url and log levels.
   */
  public MapzenRouterHttpHandler(String url, LogLevel logLevel) {
    handler = new TurnByTurnHttpHandler(url, logLevel);
  }

  /**
   * Returns the internal handler.
   * @return
   */
  TurnByTurnHttpHandler turnByTurnHandler() {
    return handler;
  }

  private static LogLevel getDefaultLogLevel() {
    return BASIC;
  }

  /**
   * Handles appending api keys for all turn-by-turn requests.
   */
  class TurnByTurnHttpHandler extends HttpHandler {

    static final String NAME_API_KEY = "api_key";

    private String apiKey;

    private final Map<LogLevel, HttpLoggingInterceptor.Level> TO_INTERNAL_LEVEL = new HashMap() {
      {
        put(NONE, HttpLoggingInterceptor.Level.NONE);
        put(BASIC, HttpLoggingInterceptor.Level.BASIC);
        put(HEADERS, HttpLoggingInterceptor.Level.HEADERS);
        put(BODY, HttpLoggingInterceptor.Level.BODY);
      }
    };

    /**
     * Construct handler with url and log levels.
     */
    public TurnByTurnHttpHandler(String endpoint, LogLevel logLevel) {
      configure(endpoint, TO_INTERNAL_LEVEL.get(logLevel));
    }

    /**
     * Set the api key to be sent with every request.
     */
    public void setApiKey(String apiKey) {
      this.apiKey = apiKey;
    }

    @Override protected Response onRequest(Interceptor.Chain chain) throws IOException {
      Map params = new HashMap();
      params.put(NAME_API_KEY, apiKey);
      Map customParams = queryParamsForRequest();
      if (customParams != null) {
        params.putAll(customParams);
      }

      Map headers = new HashMap();
      headers.put(HEADER_USER_AGENT, USER_AGENT);
      Map customHeaders = headersForRequest();
      if (customHeaders != null) {
        headers.putAll(customHeaders);
      }

      return chainProceder.proceed(chain, params, headers);
    }
  }

  /**
   * Class to handle creating requests to feed to the chain.
   */
  class ChainProceder {
    /**
     * Creates a request given the parameters and headers and feeds it to the chain for execution.
     * @param chain
     * @param params
     * @param headers
     * @return
     * @throws IOException
     */
    Response proceed(Interceptor.Chain chain, Map<String, String> params,
        Map<String, String> headers) throws IOException {
      final HttpUrl.Builder urlBuilder = chain
          .request()
          .url()
          .newBuilder();
      for (String param : params.keySet()) {
        urlBuilder.addQueryParameter(param, params.get(param));
      }

      Request.Builder requestBuilder = chain
          .request()
          .newBuilder()
          .url(urlBuilder.build());
      for (String header : headers.keySet()) {
        requestBuilder.header(header, headers.get(header));
      }

      return chain.proceed(requestBuilder.build());
    }
  }
}
