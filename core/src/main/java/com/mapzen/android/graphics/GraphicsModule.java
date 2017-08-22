package com.mapzen.android.graphics;

import com.mapzen.android.graphics.internal.StyleStringGenerator;
import com.mapzen.android.graphics.model.BitmapMarkerFactory;
import com.mapzen.android.graphics.model.MarkerManager;

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

  /**
   * Returns the object used create {@link com.mapzen.android.graphics.model.BitmapMarker}s.
   * @return
   */
  @Provides @Singleton public BitmapMarkerFactory providesBitmapMarkerFactory() {
    return new BitmapMarkerFactory();
  }

  /**
   * Returns the object used to generate a style string for a Tangram
   * {@link com.mapzen.tangram.Marker}.
   */
  @Provides @Singleton public StyleStringGenerator providesStyleStringGenerator() {
    return new StyleStringGenerator();
  }

  /**
   * Returns the object used to manager markers.
   * {@link com.mapzen.tangram.Marker}.
   */
  @Provides @Singleton public MarkerManager providesMarkerManager(
      BitmapMarkerFactory bitmapMarkerFactory, StyleStringGenerator styleStringGenerator) {
    return new MarkerManager(bitmapMarkerFactory, styleStringGenerator);
  }
}
