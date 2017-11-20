package com.mapzen.android.routing;

import com.mapzen.android.core.DI;
import com.mapzen.android.core.http.CallRequest;
import com.mapzen.android.core.http.Request;
import com.mapzen.valhalla.RouteCallback;
import com.mapzen.valhalla.Router;
import com.mapzen.valhalla.ValhallaRouter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
  public MapzenRouter(@NonNull Context context) {
    initDI(context);
    routerInitializer.initRouter(this);
  }

  private void initDI(Context context) {
    DI.init(context);
    DI.component().inject(this);
  }

  /**
   * Fetch a route for the given configuration.
   */
  public Request fetch() {
    return new CallRequest(internalRouter.fetch());
  }

  /**
   * Set the callback to be invoked after calling {@link MapzenRouter#fetch()}.
   * @param callback
   * @return
   */
  @NonNull public MapzenRouter setCallback(@Nullable RouteCallback callback) {
    internalRouter.setCallback(callback);
    return this;
  }

  /**
   * Set the distance units on the router.
   * @param units
   * @return
   */
  @NonNull public MapzenRouter setDistanceUnits(@NonNull DistanceUnits units) {
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
  @NonNull public MapzenRouter setLanguage(Router.Language language) {
    internalRouter.setLanguage(language);
    return this;
  }

  /**
   * The router can fetch different types of directions. Set the type to be biking.
   * @return
   */
  @NonNull public MapzenRouter setBiking() {
    internalRouter.setBiking();
    return this;
  }

  /**
   * The router can fetch different types of directions. Set the type to be driving.
   * @return
   */
  @NonNull public MapzenRouter setDriving() {
    internalRouter.setDriving();
    return this;
  }

  /**
   * The router can fetch different types of directions. Set the type to be walking.
   * @return
   */
  @NonNull public MapzenRouter setWalking() {
    internalRouter.setWalking();
    return this;
  }

  /**
   * The router can fetch different types of directions. Set the type to be multimodal.
   * @return
   */
  @NonNull public MapzenRouter setMultimodal() {
    internalRouter.setMultimodal();
    return this;
  }

  /**
   * Adds a location to the route.
   * @param point
   * @return
   */
  @NonNull public MapzenRouter setLocation(double[] point) {
    internalRouter.setLocation(point);
    return this;
  }

  /**
   * Adds a location and heading to the route.
   * @param point
   * @return
   */
  @NonNull public MapzenRouter setLocation(double[] point, int heading) {
    internalRouter.setLocation(point, heading);
    return this;
  }

  /**
   * Adds a location, name, street, city, and state to the route.
   * @param point
   * @return
   */
  @NonNull public MapzenRouter setLocation(double[] point, @Nullable String name,
      @Nullable String street, @Nullable String city, @Nullable String state) {
    internalRouter.setLocation(point, name, street, city, state);
    return this;
  }

  /**
   * Remove all locations from the route.
   * @return
   */
  @NonNull public MapzenRouter clearLocations() {
    internalRouter.clearLocations();
    return this;
  }

  /**
   * Sets the router's http handler for adding custom headers and parameters to
   * requests.
   * @param handler
   */
  public void setHttpHandler(@Nullable MapzenRouterHttpHandler handler) {
    internalRouter.setHttpHandler(handler.turnByTurnHandler());
  }

  @NonNull public Router getRouter() {
    return internalRouter;
  }

  /**
   * Units of distance.
   */
  public enum DistanceUnits {
    MILES("miles"),
    KILOMETERS("kilometers");

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
    @NonNull public String toString() {
      return name;
    }
  }

  @VisibleForTesting
  public void setValhallaRouter(Router router) {
    internalRouter = router;
  }
}
