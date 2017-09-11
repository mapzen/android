package com.mapzen.android.routing;

import android.content.Context;

import java.util.Map;

/**
 * Handles setting the request handler for given {@link MapzenRouter} objects.
 */
public class RouterInitializer {

  final MapzenRouterHttpHandler requestHandler;

  /**
   * Constructor.
   * @param context
   */
  public RouterInitializer(Context context) {
    requestHandler = new MapzenRouterHttpHandler(context) {
      @Override public Map<String, String> queryParamsForRequest() {
        return null;
      }

      @Override public Map<String, String> headersForRequest() {
        return null;
      }
    };
  }

  /**
   * Set the {@link MapzenRouter}'s {@link MapzenRouterHttpHandler}.
   * @param router
   */
  public void initRouter(MapzenRouter router) {
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
