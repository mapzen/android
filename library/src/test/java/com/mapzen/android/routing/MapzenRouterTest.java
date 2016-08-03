package com.mapzen.android.routing;

import com.mapzen.valhalla.Route;
import com.mapzen.valhalla.RouteCallback;
import com.mapzen.valhalla.Router;
import com.mapzen.valhalla.ValhallaRouter;

import org.junit.Before;
import org.junit.Test;

import android.content.Context;

import static com.mapzen.android.TestHelper.getMockContext;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MapzenRouterTest {

  MapzenRouter router;

  @Before public void setup() {
    Context context = getMockContext();
    router = new MapzenRouter(context);
    router.setValhallaRouter(mock(ValhallaRouter.class));
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

  @Test public void distanceUnitsToString_shouldReturnMiles() throws Exception {
    assertThat(MapzenRouter.DistanceUnits.MILES.toString()).isEqualTo("miles");
  }

  @Test public void distanceUnitsToString_shouldReturnKilometers() throws Exception {
    assertThat(MapzenRouter.DistanceUnits.KILOMETERS.toString()).isEqualTo("kilometers");
  }

  class TestRouteCallback implements RouteCallback {

    @Override public void success(Route route) {
    }

    @Override public void failure(int i) {
    }
  }
}
