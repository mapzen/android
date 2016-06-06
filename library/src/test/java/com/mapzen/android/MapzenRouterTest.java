package com.mapzen.android;

import com.mapzen.valhalla.HttpHandler;
import com.mapzen.valhalla.Route;
import com.mapzen.valhalla.RouteCallback;
import com.mapzen.valhalla.Router;
import com.mapzen.valhalla.ValhallaRouter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;

import android.content.Context;
import android.content.res.Resources;

import static com.mapzen.android.TestHelper.getMockContext;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MapzenRouterTest {

  MapzenRouter router;

  @Before
  public void setup() {
    Context context = getMockContext();
    Resources resources = context.getResources();
    Mockito.when(resources.getIdentifier("turn_by_turn_key", "string", null)).thenReturn(101);
    Mockito.when(resources.getString(101)).thenReturn("TEST_KEY");
    router = new MapzenRouter(context);
  }

  @Test
  public void routerShouldHaveEndpoint() {
    ValhallaRouter valhallaRouter = (ValhallaRouter) router.getRouter();
    HttpHandler httpHandler = (HttpHandler) Whitebox.getInternalState(valhallaRouter,
        "httpHandler");
    String endpoint = (String) Whitebox.getInternalState(httpHandler, "endpoint");
    assertThat(endpoint).isEqualTo("https://valhalla.mapzen.com/");
  }

  @Test
  public void routerShouldHaveApiKey() {
    ValhallaRouter valhallaRouter = (ValhallaRouter) router.getRouter();
    HttpHandler httpHandler = (HttpHandler) Whitebox.getInternalState(valhallaRouter,
        "httpHandler");
    String apiKey = (String) Whitebox.getInternalState(httpHandler, "apiKey");
    assertThat(apiKey).isEqualTo("TEST_KEY");
  }

  @Test
  public void fetch_shouldInvokeInternalRouter() {
    Whitebox.setInternalState(router, "internalRouter", mock(ValhallaRouter.class));
    router.fetch();
    verify(router.getRouter()).fetch();
  }

  @Test
  public void setCallback_shouldInvokeInternalRouter() {
    Whitebox.setInternalState(router, "internalRouter", mock(ValhallaRouter.class));
    TestRouteCallback callback = new TestRouteCallback();
    router.setCallback(callback);
    verify(router.getRouter()).setCallback(callback);
  }

  @Test
  public void setDistanceUnits_shouldInvokeInternalRouter() {
    Whitebox.setInternalState(router, "internalRouter", mock(ValhallaRouter.class));
    router.setDistanceUnits(MapzenRouter.DistanceUnits.MILES);
    verify(router.getRouter()).setDistanceUnits(Router.DistanceUnits.MILES);
  }

  @Test
  public void setBiking_shouldInvokeInternalRouter() {
    Whitebox.setInternalState(router, "internalRouter", mock(ValhallaRouter.class));
    router.setBiking();
    verify(router.getRouter()).setBiking();
  }

  @Test
  public void setDriving_shouldInvokeInternalRouter() {
    Whitebox.setInternalState(router, "internalRouter", mock(ValhallaRouter.class));
    router.setDriving();
    verify(router.getRouter()).setDriving();
  }

  @Test
  public void setWalking_shouldInvokeInternalRouter() {
    Whitebox.setInternalState(router, "internalRouter", mock(ValhallaRouter.class));
    router.setBiking();
    verify(router.getRouter()).setBiking();
  }

  @Test
  public void setLocation_shouldInvokeInternalRouter() {
    Whitebox.setInternalState(router, "internalRouter", mock(ValhallaRouter.class));
    double[] point = {70.0, 30.0};
    router.setLocation(point);
    verify(router.getRouter()).setLocation(point);
    float heading = 1.5f;
    router.setLocation(point, heading);
    verify(router.getRouter()).setLocation(point, heading);
    String name = "Num Pang Sandwich";
    String street = "Broadway";
    String city = "New York";
    String state = "New York";
    router.setLocation(point, name, street, city, state);
    verify(router.getRouter()).setLocation(point, name, street, city, state);
  }

  class TestRouteCallback implements RouteCallback {

    @Override public void success(Route route) {

    }

    @Override public void failure(int i) {

    }
  }
}
