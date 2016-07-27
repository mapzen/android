package com.mapzen.android;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dependency injection module for components that depend on an Android {@link Context}.
 */
@Module public class AndroidModule {
  private static final String TAG = AndroidModule.class.getSimpleName();

  private final Context context;

  /**
   * Creates module to provide components that depend on an Android {@link Context}. The
   * application context is used so it will persist on configuration changes.
   *
   * @param context The context that should be used to inject Android-specific components.
   */
  AndroidModule(Context context) {
    this.context = context.getApplicationContext();
  }

  /**
   * Provides Android application context.
   */
  @Provides @Singleton public Context provideApplicationContext() {
    return context;
  }

  /**
   * Provides Android application resources.
   */
  @Provides @Singleton public Resources provideResources() {
    return context.getResources();
  }

  /**
   * Provides HTTP handler to append API key to outgoing vector tile requests.
   */
  @Provides @Singleton public TileHttpHandler provideTileHttpHandler(Resources res) {
    final String packageName = context.getPackageName();
    try {
      final int apiKeyId = res.getIdentifier(ApiKeyConstants.API_KEY_TILE_RES_NAME,
          ApiKeyConstants.API_KEY_RES_TYPE, packageName);
      final String apiKey = res.getString(apiKeyId);
      return new TileHttpHandler(apiKey);
    } catch (Resources.NotFoundException e) {
      Log.e(TAG, e.getLocalizedMessage());
    }
    return new TileHttpHandler(null);
  }

  /**
   * Provides HTTP handler to append API key to outgoing turn-by-turn requests.
   */
  @Provides @Singleton public TurnByTurnHttpHandler provideTurnByTurnHttpHandler(Resources res) {
    TurnByTurnHttpHandler handler = new TurnByTurnHttpHandler();
    final String packageName = context.getPackageName();
    try {
      final int apiKeyId = res.getIdentifier(ApiKeyConstants.API_KEY_TURN_BY_TURN_RES_NAME,
          ApiKeyConstants.API_KEY_RES_TYPE, packageName);
      final String apiKey = res.getString(apiKeyId);
      handler.setApiKey(apiKey);
    } catch (Resources.NotFoundException e) {
      Log.e(TAG, e.getLocalizedMessage());
    }
    return handler;
  }

  /**
   * Provide initializer for configuring {@link MapzenSearch} objects.
   * @return
   */
  @Provides @Singleton public SearchInitializer provideSearchInitializer() {
    return new SearchInitializer();
  }
}
