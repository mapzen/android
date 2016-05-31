package com.mapzen.android;

/**
 * Interface for receiving a {@code MapController} once it is ready to be used.
 */
public interface OnMapReadyCallback {
  /**
   * Called when the map is ready to be used.
   *
   * @param mapzenMap A non-null {@link MapzenMap} instance for this {@code MapView}.
   */
  void onMapReady(MapzenMap mapzenMap);
}
