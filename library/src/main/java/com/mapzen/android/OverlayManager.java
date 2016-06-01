package com.mapzen.android;

import com.mapzen.android.lost.api.LocationListener;
import com.mapzen.android.lost.api.LocationRequest;
import com.mapzen.android.lost.api.LocationServices;
import com.mapzen.android.lost.api.LostApiClient;
import com.mapzen.android.model.Marker;
import com.mapzen.android.model.Polygon;
import com.mapzen.android.model.Polyline;
import com.mapzen.tangram.LngLat;
import com.mapzen.tangram.MapController;
import com.mapzen.tangram.MapData;

import android.location.Location;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Adds functionality to {@link MapController} map by way of {@link LostApiClient}.
 */
public class OverlayManager {

  private static final int LOCATION_REQUEST_INTERVAL_MILLIS = 5000;
  private static final int LOCATION_REQUEST_DISPLACEMENT_MILLIS = 5000;
  private static final int ANIMATION_DURATION_MILLIS = 500;
  private static final float DEFAULT_ZOOM = 16f;

  static final String NAME_CURRENT_LOCATION = "mz_current_location";
  private static final String NAME_POLYLINE = "mz_default_line";
  private static final String NAME_POLYGON = "mz_default_polygon";
  private static final String NAME_MARKER = "mz_default_point";
  private static final String NAME_START_PIN = "mz_route_start";
  private static final String NAME_END_PIN = "mz_route_stop";
  private static final String NAME_DROPPED_PIN = "mz_dropped_pin";
  private static final String NAME_SEARCH_RESULT_PIN = "mz_search_result";
  private static final String NAME_ROUTE_PIN = "mz_route_location";
  private static final String NAME_ROUTE_LINE = "mz_route_line";

  private static final String PROP_STATE = "state";
  private static final String PROP_STATE_ACTIVE = "active";
  private static final String PROP_STATE_INACTIVE = "inactive";
  private static final String PROP_SEARCH_INDEX = "searchIndex";
  private static final String PROP_TYPE = "type";
  private static final String PROP_POINT = "point";
  private static final String PROP_LINE = "line";

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

  private boolean locationRequested = false;

  /**
   * Receives location updates for {@link LostApiClient}.
   */
  LocationListener locationListener = new LocationListener() {
    @Override public void onLocationChanged(Location location) {
      if (!locationRequested) {
        return;
      }
      handleLocationChange(location);
    }
  };

  View.OnClickListener findMeExternalClickListener;

  private static MapData polylineMapData;
  private static MapData polygonMapData;
  private static MapData markerMapData;
  private static MapData startPinData;
  private static MapData endPinData;
  private static MapData droppedPinData;
  private static MapData searchResultPinData;
  private static MapData routePinData;
  private static MapData routeLineData;

  /**
   * Create a new {@link OverlayManager} object for handling functionality between map and
   * location services using the {@link LocationFactory}'s shared {@link LostApiClient}.
   */
  OverlayManager(MapView mapView, MapController mapController) {
    this(mapView, mapController, LocationFactory.sharedClient(mapView.getContext()));
  }

  /**
   * Create a new {@link OverlayManager} object for handling functionality between map and
   * location services.
   */
  OverlayManager(MapView mapView, MapController mapController, LostApiClient lostApiClient) {
    this.mapView = mapView;
    this.mapController = mapController;
    this.lostApiClient = lostApiClient;
  }

  /**
   * Track the user's current location by displaying an icon on the map and centering the map.
   */
  public void setMyLocationEnabled(boolean enabled) {
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
    if (polyline == null) {
      throw new IllegalArgumentException(
          "Must provide marker when calling " + "MapData#addPolyline");
    }
    if (polyline.getCoordinates().size() < MIN_COORDINATES_POLYLINE) {
      throw new IllegalArgumentException("Polyine must contain at least 2 points");
    }

    if (polylineMapData == null) {
      initPolylineMapData();
    }
    return addPolylineToPolylineMapData(polyline.getCoordinates());
  }

  /**
   * Adds a polygon to the map.
   */
  public MapData addPolygon(Polygon polygon) {
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
   * Add a point to the map for the marker.
   */
  public MapData addMarker(Marker marker) {
    if (marker == null) {
      throw new IllegalArgumentException("Must provide marker when calling " + "MapData#addMarker");
    }

    if (markerMapData == null) {
      initMarkerMapData();
    }
    return addPointToMarkerMapData(marker);
  }

  /**
   * Draws two pins on the map. The start pin is active and the end pin is inactive.
   */
  public void drawRoutePins(LngLat start, LngLat end) {
    if (startPinData == null) {
      startPinData = mapController.addDataLayer(NAME_START_PIN);
    }
    if (endPinData == null) {
      endPinData = mapController.addDataLayer(NAME_END_PIN);
    }
    startPinData.addPoint(start, null);
    endPinData.addPoint(end, null);
    mapController.requestRender();
  }

  /**
   * Clears the start and end pins from the map.
   */
  public void clearRoutePins() {
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
    if (droppedPinData == null) {
      droppedPinData = mapController.addDataLayer(NAME_DROPPED_PIN);
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
    if (searchResultPinData == null) {
      searchResultPinData = mapController.addDataLayer(NAME_SEARCH_RESULT_PIN);
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
    if (searchResultPinData != null) {
      searchResultPinData.clear();
    }
  }

  /**
   * Draws route pin at the point supplied.
   */
  public void drawRouteLocationMarker(LngLat point) {
    if (routePinData == null) {
      routePinData = mapController.addDataLayer(NAME_ROUTE_PIN);
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
    if (routePinData != null) {
      routePinData.clear();
    }
  }

  /**
   * Draws route line on the map for the points supplied.
   */
  public void drawRouteLine(List<LngLat> points) {
    if (routeLineData == null) {
      routeLineData = mapController.addDataLayer(NAME_ROUTE_LINE);
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
    if (routeLineData != null) {
      routeLineData.clear();
    }
  }

  private void addCurrentLocationMapData() {
    if (currentLocationMapData == null) {
      currentLocationMapData = mapController.addDataLayer(NAME_CURRENT_LOCATION);
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
    ImageButton button = mapView.showFindMe();
    button.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        centerMap();
        if (findMeExternalClickListener != null) {
          findMeExternalClickListener.onClick(v);
        }
      }
    });
  }

  private void hideFindMe() {
    mapView.hideFindMe();
    locationRequested = false;
  }

  private void centerMap() {
    locationRequested = true;
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
    if (!myLocationEnabled || !locationRequested) {
      return;
    }
    locationRequested = false;
    updateCurrentLocationMapData(location);
    updateMapPosition(location);
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
    mapController.setPositionEased(lngLat, ANIMATION_DURATION_MILLIS);
    mapController.requestRender();
  }

  private LngLat convertLocation(Location location) {
    return new LngLat(location.getLongitude(), location.getLatitude());
  }

  private void initPolylineMapData() {
    polylineMapData = mapController.addDataLayer(NAME_POLYLINE);
  }

  private void initPolygonMapData() {
    polygonMapData = mapController.addDataLayer(NAME_POLYGON);
  }

  private void initMarkerMapData() {
    markerMapData = mapController.addDataLayer(NAME_MARKER);
  }

  private MapData addPolylineToPolylineMapData(List<LngLat> coordinates) {
    return polylineMapData.addPolyline(coordinates, null);
  }

  private MapData addPolygonToPolygonMapData(List<List<LngLat>> coordinates) {
    return polygonMapData.addPolygon(coordinates, null);
  }

  private MapData addPointToMarkerMapData(Marker marker) {
    return markerMapData.addPoint(marker.getLocation(), null);
  }
}
