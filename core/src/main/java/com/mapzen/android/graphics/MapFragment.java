package com.mapzen.android.graphics;

import com.mapzen.R;
import com.mapzen.android.graphics.model.MapStyle;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Locale;

import static com.mapzen.android.graphics.MapView.OVERLAY_MODE_SDK;

/**
 * A map component in an app. This fragment is the simplest way to place a map in an application.
 * It's a wrapper around a view of a map to automatically handle the necessary life cycle needs.
 */
public class MapFragment extends Fragment {
  MapView mapView;

  private int overlayMode = OVERLAY_MODE_SDK;

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    int layout = R.layout.mz_fragment_map;
    if (overlayMode == MapView.OVERLAY_MODE_CLASSIC) {
      layout = R.layout.mz_fragment_map_classic;
    }

    mapView = (MapView) inflater.inflate(layout, container, false);
    return mapView;
  }

  @Override public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
    super.onInflate(context, attrs, savedInstanceState);
    TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MapFragment);
    try {
      overlayMode = a.getInteger(R.styleable.MapFragment_overlayMode, OVERLAY_MODE_SDK);
    } finally {
      a.recycle();
    }
  }

  /**
   * Asynchronously creates the map and configures the vector tiles API key using the string
   * resource declared in the client application. Uses default stylesheet (bubble wrap).
   */
  public void getMapAsync(final OnMapReadyCallback callback) {
    mapView.getMapAsync(callback);
  }

  /**
   * Asynchronously creates the map and configures the vector tiles API key using the string
   * resource declared in the client application. Uses default stylesheet (bubble wrap).
   */
  public void getMapAsync(String mapId, final OnMapReadyCallback callback) {
    mapView.getMapAsync(mapId, callback);
  }

  /**
   * Asynchronously creates the map and configures the vector tiles API key using the string
   * resource declared in the client application. Configures the stylesheet using the stylesheet
   * parameter.
   */
  public void getMapAsync(MapStyle mapStyle, final OnMapReadyCallback callback) {
    mapView.getMapAsync(mapStyle, callback);
  }

  /**
   * Asynchronously creates the map and configures the vector tiles API key using the string
   * resource declared in the client application. Configures the stylesheet using the stylesheet
   * parameter.
   */
  public void getMapAsync(String mapId, MapStyle mapStyle, final OnMapReadyCallback callback) {
    mapView.getMapAsync(mapId, mapStyle, callback);
  }

  /**
   * Asynchronously creates the map and configures the vector tiles API key using the string
   * resource declared in the client application. Configures the stylesheet using the stylesheet
   * parameter.
   *
   * Also sets {@link Locale} used to determine default language when rendering map labels.
   */
  public void getMapAsync(MapStyle mapStyle, Locale locale, final OnMapReadyCallback callback) {
    mapView.getMapAsync(mapStyle, locale, callback);
  }

  /**
   * Asynchronously creates the map and configures the vector tiles API key using the string
   * resource declared in the client application. Configures the stylesheet using the stylesheet
   * parameter.
   *
   * Also sets {@link Locale} used to determine default language when rendering map labels.
   */
  public void getMapAsync(MapStyle mapStyle, String mapId, Locale locale, final OnMapReadyCallback callback) {
    mapView.getMapAsync(mapStyle, mapId, locale, callback);
  }

  @Override public void onDestroy() {
    super.onDestroy();
    if (mapView != null) {
      mapView.onDestroy();
    }
  }
}
