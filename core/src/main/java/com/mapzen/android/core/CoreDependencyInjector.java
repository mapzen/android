package com.mapzen.android.core;

import com.mapzen.android.graphics.GraphicsModule;
import com.mapzen.android.graphics.MapInitializer;
import com.mapzen.android.graphics.MapView;
import com.mapzen.android.search.MapzenSearch;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Manages library dependencies.
 */
class CoreDependencyInjector {
  /**
   * Library component interface.
   */
  @Singleton @Component(modules = { CoreAndroidModule.class, GraphicsModule.class })
  public interface CoreLibraryComponent {
    void inject(MapView mapView);
    void inject(MapInitializer mapInitializer);
    void inject(MapzenSearch search);
  }

  private CoreLibraryComponent component;

  private static CoreDependencyInjector instance;

  /**
   * Creates a new instance of the dependency injection framework if not already initialized.
   */
  static void createInstance(Context context) {
    if (instance == null) {
      instance = new CoreDependencyInjector(context);
    }
  }

  /**
   * Returns the singleton instance of the dependency injection framework.
   */
  static CoreDependencyInjector getInstance() {
    return instance;
  }

  private CoreDependencyInjector(Context context) {
    component = DaggerCoreDependencyInjector_CoreLibraryComponent.builder()
        .coreAndroidModule(new CoreAndroidModule(context))
        .build();
  }

  /**
   * Returns the module component.
   */
  CoreLibraryComponent component() {
    return component;
  }
}
