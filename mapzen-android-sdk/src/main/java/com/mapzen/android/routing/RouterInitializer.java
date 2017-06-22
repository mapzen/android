package com.mapzen.android.routing;

import com.mapzen.android.core.MapzenManager;

import android.content.Context;

import java.util.Map;

/**
 * Handles setting the api key and a request handler for given {@link MapzenRouter} objects.
 */
public class RouterInitializer {
  final MapzenRouterHttpHandler requestHandler = new MapzenRouterHttpHandler() {
    @Override public Map<String, String> queryParamsForRequest() {
      return null;
    }

    @Override public Map<String, String> headersForRequest() {
      return null;
    }
  };

  /**
   * Initialize the {@link MapzenRouter}'s api key from mapzen.xml and set it's
   * {@link MapzenRouterHttpHandler}.
   * @param router
   * @param context
   */
  public void initRouter(MapzenRouter router, Context context) {
    initRouter(router, MapzenManager.instance(context).getApiKey());
  }

  /**
   * Initialize the {@link MapzenRouter}'s api key in code and set it's
   * {@link MapzenRouterHttpHandler}.
   * @param router
   * @param apiKey
   */
  public void initRouter(MapzenRouter router, String apiKey) {
    requestHandler.turnByTurnHandler().setApiKey(apiKey);
    router.getRouter().setHttpHandler(requestHandler.turnByTurnHandler());
  }

  /**
   * Returns the request handler.
   * @return
   */
  public MapzenRouterHttpHandler getRequestHandler() {
    return requestHandler;
  }
}
