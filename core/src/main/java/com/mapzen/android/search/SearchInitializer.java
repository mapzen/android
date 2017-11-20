package com.mapzen.android.search;

import android.content.Context;
import android.support.annotation.NonNull;

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
  public SearchInitializer(@NonNull Context context) {
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
  public void initSearch(@NonNull MapzenSearch search) {
    search.getPelias().setRequestHandler(requestHandler.searchHandler());
  }


  /**
   * Returns the request handler.
   * @return
   */
  @NonNull public MapzenSearchHttpHandler getRequestHandler() {
    return requestHandler;
  }
}
