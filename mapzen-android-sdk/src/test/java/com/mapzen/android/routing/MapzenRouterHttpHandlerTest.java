package com.mapzen.android.routing;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.mapzen.android.core.GenericHttpHandler.HEADER_USER_AGENT;
import static com.mapzen.android.core.GenericHttpHandler.USER_AGENT;
import static com.mapzen.android.routing.MapzenRouterHttpHandler.TurnByTurnHttpHandler.NAME_API_KEY;
import okhttp3.Interceptor;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MapzenRouterHttpHandlerTest {

  Map testParams;
  Map testHeaders;

  MapzenRouterHttpHandler httpHandler = new MapzenRouterHttpHandler() {
    @Override public Map<String, String> queryParamsForRequest() {
      return testParams;
    }

    @Override public Map<String, String> headersForRequest() {
      return testHeaders;
    }
  };

  @Test public void onRequest_shouldProceedWithDefaultParamsAndHeaders() throws Exception {
    httpHandler.turnByTurnHandler().setApiKey("test-key");
    MapzenRouterHttpHandler.ChainProceder proceder = mock(
        MapzenRouterHttpHandler.ChainProceder.class);
    httpHandler.chainProceder = proceder;
    Interceptor.Chain chain = mock(Interceptor.Chain.class);
    httpHandler.turnByTurnHandler().onRequest(chain);

    Map params = new HashMap();
    params.put(NAME_API_KEY, "test-key");
    Map headers = new HashMap();
    headers.put(HEADER_USER_AGENT, USER_AGENT);

    verify(proceder).proceed(chain, params, headers);
  }

  @Test public void onRequest_shouldProceedWithAllParamsAndHeaders() throws Exception {
    httpHandler.turnByTurnHandler().setApiKey("test-key");
    MapzenRouterHttpHandler.ChainProceder proceder = mock(
        MapzenRouterHttpHandler.ChainProceder.class);
    httpHandler.chainProceder = proceder;
    Interceptor.Chain chain = mock(Interceptor.Chain.class);
    testParams = new HashMap();
    testParams.put("param", "value");
    testHeaders = new HashMap();
    testHeaders.put("header", "value");
    httpHandler.turnByTurnHandler().onRequest(chain);

    Map params = new HashMap();
    params.put(NAME_API_KEY, "test-key");
    params.putAll(testParams);
    Map headers = new HashMap();
    headers.put(HEADER_USER_AGENT, USER_AGENT);
    headers.putAll(testHeaders);

    verify(proceder).proceed(chain, params, headers);
  }
}
