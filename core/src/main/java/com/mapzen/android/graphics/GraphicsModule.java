package com.mapzen.android.graphics;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dependency injection module for graphics components that do not depend on Android components.
 */
@Module public class GraphicsModule {

  /**
   * Returns the object used to persist {@link com.mapzen.tangram.MapData} objects on a
   * {@link MapzenMap}.
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

  /**
   * Returns the object used create scene updates for global variables on a {@link MapzenMap}.
   * @return
   */
  @Provides @Singleton public SceneUpdateManager providesSceneUpdateManager() {
    return new SceneUpdateManager();
  }

}
