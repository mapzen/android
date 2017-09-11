package com.mapzen.android.graphics;

import com.mapzen.android.core.GenericHttpHandler;
import com.mapzen.tangram.HttpHandler;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Base class for HTTP requests made by {@link MapzenMap}.
 */
public abstract class MapzenMapHttpHandler implements GenericHttpHandler {

  private HttpHandler httpHandler;
  RequestEnqueuer requestEnqueuer;

  /**
   * Public constructor with no cache configured.
   */
  public MapzenMapHttpHandler() {
    this(null, 0);
  }

  /**
   * Public constructor with cache configured.
   * @param directory cache directory
   * @param maxSize max cache directory size in bytes
   */
  public MapzenMapHttpHandler(File directory, long maxSize) {
    httpHandler = new InternalHttpHandler(directory, maxSize);
    requestEnqueuer = new RequestEnqueuer();
  }

  /**
   * Underlying Tangram handler.
   * @return
   */
  HttpHandler httpHandler() {
    return httpHandler;
  }

  private class InternalHttpHandler extends HttpHandler {

    public InternalHttpHandler(File directory, long maxSize) {
      super(directory, maxSize);
    }

    @Override public boolean onRequest(String url, Callback cb) {
      Map<String, String> customParams = queryParamsForRequest();
      if (customParams != null) {
        for (String key : customParams.keySet()) {
          url = url.concat(key + "=" + customParams.get(key) + "&");
        }
      }

      Map<String, String> headers = new HashMap<>();
      headers.put(HEADER_USER_AGENT, USER_AGENT);
      Map<String, String> customHeaders = headersForRequest();
      if (customHeaders != null) {
        headers.putAll(customHeaders);
      }

      requestEnqueuer.enqueueRequest(okClient, cb, url, headers);
      return true;
    }

    @Override public void onCancel(String url) {
      super.onCancel(url);
    }
  }

  /**
   * Class to handle creating and enqueuing requests.
   */
  class RequestEnqueuer {
    /**
     * Creates and enqueues a {@link Request}.
     * @param client
     * @param callback
     * @param url
     * @param headers
     */
    void enqueueRequest(OkHttpClient client, Callback callback, String url,
        Map<String, String> headers) {
      Request request = new Request.Builder()
          .url(url)
          .headers(Headers.of(headers))
          .build();
      client.newCall(request).enqueue(callback);
    }
  }
}
