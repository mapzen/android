package com.mapzen.android.graphics.model;

import com.mapzen.android.graphics.internal.StyleStringGenerator;
import com.mapzen.tangram.LngLat;
import com.mapzen.tangram.MapController;
import com.mapzen.tangram.Marker;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages {@link BitmapMarker} instances on the map.
 */
public class MarkerManager {
  private final MapController mapController;
  private final BitmapMarkerFactory bitmapMarkerFactory;
  private final StyleStringGenerator styleStringGenerator;
  private List<BitmapMarker> restorableMarkers;

  /**
   * Constructor.
   *
   * @param mapController Tangram map controller used to generate markers.
   */
  public MarkerManager(MapController mapController, BitmapMarkerFactory bitmapMarkerFactory,
      StyleStringGenerator styleStringGenerator) {
    this.mapController = mapController;
    this.bitmapMarkerFactory = bitmapMarkerFactory;
    this.styleStringGenerator = styleStringGenerator;
    this.restorableMarkers = new ArrayList<>();
  }

  /**
   * Adds a new marker to the map.
   *
   * @param markerOptions options that define the appearance of the marker.
   * @return a new bitmap marker wrapper for the Tangram marker object.
   */
  public BitmapMarker addMarker(MarkerOptions markerOptions) {
    final Marker marker = mapController.addMarker();
    configureTangramMarker(marker, styleStringGenerator, markerOptions.getPosition(),
        markerOptions.getIconDrawable(), markerOptions.getIcon(), markerOptions.getWidth(),
        markerOptions.getHeight(), markerOptions.isInteractive(), markerOptions.getColorHex(),
        markerOptions.getColorInt(), markerOptions.isVisible(), markerOptions.getDrawOrder(),
        markerOptions.getUserData());
    BitmapMarker bitmapMarker = bitmapMarkerFactory.createMarker(this, marker,
        styleStringGenerator);
    restorableMarkers.add(bitmapMarker);
    return bitmapMarker;
  }

  /**
   * Removes a marker from the map.
   *
   * @param marker Tangram marker to be removed.
   */
  public void removeMarker(BitmapMarker marker) {
    restorableMarkers.remove(marker);
    mapController.removeMarker(marker.getTangramMarker());
  }

  /**
   * Restores underlying Tangram marker for all {@link BitmapMarker}s when scene updates occur.
   */
  public void restoreMarkers() {
    for (BitmapMarker restorableMarker : restorableMarkers) {
      Marker tangramMarker = mapController.addMarker();
      configureTangramMarker(tangramMarker, restorableMarker.getStyleStringGenerator(),
          restorableMarker.getPosition(), restorableMarker.getIconDrawable(),
          restorableMarker.getIcon(), restorableMarker.getWidth(), restorableMarker.getHeight(),
          restorableMarker.isInteractive(), restorableMarker.getColorHex(),
          restorableMarker.getColor(), restorableMarker.isVisible(),
          restorableMarker.getDrawOrder(), restorableMarker.getUserData());
      restorableMarker.setTangramMarker(tangramMarker);
    }
  }

  private void configureTangramMarker(Marker marker, StyleStringGenerator styleStringGenerator,
      LngLat position, Drawable drawable, int drawableId, int width, int height,
      boolean interactive, String colorHex, int colorInt, boolean visible, int drawOrder,
      Object userData) {
    marker.setPoint(position);
    if (drawable != null) {
      marker.setDrawable(drawable);
    } else {
      marker.setDrawable(drawableId);
    }
    marker.setVisible(visible);
    marker.setDrawOrder(drawOrder);
    marker.setUserData(userData);
    String color;
    if (colorHex != null) {
      color = colorHex;
    } else {
      color = "#" + Integer.toHexString(colorInt);
    }
    marker.setStylingFromString(styleStringGenerator.getStyleString(width, height, interactive,
        color));
  }
}
