package com.mapzen.android;

import com.mapzen.valhalla.RouteCallback;
import com.mapzen.valhalla.Router;
import com.mapzen.valhalla.ValhallaRouter;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import java.util.HashMap;

/**
 * Main class for interaction with Mapzen's turn-by-turn service.
 */
public class MapzenRouter {

  private static final String API_KEY_RES_NAME = "turn_by_turn_key";
  private static final String API_KEY_RES_TYPE = "string";
  private static final String TAG = MapzenRouter.class.getSimpleName();

  private Router internalRouter = new ValhallaRouter();
  private Context context;

  private static final HashMap<DistanceUnits, Router.DistanceUnits> UNITS_TO_ROUTER_UNITS =
      new HashMap<>();
  static {
    UNITS_TO_ROUTER_UNITS.put(DistanceUnits.MILES, Router.DistanceUnits.MILES);
    UNITS_TO_ROUTER_UNITS.put(DistanceUnits.KILOMETERS, Router.DistanceUnits.KILOMETERS);
  }

  /**
   * Creates a new {@link MapzenRouter}.
   * @param context
   */
  public MapzenRouter(Context context) {
    this.context = context;
    initializeRouterKey();
    initializeRouterUrl();
  }

  private void initializeRouterKey() {
    final String packageName = context.getPackageName();
    Resources res = context.getResources();
    try {
      final int apiKeyId = res.getIdentifier(API_KEY_RES_NAME, API_KEY_RES_TYPE, packageName);
      final String apiKey = res.getString(apiKeyId);
      internalRouter.setApiKey(apiKey);
    } catch (Resources.NotFoundException e) {
      Log.e(TAG, e.getLocalizedMessage());
    }
  }

  private void initializeRouterUrl() {
    internalRouter.setEndpoint(ValhallaRouter.DEFAULT_URL);
  }

  /**
   * Set's the turn-by-turn api key.
   * @param key
   */
  public MapzenRouter setApiKey(String key) {
    internalRouter.setApiKey(key);
    return this;
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
  public MapzenRouter setLocation(double[] point, float heading) {
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

  public Router getRouter() {
    return internalRouter;
  }

  /**
   * Units of distance.
   */
  public enum DistanceUnits {
    MILES,
    KILOMETERS
  }

}
