package com.mapzen.android.graphics.model;

/**
 * Map style given a scene file.
 */
public class MapStyle {

  private final String sceneFile;

  /**
   * Creates a new instance.
   */
  public MapStyle(String sceneFile) {
    this.sceneFile = sceneFile;
  }

  /**
   * Get the underlying scene filename.
   */
  public String getSceneFile() {
    return sceneFile;
  }
}
