package com.mapzen.android.graphics.model;

import android.support.annotation.NonNull;

/**
 * Map style given a scene file.
 */
public class MapStyle {

  private final String sceneFile;

  /**
   * Creates a new instance.
   */
  public MapStyle(@NonNull String sceneFile) {
    this.sceneFile = sceneFile;
  }

  /**
   * Get the underlying scene filename.
   */
  @NonNull public String getSceneFile() {
    return sceneFile;
  }
}
