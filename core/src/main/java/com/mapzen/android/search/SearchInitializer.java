package com.mapzen.android.search;

import android.content.Context;

import java.util.Map;

/**
 * Handles setting the api key and a request handler for given {@link MapzenSearch} objects.
 */
public class SearchInitializer {
  private MapzenSearchHttpHandler requestHandler;

  /**
   * Constructor.
   * @param context
   */
  public SearchInitializer(Context context) {
    requestHandler = new MapzenSearchHttpHandler(context) {
      @Override public Map<String, String> queryParamsForRequest() {
        return null;
      }

      @Override public Map<String, String> headersForRequest() {
        return null;
      }
    };
  }

  /**
   * Set the {@link MapzenSearch}'s {@link MapzenSearchHttpHandler}.
   * @param search
   */
  public void initSearch(MapzenSearch search) {
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
