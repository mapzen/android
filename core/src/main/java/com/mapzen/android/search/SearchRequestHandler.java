package com.mapzen.android.search;

import com.mapzen.android.core.MapzenManager;
import com.mapzen.pelias.PeliasRequestHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Request handler in charge of appending the api key for {@link MapzenSearch} requests.
 */
public class SearchRequestHandler implements PeliasRequestHandler {

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
    return null;
  }

  @Override public Map<String, String> queryParamsForRequest() {
    HashMap<String, String> params = new HashMap<>();
    params.put(MapzenManager.API_KEY_PARAM_NAME, apiKey);
    return params;
  }
}
