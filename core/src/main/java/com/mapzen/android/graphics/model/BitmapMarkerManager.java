package com.mapzen.android.graphics.model;

import com.mapzen.android.graphics.internal.StyleStringGenerator;
import com.mapzen.tangram.LngLat;
import com.mapzen.tangram.MapController;
import com.mapzen.tangram.Marker;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manages {@link BitmapMarker} instances on the map.
 */
public class BitmapMarkerManager {
  private MapController mapController;
  private final BitmapMarkerFactory bitmapMarkerFactory;
  private final StyleStringGenerator styleStringGenerator;
  private List<BitmapMarker> restorableMarkers;

  /**
   * Constructor.
   */
  public BitmapMarkerManager(BitmapMarkerFactory bitmapMarkerFactory,
      StyleStringGenerator styleStringGenerator) {
    this.bitmapMarkerFactory = bitmapMarkerFactory;
    this.styleStringGenerator = styleStringGenerator;
    this.restorableMarkers = new ArrayList<>();
  }

  /**
   * Sets the manager's tangram map. Reset upon orientation changes.
   * @param mapController
   */
  public void setMapController(MapController mapController) {
    this.mapController = mapController;
  }

  /**
   * Adds a new marker to the map.
   *
   * Deprecated in favor of {@link BitmapMarkerManager#addMarker(BitmapMarkerOptions)}.
   *
   * @param markerOptions options that define the appearance of the marker.
   * @return a new bitmap marker wrapper for the Tangram marker object.
   */
  @Deprecated
  public BitmapMarker addMarker(MarkerOptions markerOptions) {
    final Marker marker = mapController.addMarker();
    BitmapMarker bitmapMarker = bitmapMarkerFactory.createMarker(this, marker,
        styleStringGenerator);
    configureMarker(bitmapMarker, markerOptions);
    Collections.synchronizedList(restorableMarkers).add(bitmapMarker);
    return bitmapMarker;
  }

  /**
   * Adds a new marker to the map.
   *
   * @param markerOptions options that define the appearance of the marker.
   * @return a new bitmap marker wrapper for the Tangram marker object.
   */
  public BitmapMarker addMarker(BitmapMarkerOptions markerOptions) {
    final Marker marker = mapController.addMarker();
    BitmapMarker bitmapMarker = bitmapMarkerFactory.createMarker(this, marker,
        styleStringGenerator);
    configureMarker(bitmapMarker, markerOptions);
    Collections.synchronizedList(restorableMarkers).add(bitmapMarker);
    return bitmapMarker;
  }

  /**
   * Removes a marker from the map.
   *
   * @param marker Tangram marker to be removed.
   */
  public void removeMarker(BitmapMarker marker) {
    Collections.synchronizedList(restorableMarkers).remove(marker);
    mapController.removeMarker(marker.getTangramMarker());
  }

  /**
   * Restores underlying Tangram marker for all {@link BitmapMarker}s when scene updates occur.
   */
  public void restoreMarkers() {
    for (BitmapMarker restorableMarker : Collections.synchronizedList(restorableMarkers)) {
      Marker tangramMarker = mapController.addMarker();
      restorableMarker.setTangramMarker(tangramMarker);
      configureMarker(restorableMarker);
    }
  }

  private void configureMarker(BitmapMarker bitmapMarker) {
    configureMarker(bitmapMarker, bitmapMarker.getPosition(),
        bitmapMarker.getIconDrawable(), bitmapMarker.getIcon(),
        bitmapMarker.getWidth(), bitmapMarker.getHeight(),
        bitmapMarker.isInteractive(), bitmapMarker.getColorHex(),
        bitmapMarker.getColor(), bitmapMarker.isVisible(),
        bitmapMarker.getDrawOrder(), bitmapMarker.getUserData());
  }

  private void configureMarker(BitmapMarker bitmapMarker, BitmapMarkerOptions markerOptions) {
    configureMarker(bitmapMarker, markerOptions.getPosition(), markerOptions.getIconDrawable(),
        markerOptions.getIcon(), markerOptions.getWidth(),
        markerOptions.getHeight(), markerOptions.isInteractive(), markerOptions.getColorHex(),
        markerOptions.getColorInt(), markerOptions.isVisible(), markerOptions.getDrawOrder(),
        markerOptions.getUserData());
  }

  @Deprecated
  private void configureMarker(BitmapMarker bitmapMarker, MarkerOptions markerOptions) {
    configureMarker(bitmapMarker, markerOptions.getPosition(), markerOptions.getIconDrawable(),
        markerOptions.getIcon(), markerOptions.getWidth(),
        markerOptions.getHeight(), markerOptions.isInteractive(), markerOptions.getColorHex(),
        markerOptions.getColorInt(), markerOptions.isVisible(), markerOptions.getDrawOrder(),
        markerOptions.getUserData());
  }

  private void configureMarker(BitmapMarker marker, LngLat position, Drawable drawable,
      int drawableId, int width, int height, boolean interactive, String colorHex, int colorInt,
      boolean visible, int drawOrder, Object userData) {
    marker.setPosition(position);
    if (drawable != null) {
      marker.setIcon(drawable);
    } else {
      marker.setIcon(drawableId);
    }
    marker.setVisible(visible);
    marker.setDrawOrder(drawOrder);
    marker.setUserData(userData);
    if (colorHex != null) {
      marker.setColor(colorHex);
    } else {
      marker.setColor(colorInt);
    }
    marker.setSize(width, height);
    marker.setInteractive(interactive);
  }
}
