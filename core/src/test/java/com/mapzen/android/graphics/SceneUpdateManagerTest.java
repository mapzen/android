package com.mapzen.android.graphics;

import com.mapzen.tangram.SceneUpdate;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

public class SceneUpdateManagerTest {

  SceneUpdateManager manager;
  String apiKey;
  Locale locale;

  @Before public void setUp() throws Exception {
    manager = new SceneUpdateManager();
    apiKey = "test_key";
    locale = new Locale("fr_fr");
  }

  @Test public void shouldReturnCorrectKeysAndValues() {
    List<SceneUpdate> updates = manager.getUpdatesFor(apiKey, locale);
    assertThat(updates.size()).isEqualTo(2);
    assertThat(updates.get(0).getPath()).isEqualTo("global.sdk_mapzen_api_key");
    assertThat(updates.get(0).getValue()).isEqualTo(apiKey);
    assertThat(updates.get(1).getPath()).isEqualTo("global.ux_language");
    assertThat(updates.get(1).getValue()).isEqualTo(locale.getLanguage());
  }
}
