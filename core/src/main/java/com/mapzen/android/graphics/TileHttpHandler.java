package com.mapzen.android.graphics;

import com.mapzen.tangram.HttpHandler;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * A handler responsible for appending an API key to vector tile requests.
 */
public class TileHttpHandler extends HttpHandler {
  private static final String TAG = TileHttpHandler.class.getSimpleName();
  private static final boolean DEBUG_REQUESTS = false;

  private HashMap<String, Long> urlToMillis = new HashMap<>();

  @Override public boolean onRequest(final String url, Callback cb) {
    final Callback finalCallback = cb;
    final Callback internalCallback = new Callback() {
      @Override public void onFailure(Call call, IOException e) {
        urlToMillis.remove(url);
        finalCallback.onFailure(call, e);
      }

      @Override public void onResponse(Call call, Response response) throws IOException {
        if (DEBUG_REQUESTS) {
          Long endMillis = System.currentTimeMillis();
          Long startMillis = urlToMillis.get(url);
          Long elapsedMillis = endMillis - startMillis;
          boolean fromCache = response.cacheResponse() != null;
          if (fromCache) {
            Log.d(TAG, url + " - loaded from cache [" + elapsedMillis + "] millis");
          } else {
            Log.d(TAG, url + " - loaded from network [" + elapsedMillis + "] millis");
          }
        }
        urlToMillis.remove(url);
        finalCallback.onResponse(call, response);
      }
    };

    urlToMillis.put(url, System.currentTimeMillis());
    return super.onRequest(url, internalCallback);
  }
}
