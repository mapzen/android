package com.mapzen.android.core;

import android.content.Context;

/**
 * Public interface to dependency injection framework.
 */
public final class CoreDI {
  /**
   * Initializes dependency injection framework.
   */
  public static void init(Context context) {
    CoreDependencyInjector.createInstance(context);
  }

  /**
   * Provides access to dependency injection modules.
   */
  public static CoreDependencyInjector.CoreLibraryComponent component() {
    return CoreDependencyInjector.getInstance().component();
  }
}
