package com.mapzen.android.graphics;

import com.mapzen.tangram.SceneUpdate;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Manages creation of {@link SceneUpdate}s for api keys and languages.
 */
class SceneUpdateManager {

  static final String STYLE_GLOBAL_VAR_API_KEY = "global.sdk_mapzen_api_key";
  static final String STYLE_GLOBAL_VAR_LANGUAGE = "global.ux_language";
  static final String STYLE_GLOBAL_VAR_TRANSIT_OVERLAY = "global.sdk_transit_overlay";
  static final String STYLE_GLOBAL_VAR_BIKE_OVERLAY = "global.sdk_bike_overlay";
  static final String STYLE_GLOBAL_VAR_PATH_OVERLAY = "global.sdk_path_overlay";

  /**
   * Creates {@link SceneUpdate}s given an api key and locale.
   * @param apiKey user's api key.
   * @param locale user's preferred map locale.
   * @return scene updates for api key and locale.
   */
  List<SceneUpdate> getUpdatesFor(String apiKey, Locale locale, boolean transitOverlayEnabled,
      boolean bikeOverlayEnabled, boolean pathOverlayEnabled) {
    final ArrayList<SceneUpdate> sceneUpdates = new ArrayList<>(2);
    sceneUpdates.add(getApiKeyUpdate(apiKey));
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_LANGUAGE, locale.getLanguage()));
    sceneUpdates.add(getTransitOverlayUpdate(transitOverlayEnabled));
    sceneUpdates.add(getBikeOverlayUpdate(bikeOverlayEnabled));
    sceneUpdates.add(getPathOverlayUpdate(pathOverlayEnabled));
    return sceneUpdates;
  }

  /**
   * Creates a {@link SceneUpdate} for enabling/disabling the transit overlay.
   * @param transitOverlayEnabled
   * @return
   */
  SceneUpdate getTransitOverlayUpdate(boolean transitOverlayEnabled) {
    return new SceneUpdate(STYLE_GLOBAL_VAR_TRANSIT_OVERLAY, String.valueOf(
        transitOverlayEnabled));
  }

  /**
   * Creates a {@link SceneUpdate} for enabling/disabling the bike overlay.
   * @param bikeOverlayEnabled
   * @return
   */
  SceneUpdate getBikeOverlayUpdate(boolean bikeOverlayEnabled) {
    return new SceneUpdate(STYLE_GLOBAL_VAR_BIKE_OVERLAY, String.valueOf(
        bikeOverlayEnabled));
  }

  /**
   * Creates a {@link SceneUpdate} for enabling/disabling the path overlay.
   * @param pathOverlayEnabled
   * @return
   */
  SceneUpdate getPathOverlayUpdate(boolean pathOverlayEnabled) {
    return new SceneUpdate(STYLE_GLOBAL_VAR_PATH_OVERLAY, String.valueOf(
        pathOverlayEnabled));
  }

  /**
   * Creates a {@link SceneUpdate} for the API key.
   * @param apiKey
   * @return
   */
  SceneUpdate getApiKeyUpdate(String apiKey) {
    return new SceneUpdate(STYLE_GLOBAL_VAR_API_KEY, apiKey);
  }
}
