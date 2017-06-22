package com.mapzen.android.routing;

import com.mapzen.android.core.MapzenManager;
import com.mapzen.valhalla.Router;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.mapzen.android.TestHelper.getMockContext;
import static com.mapzen.android.core.GenericHttpHandler.HEADER_USER_AGENT;
import static com.mapzen.android.core.GenericHttpHandler.USER_AGENT;
import static com.mapzen.android.routing.MapzenRouterHttpHandler.TurnByTurnHttpHandler.NAME_API_KEY;
import okhttp3.Interceptor;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RouterInitializerTest {
  private RouterInitializer routerInitializer;
  private MapzenRouter router;

  @Before public void setUp() throws Exception {
    MapzenManager.instance(getMockContext()).setApiKey("fake-mapzen-api-key");
    routerInitializer = new RouterInitializer();
    router = mock(MapzenRouter.class);
    when(router.getRouter()).thenReturn(mock(Router.class));
  }

  @After public void tearDown() throws Exception {
    MapzenManager.instance(getMockContext()).setApiKey(null);
  }

  @Test public void initRouter_shouldSetApiKey() throws Exception {
    routerInitializer.initRouter(router, "TEST_API_KEY");
    MapzenRouterHttpHandler.ChainProceder proceder = mock(
        MapzenRouterHttpHandler.ChainProceder.class);
    routerInitializer.getRequestHandler().chainProceder = proceder;
    Interceptor.Chain chain = mock(Interceptor.Chain.class);
    routerInitializer.getRequestHandler().turnByTurnHandler().onRequest(chain);
    Map params = new HashMap();
    params.put(NAME_API_KEY, "TEST_API_KEY");
    Map headers = new HashMap();
    headers.put(HEADER_USER_AGENT, USER_AGENT);
    verify(proceder).proceed(chain, params, headers);
  }

  @Test public void initRouter_shouldSetHttpHandler() throws Exception {
    routerInitializer.initRouter(router, "TEST_API_KEY");
    verify(router.getRouter()).setHttpHandler(
        routerInitializer.getRequestHandler().turnByTurnHandler());
  }
}
