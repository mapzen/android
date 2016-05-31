package com.mapzen.tangram;

import java.util.List;
import java.util.Map;

/**
 * Returned by {@link com.mapzen.android.TestMapController}
 */
public class TestMapData extends MapData {

  public TestMapData(String name) {
    super(name, 0, null);
  }

  @Override public MapData addPolyline(List<LngLat> polyline, Map<String, String> properties) {
    return this;
  }

  @Override public MapData addPoint(LngLat point, Map<String, String> properties) {
    return this;
  }

  @Override public MapData addPolygon(List<List<LngLat>> polygon, Map<String, String> properties) {
    return this;
  }

  @Override public MapData clear() {
    return this;
  }
}
