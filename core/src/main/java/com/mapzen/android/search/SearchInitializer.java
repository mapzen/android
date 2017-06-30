package com.mapzen.android.search;

import com.mapzen.android.core.MapzenManager;

import android.content.Context;

import java.util.Map;

/**
 * Handles setting the api key and a request handler for given {@link MapzenSearch} objects.
 */
public class SearchInitializer {
  private MapzenSearchHttpHandler requestHandler = new MapzenSearchHttpHandler() {
    @Override public Map<String, String> queryParamsForRequest() {
      return null;
    }

    @Override public Map<String, String> headersForRequest() {
      return null;
    }
  };

  /**
   * Initialize the {@link MapzenSearch}'s api key from mapzen.xml and set it's
   * {@link MapzenSearchHttpHandler}.
   * @param search
   * @param context
   */
  public void initSearch(MapzenSearch search, Context context) {
    initSearch(search, MapzenManager.instance(context).getApiKey());
  }

  /**
   * Initialize the {@link MapzenSearch}'s api key in code and set it's
   * {@link MapzenSearchHttpHandler}.
   * @param search
   * @param apiKey
   */
  public void initSearch(MapzenSearch search, String apiKey) {
    requestHandler.searchHandler().setApiKey(apiKey);
    search.getPelias().setRequestHandler(requestHandler.searchHandler());
  }

  /**
   * Returns the request handler.
   * @return
   */
  public MapzenSearchHttpHandler getRequestHandler() {
    return requestHandler;
  }
}
