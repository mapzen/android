package com.mapzen.android.dagger;

import android.content.Context;
import android.content.res.Resources;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dependency injection module for components that depend on an Android {@link Context}.
 */
@Module
public class AndroidModule {
    private final Context context;

    /**
     * Creates module to provide components that depend on an Android {@link Context}. The
     * application context is used so it will persist on configuration changes.
     *
     * @param context The context that should be used to inject Android-specific components.
     */
    AndroidModule(Context context) {
        this.context = context.getApplicationContext();
    }

    /**
     * Provides Android application context.
     */
    @Provides @Singleton public Context provideApplicationContext() {
        return context;
    }

    /**
     * Provide Android application resources.
     */
    @Provides @Singleton public Resources provideResources() {
        return context.getResources();
    }
}
