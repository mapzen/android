package com.mapzen.android.dagger;

import android.content.Context;

/**
 * Public interface to dependency injection framework.
 */
public final class DI {
    /**
     * Initializes dependency injection framework.
     */
    public static void init(Context context) {
        DependencyInjector.createInstance(context);
    }

    /**
     * Provides access to dependency injection modules.
     */
    public static DependencyInjector.LibraryComponent component() {
        return DependencyInjector.getInstance().component();
    }
}
