package com.mapzen.android.graphics;

import com.mapzen.android.graphics.model.Marker;
import com.mapzen.android.graphics.model.Polygon;
import com.mapzen.android.graphics.model.Polyline;
import com.mapzen.tangram.LngLat;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PersistableMapDataTest {

  @Test public void constructor_shouldSetPolylineAndDataLayerType() {
    Polyline polyline = new Polyline.Builder().add(new LngLat(-73.9903, 40.74433))
        .add(new LngLat(-73.984770, 40.734807))
        .add(new LngLat(-73.998674, 40.732172))
        .add(new LngLat(-73.996142, 40.741050))
        .build();
    PersistableMapData mapData = new PersistableMapData(polyline);
    assertThat(mapData.getPolyline()).isEqualTo(polyline);
    assertThat(mapData.getDataLayerType()).isEqualTo(DataLayerType.POLYLINE);
  }

  @Test public void constructor_shouldSetPolygonAndDataLayerType() {
    Polygon polygon = new Polygon.Builder().add(new LngLat(-73.9903, 40.74433))
        .add(new LngLat(-73.984770, 40.734807))
        .add(new LngLat(-73.998674, 40.732172))
        .add(new LngLat(-73.996142, 40.741050))
        .build();
    PersistableMapData mapData = new PersistableMapData(polygon);
    assertThat(mapData.getPolygon()).isEqualTo(polygon);
    assertThat(mapData.getDataLayerType()).isEqualTo(DataLayerType.POLYGON);
  }

  @Test public void constructor_shouldSetMarkerAndDataLayerType() {
    Marker marker = new Marker(-73.9903, 40.74433);
    PersistableMapData mapData = new PersistableMapData(marker);
    assertThat(mapData.getMarker()).isEqualTo(marker);
    assertThat(mapData.getDataLayerType()).isEqualTo(DataLayerType.MARKER);
  }

  @Test public void constructor_shouldSetStartEndAndDataLayerType() {
    LngLat start = new LngLat(-73.9903, 40.74433);
    LngLat end = new LngLat(-73.9903, 40.74433);
    PersistableMapData mapData = new PersistableMapData(start, end);
    assertThat(mapData.getStart()).isEqualTo(start);
    assertThat(mapData.getEnd()).isEqualTo(end);
    assertThat(mapData.getDataLayerType()).isEqualTo(DataLayerType.START_END_PIN);
  }

  @Test public void constructor_shouldSetPointAndDataLayerType() {
    LngLat point = new LngLat(-73.9903, 40.74433);
    PersistableMapData mapData = new PersistableMapData(point, DataLayerType.DROPPED_PIN);
    assertThat(mapData.getPoint()).isEqualTo(point);
    assertThat(mapData.getDataLayerType()).isEqualTo(DataLayerType.DROPPED_PIN);
  }

  @Test public void constructor_shouldSetPointAndActiveAndIndexAndDataLayerType() {
    LngLat point = new LngLat(-73.9903, 40.74433);
    PersistableMapData mapData = new PersistableMapData(point, true, 8);
    assertThat(mapData.getPoint()).isEqualTo(point);
    assertThat(mapData.getIsActive()).isTrue();
    assertThat(mapData.getIndex()).isEqualTo(8);
    assertThat(mapData.getDataLayerType()).isEqualTo(DataLayerType.SEARCH_RESULT_PIN);
  }

  @Test public void constructor_shouldSetPointsAndDataLayerType() {
    List<LngLat> points = new ArrayList();
    PersistableMapData mapData = new PersistableMapData(points);
    assertThat(mapData.getPoints()).isEqualTo(points);
    assertThat(mapData.getDataLayerType()).isEqualTo(DataLayerType.ROUTE_LINE);
  }

  @Test public void constructor_shouldSetPointsAndStationsAndHexAndDataLayerType() {
    List<LngLat> points = new ArrayList();
    List<LngLat> stations = new ArrayList();
    PersistableMapData mapData = new PersistableMapData(points, stations, "hex");
    assertThat(mapData.getPoints()).isEqualTo(points);
    assertThat(mapData.getStations()).isEqualTo(stations);
    assertThat(mapData.getHexColor()).isEqualTo("hex");
    assertThat(mapData.getDataLayerType()).isEqualTo(DataLayerType.TRANSIT_ROUTE_LINE_STATION_ICON);
  }

  @Test public void constructor_shouldSetLocationEnabledAndDataLayer() {
    PersistableMapData mapData = new PersistableMapData(true);
    assertThat(mapData.getLocationEnabled()).isTrue();
    assertThat(mapData.getDataLayerType()).isEqualTo(DataLayerType.CURRENT_LOCATION);
  }
}
