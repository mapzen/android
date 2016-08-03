package com.mapzen.android.graphics;

import com.mapzen.android.R;
import com.mapzen.android.graphics.model.Marker;
import com.mapzen.android.graphics.model.Polygon;
import com.mapzen.android.graphics.model.Polyline;
import com.mapzen.android.location.LocationFactory;
import com.mapzen.android.lost.api.LocationListener;
import com.mapzen.android.lost.api.LocationRequest;
import com.mapzen.android.lost.api.LocationServices;
import com.mapzen.android.lost.api.LostApiClient;
import com.mapzen.tangram.LngLat;
import com.mapzen.tangram.MapController;
import com.mapzen.tangram.MapData;
import com.mapzen.tangram.TouchInput;

import org.jetbrains.annotations.NotNull;

import android.location.Location;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Adds functionality to {@link MapController} map by way of {@link LostApiClient}.
 */
public class OverlayManager implements TouchInput.PanResponder {

  private static final int LOCATION_REQUEST_INTERVAL_MILLIS = 5000;
  private static final int LOCATION_REQUEST_DISPLACEMENT_MILLIS = 5000;
  private static final int ANIMATION_DURATION_MILLIS = 500;
  private static final float DEFAULT_ZOOM = 16f;

  //public static final String NAME_CURRENT_LOCATION = "mz_current_location";
  //public static final String NAME_POLYLINE = "mz_default_line";
  //public static final String NAME_POLYGON = "mz_default_polygon";
  //public static final String NAME_MARKER = "mz_default_point";
  //public static final String NAME_START_PIN = "mz_route_start";
  //public static final String NAME_END_PIN = "mz_route_destination";
  //public static final String NAME_DROPPED_PIN = "mz_dropped_pin";
  //public static final String NAME_SEARCH_RESULT_PIN = "mz_search_result";
  //public static final String NAME_ROUTE_PIN = "mz_route_location";
  //public static final String NAME_ROUTE_LINE = "mz_route_line";
  //public static final String NAME_TRANSIT_ROUTE_LINE = "mz_route_line_transit";
  //public static final String NAME_STATION_ICON = "mz_route_transit_stop";

  private static final String PROP_STATE = "state";
  private static final String PROP_STATE_ACTIVE = "active";
  private static final String PROP_STATE_INACTIVE = "inactive";
  private static final String PROP_SEARCH_INDEX = "searchIndex";
  private static final String PROP_TYPE = "type";
  private static final String PROP_POINT = "point";
  private static final String PROP_LINE = "line";
  private static final String PROP_COLOR = "color";

  private static final int MIN_COORDINATES_POLYGON = 2;
  private static final int MIN_COORDINATES_POLYLINE = 2;

  private static final int INDEX_NONE = -1;

  /**
   * For interaction with the map.
   */
  private MapController mapController;
  /**
   * For interaction with location services.
   */
  private LostApiClient lostApiClient;
  /**
   * For interaction with map ui objects such as the "find me" button.
   */
  private MapView mapView;
  /**
   * Object to hold current location information to be displayed on map.
   */
  private MapData currentLocationMapData;
  /**
   * Should we show the current location icon and find me icon.
   */
  private boolean myLocationEnabled;

  /**
   * Receives location updates for {@link LostApiClient}.
   */
  LocationListener locationListener = new LocationListener() {
    @Override public void onLocationChanged(Location location) {
      handleLocationChange(location);
    }
  };

  View.OnClickListener findMeExternalClickListener;

  private MapData polylineMapData;
  private MapData polygonMapData;
  private MapData markerMapData;
  private MapData startPinData;
  private MapData endPinData;
  private MapData droppedPinData;
  private MapData searchResultPinData;
  private MapData routePinData;
  private MapData routeLineData;
  private MapData transitRouteLineData;
  private MapData stationIconData;

  private MapDataManager mapDataManager;
  private MapStateManager mapStateManager;

  /**
   * Create a new {@link OverlayManager} object for handling functionality between map and
   * location services using the {@link LocationFactory}'s shared {@link LostApiClient}.
   */
  OverlayManager(MapView mapView, MapController mapController, MapDataManager mapDataManager,
      MapStateManager mapStateManager) {
    this(mapView, mapController, mapDataManager, mapStateManager, LocationFactory.sharedClient(
        mapView.getContext()));
  }

  /**
   * Create a new {@link OverlayManager} object for handling functionality between map and
   * location services.
   */
  OverlayManager(MapView mapView, MapController mapController, MapDataManager mapDataManager,
      MapStateManager mapStateManager, LostApiClient lostApiClient) {
    this.mapView = mapView;
    this.mapController = mapController;
    this.mapDataManager = mapDataManager;
    this.mapStateManager = mapStateManager;
    this.lostApiClient = lostApiClient;
  }

  /**
   * Track the user's current location by displaying an icon on the map and centering the map.
   */
  public void setMyLocationEnabled(boolean enabled) {
    setMyLocationEnabled(enabled, true);
  }

  /**
   * Track the user's current location by displaying an icon on the map and centering the map.
   * Optionally persists the map data to the {@link MapDataManager}.
   */
  public void setMyLocationEnabled(boolean enabled, boolean persistMapData) {
    if (persistMapData) {
      mapDataManager.addMapData(new PersistableMapData(enabled));
    }
    myLocationEnabled = enabled;
    if (enabled) {
      addCurrentLocationMapData();
    } else {
      removeCurrentLocationMapData();
    }

    handleMyLocationEnabledChanged();
  }

  /**
   * Are we tracking the user's current location.
   */
  public boolean isMyLocationEnabled() {
    return myLocationEnabled;
  }

  /**
   * Set an external click listener to be invoked after the internal listener.
   */
  public void setFindMeOnClickListener(View.OnClickListener listener) {
    findMeExternalClickListener = listener;
  }

  /**
   * Adds a polyline to the map.
   */
  public MapData addPolyline(Polyline polyline) {
    return addPolyline(polyline, true);
  }

  /**
   * Adds a polyline to the map. Optionally persists the map data to the {@link MapDataManager}.
   * @param polyline
   * @param persistMapData
   * @return
   */
  public MapData addPolyline(Polyline polyline, boolean persistMapData) {
    if (polyline == null) {
      throw new IllegalArgumentException(
          "Must provide marker when calling " + "MapData#addPolyline");
    }
    if (polyline.getCoordinates().size() < MIN_COORDINATES_POLYLINE) {
      throw new IllegalArgumentException("Polyine must contain at least 2 points");
    }

    if (persistMapData) {
      mapDataManager.addMapData(new PersistableMapData(polyline));
    }

    if (polylineMapData == null) {
      initPolylineMapData();
    }
    return addPolylineToPolylineMapData(polyline.getCoordinates());
  }

  /**
   * Removes polyline overlay from map.
   */
  public void removePolyline() {
    mapDataManager.removeMapData(DataLayerType.POLYLINE);
    if (polylineMapData != null) {
      polylineMapData.clear();
    }
  }

  /**
   * Adds a polygon to the map.
   */
  public MapData addPolygon(Polygon polygon) {
    return addPolygon(polygon, true);
  }

  /**
   * Adds a polygon to the map. Optionally persists the map data to the {@link MapDataManager}.
   * @param polygon
   * @param persistMapData
   * @return
   */
  public MapData addPolygon(Polygon polygon, boolean persistMapData) {
    if (polygon == null) {
      throw new IllegalArgumentException(
          "Must provide marker when calling " + "MapData#addPolygon");
    }
    if (polygon.getCoordinates().size() < MIN_COORDINATES_POLYGON) {
      throw new IllegalArgumentException("Polygon must contain at least 2 points");
    }

    if (polygonMapData == null) {
      initPolygonMapData();
    }

    if (persistMapData) {
      mapDataManager.addMapData(new PersistableMapData(polygon));
    }

    List<LngLat> coords = new ArrayList<>();
    coords.addAll(polygon.getCoordinates());
    LngLat first = polygon.getCoordinates().get(0);
    int size = polygon.getCoordinates().size();
    LngLat last = polygon.getCoordinates().get(size - 1);
    boolean closed = first.equals(last);
    if (!closed) {
      coords.add(first);
    }
    List allCoords = new ArrayList();
    allCoords.add(coords);

    return addPolygonToPolygonMapData(allCoords);
  }

  /**
   * Removes polygon overlay from map.
   */
  public void removePolygon() {
    mapDataManager.removeMapData(DataLayerType.POLYGON);
    if (polygonMapData != null) {
      polygonMapData.clear();
    }
  }

  /**
   * Add a point to the map for the marker.
   */
  public MapData addMarker(Marker marker) {
    return addMarker(marker, true);
  }

  /**
   * Add a point to the map for the marker. Optionally persists the map data to the
   * {@link MapDataManager}.
   */
  public MapData addMarker(Marker marker, boolean persistMapData) {
    if (marker == null) {
      throw new IllegalArgumentException("Must provide marker when calling " + "MapData#addMarker");
    }

    if (persistMapData) {
      mapDataManager.addMapData(new PersistableMapData(marker));
    }

    if (markerMapData == null) {
      initMarkerMapData();
    }
    return addPointToMarkerMapData(marker);
  }

  /**
   * Removes marker overlay from map.
   */
  public void removeMarker() {
    mapDataManager.removeMapData(DataLayerType.MARKER);
    if (markerMapData != null) {
      markerMapData.clear();
    }
  }

  /**
   * Draws two pins on the map. The start pin is active and the end pin is inactive.
   */
  public void drawRoutePins(LngLat start, LngLat end) {
    drawRoutePins(start, end, true);
  }

  /**
   * Draws two pins on the map. The start pin is active and the end pin is inactive. Optionally
   * persists the map data to the {@link MapDataManager}.
   */
  public void drawRoutePins(LngLat start, LngLat end, boolean persistMapData) {
    if (startPinData == null) {
      startPinData = mapController.addDataLayer(DataLayerType.ROUTE_START_PIN.toString());
    }
    if (endPinData == null) {
      endPinData = mapController.addDataLayer(DataLayerType.ROUTE_END_PIN.toString());
    }
    if (persistMapData) {
      mapDataManager.addMapData(new PersistableMapData(start, end));
    }
    startPinData.addPoint(start, null);
    endPinData.addPoint(end, null);
    mapController.requestRender();
  }

  /**
   * Clears the start and end pins from the map.
   */
  public void clearRoutePins() {
    mapDataManager.removeMapData(DataLayerType.ROUTE_START_PIN);

    if (startPinData != null) {
      startPinData.clear();
    }
    if (endPinData != null) {
      endPinData.clear();
    }
  }

  /**
   * Draws a dropped pin on the map at the point supplied.
   */
  public void drawDroppedPin(LngLat point) {
    drawDroppedPin(point, true);
  }

  /**
   * Draws a dropped pin on the map at the point supplied. Optionally persists the map data to
   * the {@link MapDataManager}.
   */
  public void drawDroppedPin(LngLat point, boolean persistMapData) {
    if (persistMapData) {
      mapDataManager.addMapData(new PersistableMapData(point, DataLayerType.DROPPED_PIN));
    }
    if (droppedPinData == null) {
      droppedPinData = mapController.addDataLayer(DataLayerType.DROPPED_PIN.toString());
    }
    HashMap<String, String> properties = new HashMap<>();
    properties.put(PROP_STATE, PROP_STATE_ACTIVE);
    droppedPinData.addPoint(point, properties);
    mapController.requestRender();
  }

  /**
   * Clears the dropped pin from the map.
   */
  public void clearDroppedPin() {
    mapDataManager.removeMapData(DataLayerType.DROPPED_PIN);

    if (droppedPinData != null) {
      droppedPinData.clear();
    }
  }

  /**
   * Draws a search result on the map at the point supplied.
   */
  public void drawSearchResult(LngLat point, boolean active) {
    drawSearchResult(point, active, INDEX_NONE);
  }

  /**
   * Draws a search result at the point supplied and displays it as active/inactive. If an index
   * is supplied, it adds property {@code PROP_SEARCH_INDEX} when adding it to the map.
   */
  public void drawSearchResult(LngLat point, boolean active, int index) {
    drawSearchResult(point, active, index, true);
  }

  /**
   * Draws a search result at the point supplied and displays it as active/inactive. If an index
   * is supplied, it adds property {@code PROP_SEARCH_INDEX} when adding it to the map. Optionally
   * persists the map data to the {@link MapDataManager}.
   */
  public void drawSearchResult(LngLat point, boolean active, int index, boolean persistMapData) {
    if (persistMapData) {
      mapDataManager.addMapData(new PersistableMapData(point, active, index));
    }

    if (searchResultPinData == null) {
      searchResultPinData = mapController.addDataLayer(DataLayerType.SEARCH_RESULT_PIN.toString());
    }
    HashMap<String, String> properties = new HashMap<>();
    if (index != INDEX_NONE) {
      properties.put(PROP_SEARCH_INDEX, String.valueOf(index));
    }
    if (active) {
      properties.put(PROP_STATE, PROP_STATE_ACTIVE);
    } else {
      properties.put(PROP_STATE, PROP_STATE_INACTIVE);
    }
    searchResultPinData.addPoint(point, properties);
    mapController.requestRender();
  }

  /**
   * Clears search result from the map.
   */
  public void clearSearchResult() {
    mapDataManager.removeMapData(DataLayerType.SEARCH_RESULT_PIN);

    if (searchResultPinData != null) {
      searchResultPinData.clear();
    }
  }

  /**
   * Draws route pin at the point supplied.
   */
  public void drawRouteLocationMarker(LngLat point) {
    drawRouteLocationMarker(point, true);
  }

  /**
   * Draws route pin at the point supplied. Optionally persists the map data to the
   * {@link MapDataManager}.
   */
  public void drawRouteLocationMarker(LngLat point, boolean persistMapData) {
    if (persistMapData) {
      mapDataManager.addMapData(new PersistableMapData(point, DataLayerType.ROUTE_PIN));
    }
    if (routePinData == null) {
      routePinData = mapController.addDataLayer(DataLayerType.ROUTE_PIN.toString());
    }
    HashMap<String, String> properties = new HashMap<>();
    properties.put(PROP_TYPE, PROP_POINT);
    routePinData.addPoint(point, properties);
    mapController.requestRender();
  }

  /**
   * Clears route pin from the map.
   */
  public void clearRouteLocationMarker() {
    mapDataManager.removeMapData(DataLayerType.ROUTE_PIN);

    if (routePinData != null) {
      routePinData.clear();
    }
  }

  /**
   * Draws route line on the map for the points supplied.
   */
  public void drawRouteLine(List<LngLat> points) {
    drawRouteLine(points, true);
  }

  /**
   * Draws route line on the map for the points supplied. Optionally persists the map data to the
   * {@link MapDataManager}.
   */
  public void drawRouteLine(List<LngLat> points, boolean persistMapData) {
    if (persistMapData) {
      mapDataManager.addMapData(new PersistableMapData(points));
    }

    if (routeLineData == null) {
      routeLineData = mapController.addDataLayer(DataLayerType.ROUTE_LINE.toString());
    }
    HashMap<String, String> properties = new HashMap<>();
    properties.put(PROP_TYPE, PROP_LINE);
    routeLineData.addPolyline(points, properties);
    mapController.requestRender();
  }

  /**
   * Clears route line from the map.
   */
  public void clearRouteLine() {
    mapDataManager.removeMapData(DataLayerType.ROUTE_LINE);

    if (routeLineData != null) {
      routeLineData.clear();
    }
  }

  /**
   * Draws route line on the map for the points supplied. Also draws station icons for each point.
   */
  public void drawTransitRouteLine(@NotNull List<LngLat> points, @Nullable List<LngLat> stations,
      @NotNull String colorHex) {
    drawTransitRouteLine(points, stations, colorHex, true);
  }

  /**
   * Draws route line on the map for the points supplied. Also draws station icons for each point.
   * Optionally persists the map data to the {@link MapDataManager}.
   */
  public void drawTransitRouteLine(@NotNull List<LngLat> points, @Nullable List<LngLat> stations,
      @NotNull String colorHex, boolean persistMapData) {
    if (persistMapData) {
      mapDataManager.addMapData(new PersistableMapData(points, stations, colorHex));
    }
    if (transitRouteLineData == null) {
      transitRouteLineData = mapController.addDataLayer(
          DataLayerType.TRANSIT_ROUTE_LINE.toString());
    }
    HashMap<String, String> properties = new HashMap<>();
    properties.put(PROP_TYPE, PROP_LINE);
    properties.put(PROP_COLOR, colorHex);

    transitRouteLineData.addPolyline(points, properties);
    mapController.requestRender();

    if (stations != null) {
      if (stationIconData == null) {
        stationIconData = mapController.addDataLayer(
            DataLayerType.TRANSIT_ROUTE_LINE_STATION_ICON.toString());
      }
      for (LngLat station : stations) {
        stationIconData.addPoint(station, null);
      }
    }
  }

  /**
   * Clears transit route line from the map.
   */
  public void clearTransitRouteLine() {
    mapDataManager.removeMapData(DataLayerType.TRANSIT_ROUTE_LINE_STATION_ICON);

    if (transitRouteLineData != null) {
      transitRouteLineData.clear();
    }
    if (stationIconData != null) {
      stationIconData.clear();
    }
  }

  /**
   * By default all {@link MapData} objects are removed from the map when it is destroyed. To
   * persist this data such as in the case of orientation changes, use this method.
   * @param persistOnRecreation persist {@link MapData} across orientation changes
   */
  public void setPersistMapData(boolean persistOnRecreation) {
    mapDataManager.setPersistMapData(persistOnRecreation);
  }

  /**
   * Restores any {@link MapData} objects that were previously displayed on the map.
   */
  public void restoreMapData() {
    if (!mapDataManager.getPersistMapData()) {
      return;
    }
    for (PersistableMapData persistableMapData : mapDataManager.getMapData()) {
      switch (persistableMapData.getDataLayerType()) {
        case POLYLINE:
          addPolyline(persistableMapData.getPolyline(), false);
          break;
        case POLYGON:
          addPolygon(persistableMapData.getPolygon(), false);
          break;
        case MARKER:
          addMarker(persistableMapData.getMarker(), false);
          break;
        case ROUTE_START_PIN:
          drawRoutePins(persistableMapData.getStart(), persistableMapData.getEnd(), false);
          break;
        case DROPPED_PIN:
          drawDroppedPin(persistableMapData.getPoint(), false);
          break;
        case SEARCH_RESULT_PIN:
          drawSearchResult(persistableMapData.getPoint(), persistableMapData.getIsActive(),
              persistableMapData.getIndex(), false);
          break;
        case ROUTE_PIN:
          drawRouteLocationMarker(persistableMapData.getPoint(), false);
          break;
        case ROUTE_LINE:
          drawRouteLine(persistableMapData.getPoints(), false);
          break;
        case TRANSIT_ROUTE_LINE_STATION_ICON:
          drawTransitRouteLine(persistableMapData.getPoints(), persistableMapData.getStations(),
              persistableMapData.getHexColor(), false);
          break;
        case CURRENT_LOCATION:
          setMyLocationEnabled(persistableMapData.getLocationEnabled(), false);
        default:
          break;
      }
    }
  }

  private void addCurrentLocationMapData() {
    if (currentLocationMapData == null) {
      currentLocationMapData = mapController.addDataLayer(
          DataLayerType.CURRENT_LOCATION.toString());
    }
  }

  private void removeCurrentLocationMapData() {
    if (currentLocationMapData != null) {
      currentLocationMapData.clear();
      currentLocationMapData.remove();
      currentLocationMapData = null;
    }
  }

  private void handleMyLocationEnabledChanged() {
    if (myLocationEnabled) {
      lostApiClient.connect();
      showLastKnownLocation();
      showFindMe();
      requestLocationUpdates();
    } else {
      hideFindMe();
      removeLocationUpdates();
    }
  }

  private void showFindMe() {
    final ImageButton button = mapView.showFindMe();
    button.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        toggleActivation(button);
        centerMap();
        if (findMeExternalClickListener != null) {
          findMeExternalClickListener.onClick(v);
        }
      }
    });
  }

  private void toggleActivation(ImageButton button) {
    button.setActivated(!button.isActivated());
  }

  private void hideFindMe() {
    mapView.hideFindMe();
  }

  private void centerMap() {
    showLastKnownLocation();
    centerMapOnLastKnownLocation();
  }

  private void showLastKnownLocation() {
    final Location current = LocationServices.FusedLocationApi.getLastLocation();
    if (current != null) {
      updateCurrentLocationMapData(current);
    }
  }

  private void centerMapOnLastKnownLocation() {
    final Location current = LocationServices.FusedLocationApi.getLastLocation();
    if (current != null) {
      updateMapPosition(current);
    }
  }

  private void requestLocationUpdates() {
    LocationRequest locationRequest = LocationRequest.create()
        .setInterval(LOCATION_REQUEST_INTERVAL_MILLIS)
        .setFastestInterval(LOCATION_REQUEST_DISPLACEMENT_MILLIS)
        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    LocationServices.FusedLocationApi.requestLocationUpdates(locationRequest, locationListener);
  }

  private void removeLocationUpdates() {
    lostApiClient.disconnect();
  }

  private void handleLocationChange(Location location) {
    if (!myLocationEnabled) {
      return;
    }

    updateCurrentLocationMapData(location);
    checkFindMeAndCenterMap(location);
  }

  /**
   * Center map on location only if find me button is activated.
   */
  private void checkFindMeAndCenterMap(Location location) {
    final ImageButton button = mapView.showFindMe();
    if (button.isActivated()) {
      updateMapPosition(location);
    }
  }

  private void updateCurrentLocationMapData(final Location location) {
    currentLocationMapData.clear();
    currentLocationMapData.addPoint(convertLocation(location), null);
  }

  private void updateMapPosition(Location location) {
    if (mapController == null) {
      return;
    }

    final LngLat lngLat = new LngLat(location.getLongitude(), location.getLatitude());
    mapController.setZoomEased(DEFAULT_ZOOM, ANIMATION_DURATION_MILLIS);
    mapStateManager.setZoom(DEFAULT_ZOOM);
    mapController.setPositionEased(lngLat, ANIMATION_DURATION_MILLIS);
    mapStateManager.setPosition(lngLat);
    mapController.requestRender();
  }

  private LngLat convertLocation(Location location) {
    return new LngLat(location.getLongitude(), location.getLatitude());
  }

  private void initPolylineMapData() {
    polylineMapData = mapController.addDataLayer(DataLayerType.POLYLINE.toString());
  }

  private void initPolygonMapData() {
    polygonMapData = mapController.addDataLayer(DataLayerType.POLYGON.toString());
  }

  private void initMarkerMapData() {
    markerMapData = mapController.addDataLayer(DataLayerType.MARKER.toString());
  }

  private MapData addPolylineToPolylineMapData(List<LngLat> coordinates) {
    MapData mapData = polylineMapData.addPolyline(coordinates, null);
    mapController.requestRender();
    return mapData;
  }

  private MapData addPolygonToPolygonMapData(List<List<LngLat>> coordinates) {
    MapData mapData = polygonMapData.addPolygon(coordinates, null);
    mapController.requestRender();
    return mapData;
  }

  private MapData addPointToMarkerMapData(Marker marker) {
    MapData mapData = markerMapData.addPoint(marker.getLocation(), null);
    mapController.requestRender();
    return mapData;
  }

  @Override public boolean onPan(float startX, float startY, float endX, float endY) {
    final View findMe = mapView.findViewById(R.id.mz_find_me);
    findMe.setActivated(false);
    return false;
  }

  @Override public boolean onFling(float posX, float posY, float velocityX, float velocityY) {
    final View findMe = mapView.findViewById(R.id.mz_find_me);
    findMe.setActivated(false);
    return false;
  }
}
