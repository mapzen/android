package com.mapzen.android.core;

import com.mapzen.android.graphics.GraphicsModule;
import com.mapzen.android.graphics.MapInitializer;
import com.mapzen.android.graphics.MapView;
import com.mapzen.android.routing.MapzenRouter;
import com.mapzen.android.search.MapzenSearch;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Manages library dependencies.
 */
class DependencyInjector {
  /**
   * Library component interface.
   */
  @Singleton @Component(modules = { AndroidModule.class, CommonModule.class, GraphicsModule.class })
  public interface LibraryComponent {
    void inject(MapView mapView);

    void inject(MapInitializer mapInitializer);
    void inject(MapzenRouter router);
    void inject(MapzenSearch search);
  }

  private LibraryComponent component;

  private static DependencyInjector instance;

  /**
   * Creates a new instance of the dependency injection framework if not already initialized.
   */
  static void createInstance(Context context) {
    if (instance == null) {
      instance = new DependencyInjector(context);
    }
  }

  /**
   * Returns the singleton instance of the dependency injection framework.
   */
  static DependencyInjector getInstance() {
    return instance;
  }

  private DependencyInjector(Context context) {
    component = DaggerDependencyInjector_LibraryComponent.builder()
        .androidModule(new AndroidModule(context))
        .build();
  }

  /**
   * Returns the module component.
   */
  LibraryComponent component() {
    return component;
  }
}
