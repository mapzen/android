package com.mapzen.android;

import com.mapzen.android.model.Marker;
import com.mapzen.android.model.Polygon;
import com.mapzen.android.model.Polyline;
import com.mapzen.tangram.LngLat;

import java.util.List;

/**
 * Represents {@link MapData} objects in a way that allows {@link MapDataManager} to restore
 * map state on recreation.
 */
class PersistableMapData {

  private DataLayerType dataLayerType;
  private Polyline polyline;
  private Polygon polygon;
  private Marker marker;
  private LngLat start;
  private LngLat end;
  private LngLat point;
  private boolean isActive;
  private int index;
  private List<LngLat> points;
  private List<LngLat> stations;
  private String hexColor;
  private boolean locationEnabled;

  /**
   * Creates a new object to represent polyline {@link MapData}.
   * @param polyline
   */
  public PersistableMapData(Polyline polyline) {
    this.polyline = polyline;
    this.dataLayerType = DataLayerType.POLYLINE;
  }

  /**
   * Creates a new object to represent polygon {@link MapData}.
   * @param polygon
   */
  public PersistableMapData(Polygon polygon) {
    this.polygon = polygon;
    this.dataLayerType = DataLayerType.POLYGON;
  }

  /**
   * Creates a new object to represent marker {@link MapData}.
   * @param marker
   */
  public PersistableMapData(Marker marker) {
    this.marker = marker;
    this.dataLayerType = DataLayerType.MARKER;
  }

  /**
   * Creates a new object to represent start and end pin {@link MapData}.
   * @param start
   * @param end
   */
  public PersistableMapData(LngLat start, LngLat end) {
    this.start = start;
    this.end = end;
    this.dataLayerType = DataLayerType.START_END_PIN;
  }

  /**
   * Creates a new object to represent point {@link MapData}. Used for DataLayerTypes DROPPED_PIN
   * and ROUTE_PIN
   * @param point
   * @param layerType
   */
  public PersistableMapData(LngLat point, DataLayerType layerType) {
    this.point = point;
    this.dataLayerType = layerType;
  }

  /**
   * Creates a new object to represent {@link MapData} where a point, index and active state
   * exist. Used for DataLayerType.SEARCH_RESULT_PIN
   * @param point
   * @param active
   * @param index
   */
  public PersistableMapData(LngLat point, boolean active, int index) {
    this.point = point;
    this.isActive = active;
    this.index = index;
    this.dataLayerType = DataLayerType.SEARCH_RESULT_PIN;
  }

  /**
   * Creates a new object to represent route line {@link MapData}.
   * @param points
   */
  public PersistableMapData(List<LngLat> points) {
    this.points = points;
    this.dataLayerType = DataLayerType.ROUTE_LINE;
  }

  /**
   * Creates a new object to represent transit route line {@link MapData}.
   * @param points
   * @param stations
   * @param hexColor
   */
  public PersistableMapData(List<LngLat> points, List<LngLat> stations, String hexColor) {
    this.points = points;
    this.stations = stations;
    this.hexColor = hexColor;
    this.dataLayerType = DataLayerType.TRANSIT_ROUTE_LINE_STATION_ICON;
  }

  /**
   * Creates a new object to represent current location {@link MapData}.
   * @param locationEnabled
   */
  public PersistableMapData(boolean locationEnabled) {
    this.locationEnabled = locationEnabled;
    this.dataLayerType = DataLayerType.CURRENT_LOCATION;
  }

  public DataLayerType getDataLayerType() {
    return dataLayerType;
  }

  public Polyline getPolyline() {
    return polyline;
  }

  public Polygon getPolygon() {
    return polygon;
  }

  public Marker getMarker() {
    return marker;
  }

  public LngLat getStart() {
    return start;
  }

  public LngLat getEnd() {
    return end;
  }

  public LngLat getPoint() {
    return point;
  }

  public boolean getIsActive() {
    return isActive;
  }

  public int getIndex() {
    return index;
  }

  public List<LngLat> getPoints() {
    return points;
  }

  public List<LngLat> getStations() {
    return stations;
  }

  public String getHexColor() {
    return hexColor;
  }

  public boolean getLocationEnabled() {
    return locationEnabled;
  }
}
