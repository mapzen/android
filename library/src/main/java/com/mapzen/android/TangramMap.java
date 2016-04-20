package com.mapzen.android;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Entry point for interaction with Tangram map. Wrapped inside of {@link MapView}.
 * Do not use this class directly, instead access it via {@link MapView}.
 */
public class TangramMap extends com.mapzen.tangram.MapView {

    /**
     * Creates a new {@link TangramMap}.
     * @param context
     */
    public TangramMap(Context context) {
        super(context);
    }

    /**
     * Creates a new {@link TangramMap}.
     * @param context
     */
    public TangramMap(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
