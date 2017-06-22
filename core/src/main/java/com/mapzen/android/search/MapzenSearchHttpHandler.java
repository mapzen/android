package com.mapzen.android.search;

import com.mapzen.android.core.GenericHttpHandler;
import com.mapzen.android.core.MapzenManager;
import com.mapzen.pelias.PeliasRequestHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Base class for HTTP requests made by {@link MapzenSearch}.
 */
public abstract class MapzenSearchHttpHandler implements GenericHttpHandler {

  private SearchRequestHandler searchHandler;

  /**
   * Public constructor.
   */
  public MapzenSearchHttpHandler() {
    searchHandler = new SearchRequestHandler();
  }

  /**
   * Returns the internal handler.
   * @return
   */
  SearchRequestHandler searchHandler() {
    return searchHandler;
  }

  /**
   * Request handler in charge of appending the api key and user agent for {@link MapzenSearch}
   * requests.
   */
  class SearchRequestHandler implements PeliasRequestHandler {

    private String apiKey;

    /**
     * Set the api key to be used for {@link MapzenSearch} requests.
     * @param apiKey
     */
    public void setApiKey(String apiKey) {
      this.apiKey = apiKey;
    }

    /**
     * Get the api key used for {@link MapzenSearch} requests.
     */
    public String getApiKey() {
      return apiKey;
    }

    @Override public Map<String, String> headersForRequest() {
      Map<String, String> headers = new HashMap<>();
      headers.put(HEADER_USER_AGENT, USER_AGENT);
      Map<String, String> customHeaders = MapzenSearchHttpHandler.this.headersForRequest();
      if (customHeaders != null) {
        headers.putAll(customHeaders);
      }
      return headers;
    }

    @Override public Map<String, String> queryParamsForRequest() {
      HashMap<String, String> params = new HashMap<>();
      params.put(MapzenManager.API_KEY_PARAM_NAME, apiKey);
      Map<String, String> customParams = MapzenSearchHttpHandler.this.queryParamsForRequest();
      if (customParams != null) {
        params.putAll(customParams);
      }
      return params;
    }
  }
}
