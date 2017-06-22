package com.mapzen.android.graphics;

import java.util.Map;

/**
 * Base implementation of {@link MapzenMapHttpHandler}.
 */
public class BaseMapzenMapHttpHandler extends MapzenMapHttpHandler {
  /**
   * Base implementation doesn't return any custom parameters.
   * @return
   */
  @Override public Map<String, String> queryParamsForRequest() {
    return null;
  }

  /**
   * Base implementation doesn't return any custom headers.
   * @return
   */
  @Override public Map<String, String> headersForRequest() {
    return null;
  }
}
