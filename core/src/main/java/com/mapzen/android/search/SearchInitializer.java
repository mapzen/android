package com.mapzen.android.search;

import com.mapzen.android.core.MapzenManager;

import android.content.Context;

/**
 * Handles setting the api key and a request handler for given {@link MapzenSearch} objects.
 */
public class SearchInitializer {
  private SearchRequestHandler requestHandler = new SearchRequestHandler();

  /**
   * Initialize the {@link MapzenSearch}'s api key from mapzen.xml and set it's
   * {@link SearchRequestHandler}.
   * @param search
   * @param context
   */
  public void initSearch(MapzenSearch search, Context context) {
    initSearch(search, MapzenManager.instance(context).getApiKey());
  }

  /**
   * Initialize the {@link MapzenSearch}'s api key in code and set it's
   * {@link SearchRequestHandler}.
   * @param search
   * @param apiKey
   */
  public void initSearch(MapzenSearch search, String apiKey) {
    requestHandler.setApiKey(apiKey);
    search.getPelias().setRequestHandler(requestHandler);
  }

  /**
   * Returns the request handler.
   * @return
   */
  public SearchRequestHandler getRequestHandler() {
    return requestHandler;
  }
}
