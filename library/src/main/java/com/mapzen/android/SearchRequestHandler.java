package com.mapzen.android;

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
    params.put(ApiKeyConstants.API_KEY, apiKey);
    return params;
  }
}
