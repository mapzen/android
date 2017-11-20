package com.mapzen.android.core;

import android.support.annotation.NonNull;

/**
 * Listener for handling changes to API key.
 */
public interface ApiKeyChangeListener {
  /**
   * Called when the {@link MapzenManager}'s API key is changed.
   * @param apiKey the current API key
   */
  void onApiKeyChanged(@NonNull String apiKey);
}
