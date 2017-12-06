package com.mapzen.android.graphics;

import com.mapzen.pelias.BoundingBox;
import com.mapzen.pelias.PeliasLocationProvider;
import com.mapzen.tangram.LngLat;

import android.content.Context;
import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.WindowManager;

/**
 * This class uses a {@link MapzenMap} to return both lat/lon and a bounding box based on the map's
 * current position. Use this class in conjunction with a
 * {@link com.mapzen.pelias.widget.PeliasSearchView} if you want search/autocomplete results
 * relevant to the map's position.
 */
public class MapzenMapPeliasLocationProvider implements PeliasLocationProvider {

  Context context;
  MapzenMap mapzenMap;

  /**
   * Public constructor.
   * @param context
   */
  public MapzenMapPeliasLocationProvider(@NonNull Context context) {
    this.context = context;
  }

  /**
   * Set the map to use for retrieving lat/lon/bounding box.
   * @param map
   */
  public void setMapzenMap(@Nullable MapzenMap map) {
    this.mapzenMap = map;
  }

  @Override public double getLat() {
    if (mapzenMap == null) {
      return 0;
    }
    WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    Display display = windowManager.getDefaultDisplay();
    LngLat midLatLon = mapzenMap.screenPositionToLngLat(new PointF(display.getWidth() / 2,
        display.getHeight() / 2));
    return midLatLon.latitude;
  }

  @Override public double getLon() {
    if (mapzenMap == null) {
      return 0;
    }
    WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    Display display = windowManager.getDefaultDisplay();
    LngLat midLatLon = mapzenMap.screenPositionToLngLat(new PointF(display.getWidth() / 2,
        display.getHeight() / 2));
    return midLatLon.longitude;
  }

  @Override @Nullable public BoundingBox getBoundingBox() {
    if (mapzenMap == null) {
      return null;
    }
    WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    Display display = windowManager.getDefaultDisplay();
    LngLat minLatLon = mapzenMap.screenPositionToLngLat(new PointF(0.f, display.getHeight()));
    LngLat maxLatLon = mapzenMap.screenPositionToLngLat(new PointF(display.getWidth(), 0.f));
    BoundingBox boundingBox =
        new BoundingBox(minLatLon.latitude, minLatLon.longitude, maxLatLon.latitude,
            maxLatLon.longitude);
    return boundingBox;
  }
}

