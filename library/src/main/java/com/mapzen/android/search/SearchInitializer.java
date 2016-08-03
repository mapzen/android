package com.mapzen.android.search;


import com.mapzen.android.core.ApiKeyConstants;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

/**
 * Handles setting the api key and a request handler for given {@link MapzenSearch} objects.
 */
public class SearchInitializer {

  private static final String TAG = SearchInitializer.class.getSimpleName();

  private SearchRequestHandler requestHandler = new SearchRequestHandler();

  /**
   * Initialize the {@link MapzenSearch}'s api key from mapzen.xml and set it's
   * {@link SearchRequestHandler}.
   * @param search
   * @param context
   */
  public void initSearch(MapzenSearch search, Context context) {
    Resources res = context.getResources();
    final String packageName = context.getPackageName();
    try {
      final int apiKeyId = res.getIdentifier(ApiKeyConstants.API_KEY_SEARCH_RES_NAME,
          ApiKeyConstants.API_KEY_RES_TYPE, packageName);
      final String apiKey = res.getString(apiKeyId);
      initSearch(search, apiKey);
    } catch (Resources.NotFoundException e) {
      Log.e(TAG, e.getLocalizedMessage());
    }
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
