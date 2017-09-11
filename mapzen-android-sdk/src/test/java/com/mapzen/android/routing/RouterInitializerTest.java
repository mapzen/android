package com.mapzen.android.routing;

import com.mapzen.android.core.MapzenManager;
import com.mapzen.valhalla.Router;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.mapzen.android.TestHelper.getMockContext;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RouterInitializerTest {
  private RouterInitializer routerInitializer;
  private MapzenRouter router;

  @Before public void setUp() throws Exception {
    MapzenManager.instance(getMockContext()).setApiKey("api-key");
    routerInitializer = new RouterInitializer(getMockContext());
    router = mock(MapzenRouter.class);
    when(router.getRouter()).thenReturn(mock(Router.class));
  }

  @After public void tearDown() throws Exception {
    MapzenManager.instance(getMockContext()).setApiKey(null);
  }

  @Test public void initRouter_shouldSetHttpHandler() throws Exception {
    routerInitializer.initRouter(router);
    verify(router.getRouter()).setHttpHandler(
        routerInitializer.getRequestHandler().turnByTurnHandler());
  }
}
