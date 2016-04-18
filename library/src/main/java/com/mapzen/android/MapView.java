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
     * Load map asynchronously.
     */
    public void getMapAsync(@NonNull OnMapReadyCallback callback) {
        mapInitializer.init(this, callback);
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
