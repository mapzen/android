package com.mapzen.android.graphics;

import com.mapzen.android.graphics.internal.StyleStringGenerator;
import com.mapzen.android.graphics.model.BitmapMarkerFactory;
import com.mapzen.android.graphics.model.BitmapMarkerManager;

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
  @Provides @Singleton public PersistDataManagers providesPersistDataManagers() {
    return PersistDataManagers.instance();
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
  @Provides @Singleton public BitmapMarkerManager providesBitmapMarkerManager(
      BitmapMarkerFactory bitmapMarkerFactory, StyleStringGenerator styleStringGenerator) {
    return new BitmapMarkerManager(bitmapMarkerFactory, styleStringGenerator);
  }

  /**
   * Returns the object used to generate import yaml for use loading
   * {@link com.mapzen.android.graphics.model.ThemedMapStyle}s.
   * @return
   */
  @Provides @Singleton public ImportYamlGenerator providesImportYamlGenerator() {
    return new ImportYamlGenerator();
  }
}
