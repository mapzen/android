package com.mapzen.android.graphics;

import com.mapzen.android.graphics.model.Marker;
import com.mapzen.android.graphics.model.Polygon;
import com.mapzen.android.graphics.model.Polyline;
import com.mapzen.tangram.LngLat;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MapDataManagerTest {

  MapDataManager dataManager = new MapDataManager();

  @Test public void addMapData_shouldModifyPersistData() {
    Polyline polyline = new Polyline.Builder().add(new LngLat(-73.9903, 40.74433))
        .add(new LngLat(-73.984770, 40.734807))
        .add(new LngLat(-73.998674, 40.732172))
        .add(new LngLat(-73.996142, 40.741050))
        .build();
    PersistableMapData mapData = new PersistableMapData(polyline);
    dataManager.addMapData(mapData);
    assertThat(dataManager.getMapData()).contains(mapData);
  }

  @Test public void removeMapData_polyline_shouldModifyPersistData() {
    Polyline polyline = new Polyline.Builder().add(new LngLat(-73.9903, 40.74433))
        .add(new LngLat(-73.984770, 40.734807))
        .add(new LngLat(-73.998674, 40.732172))
        .add(new LngLat(-73.996142, 40.741050))
        .build();
    PersistableMapData mapData = new PersistableMapData(polyline);
    dataManager.addMapData(mapData);
    assertThat(dataManager.getMapData()).contains(mapData);
    dataManager.removeMapData(DataLayerType.POLYLINE);
    assertThat(dataManager.getMapData()).isEmpty();
  }

  @Test public void removeMapData_polygon_shouldModifyPersistData() {
    Polygon polygon = new Polygon.Builder().add(new LngLat(-73.9903, 40.74433))
        .add(new LngLat(-73.984770, 40.734807))
        .add(new LngLat(-73.998674, 40.732172))
        .add(new LngLat(-73.996142, 40.741050))
        .build();
    PersistableMapData mapData = new PersistableMapData(polygon);
    dataManager.addMapData(mapData);
    assertThat(dataManager.getMapData()).contains(mapData);
    dataManager.removeMapData(DataLayerType.POLYGON);
    assertThat(dataManager.getMapData()).isEmpty();
  }

  @Test public void removeMapData_marker_shouldModifyPersistData() {
    Marker marker = new Marker(-73.9903, 40.74433);
    PersistableMapData mapData = new PersistableMapData(marker);
    dataManager.addMapData(mapData);
    assertThat(dataManager.getMapData()).contains(mapData);
    dataManager.removeMapData(DataLayerType.MARKER);
    assertThat(dataManager.getMapData()).isEmpty();
  }

  @Test public void removeMapData_startEndPoints_shouldModifyPersistData() {
    LngLat start = new LngLat(-73.9903, 40.74433);
    LngLat end = new LngLat(-73.9903, 40.74433);
    PersistableMapData mapData = new PersistableMapData(start, end);
    dataManager.addMapData(mapData);
    assertThat(dataManager.getMapData()).contains(mapData);
    dataManager.removeMapData(DataLayerType.ROUTE_START_PIN);
    assertThat(dataManager.getMapData()).isEmpty();
  }

  @Test public void removeMapData_point_shouldModifyPersistData() {
    LngLat point = new LngLat(-73.9903, 40.74433);
    PersistableMapData mapData = new PersistableMapData(point, DataLayerType.DROPPED_PIN);
    dataManager.addMapData(mapData);
    assertThat(dataManager.getMapData()).contains(mapData);
    dataManager.removeMapData(DataLayerType.DROPPED_PIN);
    assertThat(dataManager.getMapData()).isEmpty();
  }

  @Test public void removeMapData_searchResultPin_shouldModifyPersistData() {
    LngLat point = new LngLat(-73.9903, 40.74433);
    PersistableMapData mapData = new PersistableMapData(point, true, 0);
    dataManager.addMapData(mapData);
    assertThat(dataManager.getMapData()).contains(mapData);
    dataManager.removeMapData(DataLayerType.SEARCH_RESULT_PIN);
    assertThat(dataManager.getMapData()).isEmpty();
  }

  @Test public void removeMapData_routeLine_shouldModifyPersistData() {
    List<LngLat> points = new ArrayList();
    PersistableMapData mapData = new PersistableMapData(points);
    dataManager.addMapData(mapData);
    assertThat(dataManager.getMapData()).contains(mapData);
    dataManager.removeMapData(DataLayerType.ROUTE_LINE);
    assertThat(dataManager.getMapData()).isEmpty();
  }

  @Test public void removeMapData_transitLine_shouldModifyPersistData() {
    List<LngLat> points = new ArrayList();
    List<LngLat> stations = new ArrayList();
    PersistableMapData mapData = new PersistableMapData(points, stations, "hex");
    dataManager.addMapData(mapData);
    assertThat(dataManager.getMapData()).contains(mapData);
    dataManager.removeMapData(DataLayerType.TRANSIT_ROUTE_LINE_STATION_ICON);
    assertThat(dataManager.getMapData()).isEmpty();
  }

  @Test public void removeMapData_currentLocation_shouldModifyPersistData() {
    PersistableMapData mapData = new PersistableMapData(true);
    dataManager.addMapData(mapData);
    assertThat(dataManager.getMapData()).contains(mapData);
    dataManager.removeMapData(DataLayerType.CURRENT_LOCATION);
    assertThat(dataManager.getMapData()).isEmpty();
  }

  @Test public void setPersistMapData_shouldDefaultToFalse() {
    assertThat(MapDataManager.getPersistMapData()).isFalse();
  }

  @Test public void setPersistMapData_shouldUpdatePersistMapData() {
    MapDataManager.setPersistMapData(true);
    assertThat(MapDataManager.getPersistMapData()).isTrue();
  }

}
