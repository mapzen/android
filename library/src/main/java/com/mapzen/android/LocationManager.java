package com.mapzen.android;

import com.mapzen.android.lost.api.LostApiClient;

import android.content.Context;

/**
 *
 */
public class LocationManager {

    private static LostApiClient shared;

    public static LostApiClient sharedClient(Context context) {
        if (shared == null) {
            return shared = new LostApiClient.Builder(context).build();
        }
        return shared;
    }

    public LostApiClient getApiClient(Context context) {
        return new LostApiClient.Builder(context).build();
    }
}
