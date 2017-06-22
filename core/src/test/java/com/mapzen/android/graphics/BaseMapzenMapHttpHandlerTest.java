package com.mapzen.android.graphics;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.mapzen.android.core.GenericHttpHandler.HEADER_USER_AGENT;
import static com.mapzen.android.core.GenericHttpHandler.USER_AGENT;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class BaseMapzenMapHttpHandlerTest {
  private BaseMapzenMapHttpHandler httpHandler = new BaseMapzenMapHttpHandler();

  @Test public void shouldNotBeNull() throws Exception {
    assertThat(httpHandler).isNotNull();
  }

  @Test public void shouldAppendUserAgent() throws Exception {
    MapzenMapHttpHandler.RequestEnqueuer enqueuer = mock(
        MapzenMapHttpHandler.RequestEnqueuer.class);
    httpHandler.requestEnqueuer = enqueuer;
    httpHandler.httpHandler().onRequest("test", null);
    Map<String, String> userAgentMap = new HashMap();
    userAgentMap.put(HEADER_USER_AGENT, USER_AGENT);
    verify(enqueuer).enqueueRequest(any(OkHttpClient.class), any(Callback.class), eq("test"),
        eq(userAgentMap));
  }
}
