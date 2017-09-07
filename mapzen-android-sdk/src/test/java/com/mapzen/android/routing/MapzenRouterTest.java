package com.mapzen.android.routing;

import com.mapzen.android.core.MapzenManager;
import com.mapzen.valhalla.Route;
import com.mapzen.valhalla.RouteCallback;
import com.mapzen.valhalla.Router;
import com.mapzen.valhalla.ValhallaRouter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import android.content.Context;

import static com.mapzen.android.TestHelper.getMockContext;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MapzenRouterTest {

  MapzenRouter router;

  @Before public void setup() {
    Context context = getMockContext();
    MapzenManager.instance(context).setApiKey("fake-mapzen-api-key");
    router = new MapzenRouter(context);
    router.setValhallaRouter(mock(ValhallaRouter.class));
  }

  @After public void tearDown() throws Exception {
    MapzenManager.instance(getMockContext()).setApiKey(null);
  }

  @Test public void fetch_shouldInvokeInternalRouter() {
    router.fetch();
    verify(router.getRouter()).fetch();
  }

  @Test public void setCallback_shouldInvokeInternalRouter() {
    TestRouteCallback callback = new TestRouteCallback();
    router.setCallback(callback);
    verify(router.getRouter()).setCallback(callback);
  }

  @Test public void setDistanceUnits_shouldInvokeInternalRouter() {
    router.setDistanceUnits(MapzenRouter.DistanceUnits.MILES);
    verify(router.getRouter()).setDistanceUnits(Router.DistanceUnits.MILES);
  }

  @Test public void setLanguage_shouldInvokeInternalRouter() throws Exception {
    router.setLanguage(Router.Language.FR_FR);
    verify(router.getRouter()).setLanguage(Router.Language.FR_FR);
  }

  @Test public void setBiking_shouldInvokeInternalRouter() {
    router.setBiking();
    verify(router.getRouter()).setBiking();
  }

  @Test public void setDriving_shouldInvokeInternalRouter() {
    router.setDriving();
    verify(router.getRouter()).setDriving();
  }

  @Test public void setWalking_shouldInvokeInternalRouter() {
    router.setBiking();
    verify(router.getRouter()).setBiking();
  }

  @Test public void setMultimodal_shouldInvokeInternalRouter() {
    router.setMultimodal();
    verify(router.getRouter()).setMultimodal();
  }

  @Test public void setLocation_shouldInvokeInternalRouter() {
    double[] point = {70.0, 30.0};
    router.setLocation(point);
    verify(router.getRouter()).setLocation(point);
    int heading = 1;
    router.setLocation(point, heading);
    verify(router.getRouter()).setLocation(point, heading);
    String name = "Num Pang Sandwich";
    String street = "Broadway";
    String city = "New York";
    String state = "New York";
    router.setLocation(point, name, street, city, state);
    verify(router.getRouter()).setLocation(point, name, street, city, state);
  }

  @Test public void distanceUnitsToString_shouldReturnMiles() throws Exception {
    assertThat(MapzenRouter.DistanceUnits.MILES.toString()).isEqualTo("miles");
  }

  @Test public void distanceUnitsToString_shouldReturnKilometers() throws Exception {
    assertThat(MapzenRouter.DistanceUnits.KILOMETERS.toString()).isEqualTo("kilometers");
  }

  @Test public void setHttpHandler_shouldCallInternalRouter() throws Exception {
    MapzenRouterHttpHandler mapzenRouterHandler = mock(MapzenRouterHttpHandler.class);
    MapzenRouterHttpHandler.TurnByTurnHttpHandler handler = mock(
        MapzenRouterHttpHandler.TurnByTurnHttpHandler.class);
    when(mapzenRouterHandler.turnByTurnHandler()).thenReturn(handler);
    router.setHttpHandler(mapzenRouterHandler);
    verify(router.getRouter()).setHttpHandler(handler);
  }

  class TestRouteCallback implements RouteCallback {

    @Override public void success(Route route) {
    }

    @Override public void failure(int i) {
    }
  }
}
