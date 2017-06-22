package com.mapzen.android.core;

import com.mapzen.android.graphics.BaseMapzenMapHttpHandler;
import com.mapzen.android.graphics.MapzenMapHttpHandler;
import com.mapzen.android.search.SearchInitializer;

import android.content.Context;
import android.content.res.Resources;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dependency injection module for components that depend on an Android {@link Context}.
 */
@Module class CoreAndroidModule {
  private final Context context;

  /**
   * Creates module to provide components that depend on an Android {@link Context}. The
   * application context is used so it will persist on configuration changes.
   *
   * @param context The context that should be used to inject Android-specific components.
   */
  CoreAndroidModule(Context context) {
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
   * Provides HTTP handler to configure User-Agent for outgoing vector tile requests.
   */
  @Provides @Singleton public MapzenMapHttpHandler provideTileHttpHandler() {
    return new BaseMapzenMapHttpHandler();
  }

  /**
   * Provide initializer for configuring {@link com.mapzen.android.search.MapzenSearch} objects.
   * @return
   */
  @Provides @Singleton public SearchInitializer provideSearchInitializer() {
    return new SearchInitializer();
  }
}
