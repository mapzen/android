package com.mapzen.android;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dependency injection module for components that do not depend on Android components.
 */
@Module public class CommonModule {

  /**
   * Returns the object used to persist {@link MapData} objects on a {@link MapzenMap}.
   * @return
   */
  @Provides @Singleton public MapDataManager providesMapDataManager() {
    return new MapDataManager();
  }

  /**
   * Returns the object used to persist map position/zoom/tilt on a {@link MapzenMap}.
   * @return
   */
  @Provides @Singleton public MapStateManager providesMapStateManager() {
    return new MapStateManager();
  }
}
