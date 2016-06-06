package com.mapzen.tangram;

import java.util.List;
import java.util.Map;

/**
 * Returned by {@link com.mapzen.android.TestMapController}
 */
public class TestMapData extends MapData {
  public LngLat point;
  public List<LngLat> polyline;
  public List<List<LngLat>> polygon;

  public TestMapData(String name) {
    super(name, 0, null);
  }

  @Override public MapData addPoint(LngLat point, Map<String, String> properties) {
    this.point = point;
    return this;
  }

  @Override public MapData addPolyline(List<LngLat> polyline, Map<String, String> properties) {
    this.polyline = polyline;
    return this;
  }

  @Override public MapData addPolygon(List<List<LngLat>> polygon, Map<String, String> properties) {
    this.polygon = polygon;
    return this;
  }

  @Override public MapData clear() {
    point = null;
    polyline = null;
    polygon = null;
    return this;
  }
}
