package com.mapzen.android.graphics;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.mapzen.android.core.GenericHttpHandler.HEADER_USER_AGENT;
import static com.mapzen.android.core.GenericHttpHandler.USER_AGENT;
import static com.mapzen.android.core.TestVals.testHeaders;
import static com.mapzen.android.core.TestVals.testParams;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MapzenMapHttpHandlerTest {

  MapzenMapHttpHandler handler;
  MapzenMapHttpHandler.RequestEnqueuer enqueuer;

  @Before public void setup() throws Exception {
    handler = new MapzenMapHttpHandler() {
      @Override public Map<String, String> queryParamsForRequest() {
        return testParams();
      }

      @Override public Map<String, String> headersForRequest() {
        return testHeaders();
      }
    };
    enqueuer = mock(MapzenMapHttpHandler.RequestEnqueuer.class);
    handler.requestEnqueuer = enqueuer;
  }

  @Test public void shouldHaveCorrectUrlAndHeaders() throws Exception {
    handler.httpHandler().onRequest("http://mapzen.com?key=test&", null);
    Map<String, String> userAgentMap = new HashMap();
    userAgentMap.put(HEADER_USER_AGENT, USER_AGENT);
    String url = "http://mapzen.com?key=test&param1=val1&param2=val2&";
    Map headers = testHeaders();
    headers.put(HEADER_USER_AGENT, USER_AGENT);
    verify(enqueuer).enqueueRequest(any(OkHttpClient.class), any(Callback.class), eq(url),
        eq(headers));
  }
}
