package com.mapzen.android.core;

import com.mapzen.android.routing.MapzenRouter;

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
  @Singleton @Component(modules = { AndroidModule.class, CommonModule.class })
  public interface LibraryComponent {
    void inject(MapzenRouter router);
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
