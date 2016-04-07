package com.mapzen.android;

import com.mapzen.android.lost.api.LostApiClient;

import android.content.Context;

/**
 * Manages {@link LostApiClient} instances.
 */
public class LocationManager {

    private static LostApiClient shared;

    /**
     * Returns shared {@link LostApiClient}.
     * @param context
     * @return
     */
    public static LostApiClient sharedClient(Context context) {
        if (shared == null) {
            shared = new LostApiClient.Builder(context).build();
        }
        return shared;
    }

    /**
     * Returns a new instance of {@link LostApiClient}.
     * @param context
     * @return
     */
    public LostApiClient getApiClient(Context context) {
        return new LostApiClient.Builder(context).build();
    }
}
