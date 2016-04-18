package com.mapzen.android;

import com.mapzen.android.dagger.DI;
import com.mapzen.tangram.MapController;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;

import javax.inject.Inject;

/**
 * Wrapper for Tangram MapView that initializes {@link MapController} for client applications.
 */
public class MapView extends com.mapzen.tangram.MapView {
    @Inject MapInitializer mapInitializer;

    MapController mapController;

    /**
     * Create new instance.
     */
    public MapView(Context context) {
        super(context);
        initDI(context);
    }

    /**
     * Create new instance.
     */
    public MapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDI(context);
    }

    private void initDI(Context context) {
        DI.init(context);
        DI.component().inject(this);
    }

    /**
     * Load map asynchronously using APK key declared in XML resources. For example:
     * {@code <string name="vector_tiles_key">[YOUR_VECTOR_TILES_KEY]</string>}
     *
     * @param callback listener to be invoked when map is initialized and ready to use.
     */
    public void getMapAsync(@NonNull OnMapReadyCallback callback) {
        mapInitializer.init(this, callback);
    }

    /**
     * Load map asynchronously using given API key.
     *
     * @param callback listener to be invoked when map is initialized and ready to use.
     * @param key vector tiles API key that should be used to load map tiles.
     */
    public void getMapAsync(@NonNull OnMapReadyCallback callback, @NonNull String key) {
        mapInitializer.init(this, callback, key);
    }

    /**
     * Interface for receiving a {@code MapController} once it is ready to be used.
     */
    public interface OnMapReadyCallback {
        /**
         * Called when the map is ready to be used.
         * @param mapController A non-null {@code MapController} instance for this {@code MapView}.
         */
        void onMapReady(MapController mapController);
    }
}
