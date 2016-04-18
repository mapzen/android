package com.mapzen.android.dagger;

import com.mapzen.android.TileHttpHandler;

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
    private static final String API_KEY_RES_NAME = "vector_tiles_key";
    private static final String API_KEY_RES_TYPE = "string";

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
     * Provides Android application resources.
     */
    @Provides @Singleton public Resources provideResources() {
        return context.getResources();
    }

    /**
     * Provides HTTP handler to append API key to outgoing vector tile requests.
     */
    @Provides @Singleton public TileHttpHandler provideTileHttpHandler(Resources res) {
        final String packageName = context.getPackageName();
        final int apiKeyId = res.getIdentifier(API_KEY_RES_NAME, API_KEY_RES_TYPE, packageName);
        final String apiKey = res.getString(apiKeyId);
        return new TileHttpHandler(apiKey);
    }
}
