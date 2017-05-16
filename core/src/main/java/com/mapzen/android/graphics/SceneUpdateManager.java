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

  /**
   * Creates {@link SceneUpdate}s given an api key and locale.
   * @param apiKey user's api key.
   * @param locale user's preferred map locale.
   * @return scene updates for api key and locale.
   */
  List<SceneUpdate> getUpdatesFor(String apiKey, Locale locale) {
    final ArrayList<SceneUpdate> sceneUpdates = new ArrayList<>(2);
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_API_KEY, apiKey));
    sceneUpdates.add(new SceneUpdate(STYLE_GLOBAL_VAR_LANGUAGE, locale.getLanguage()));
    return sceneUpdates;
  }
}
