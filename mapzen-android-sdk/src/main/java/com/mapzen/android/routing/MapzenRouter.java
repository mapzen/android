package com.mapzen.android.routing;

import com.mapzen.android.core.DI;
import com.mapzen.valhalla.RouteCallback;
import com.mapzen.valhalla.Router;
import com.mapzen.valhalla.ValhallaRouter;

import android.content.Context;
import android.support.annotation.VisibleForTesting;

import javax.inject.Inject;

import static com.mapzen.android.routing.Converter.UNITS_TO_ROUTER_UNITS;

/**
 * Main class for interaction with Mapzen's turn-by-turn service.
 */
public class MapzenRouter {

  private Router internalRouter = new ValhallaRouter();

  @Inject RouterInitializer routerInitializer;

  /**
   * Creates a new {@link MapzenRouter} with api key set from mapzen.xml.
   */
  public MapzenRouter(Context context) {
    initDI(context);
    routerInitializer.initRouter(this, context);
  }

  private void initDI(Context context) {
    DI.init(context);
    DI.component().inject(this);
  }

  /**
   * Fetch a route for the given configuration.
   */
  public void fetch() {
    internalRouter.fetch();
  }

  /**
   * Set the callback to be invoked after calling {@link MapzenRouter#fetch()}.
   * @param callback
   * @return
   */
  public MapzenRouter setCallback(RouteCallback callback) {
    internalRouter.setCallback(callback);
    return this;
  }

  /**
   * Set the distance units on the router.
   * @param units
   * @return
   */
  public MapzenRouter setDistanceUnits(DistanceUnits units) {
    internalRouter.setDistanceUnits(UNITS_TO_ROUTER_UNITS.get(units));
    return this;
  }

  /**
   * Set route narrative language.
   *
   * @param language language to be used for written and verbal instructions. A list of supported
   * languages can be found at:
   * <p />
   * https://github.com/valhalla/valhalla-docs/blob/master/api-reference.md#directions-options.
   */
  public MapzenRouter setLanguage(Router.Language language) {
    internalRouter.setLanguage(language);
    return this;
  }

  /**
   * The router can fetch different types of directions. Set the type to be biking.
   * @return
   */
  public MapzenRouter setBiking() {
    internalRouter.setBiking();
    return this;
  }

  /**
   * The router can fetch different types of directions. Set the type to be driving.
   * @return
   */
  public MapzenRouter setDriving() {
    internalRouter.setDriving();
    return this;
  }

  /**
   * The router can fetch different types of directions. Set the type to be walking.
   * @return
   */
  public MapzenRouter setWalking() {
    internalRouter.setWalking();
    return this;
  }

  /**
   * The router can fetch different types of directions. Set the type to be multimodal.
   * @return
   */
  public MapzenRouter setMultimodal() {
    internalRouter.setMultimodal();
    return this;
  }

  /**
   * Adds a location to the route.
   * @param point
   * @return
   */
  public MapzenRouter setLocation(double[] point) {
    internalRouter.setLocation(point);
    return this;
  }

  /**
   * Adds a location and heading to the route.
   * @param point
   * @return
   */
  public MapzenRouter setLocation(double[] point, int heading) {
    internalRouter.setLocation(point, heading);
    return this;
  }

  /**
   * Adds a location, name, street, city, and state to the route.
   * @param point
   * @return
   */
  public MapzenRouter setLocation(double[] point, String name, String street, String city,
      String state) {
    internalRouter.setLocation(point, name, street, city, state);
    return this;
  }

  /**
   * Remove all locations from the route.
   * @return
   */
  public MapzenRouter clearLocations() {
    internalRouter.clearLocations();
    return this;
  }

  /**
   * Sets the router's http handler for adding custom headers and parameters to
   * requests.
   * @param handler
   */
  public void setHttpHandler(MapzenRouterHttpHandler handler) {
    internalRouter.setHttpHandler(handler.turnByTurnHandler());
  }

  public Router getRouter() {
    return internalRouter;
  }

  /**
   * Units of distance.
   */
  public enum DistanceUnits {
    MILES ("miles"),
    KILOMETERS ("kilometers");

    private final String name;

    /**
     * Construct {@code DistanceUnits} with given string value.
     */
    DistanceUnits(String s) {
      name = s;
    }

    /**
     * Returns string value.
     */
    public String toString() {
      return name;
    }
  }

  @VisibleForTesting
  public void setValhallaRouter(Router router) {
    internalRouter = router;
  }
}
