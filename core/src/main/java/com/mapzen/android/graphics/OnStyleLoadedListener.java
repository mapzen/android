package com.mapzen.android.graphics;

/**
 * Callback used when loading a new {@link com.mapzen.android.graphics.model.MapStyle}
 * asynchronously.
 */
public interface OnStyleLoadedListener {
  /**
   * Called when the style has finished loaded.
   */
  void onStyleLoaded();
}
