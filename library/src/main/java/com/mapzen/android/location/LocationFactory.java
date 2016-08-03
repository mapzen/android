package com.mapzen.android.location;

import com.mapzen.android.lost.api.LostApiClient;

import android.content.Context;

/**
 * Manages {@link LostApiClient} instances.
 */
public class LocationFactory {

  private static LostApiClient shared;

  /**
   * Returns shared {@link LostApiClient}.
   */
  public static LostApiClient sharedClient(Context context) {
    if (shared == null) {
      shared = new LostApiClient.Builder(context).build();
    }
    return shared;
  }

  /**
   * Returns a new instance of {@link LostApiClient}.
   */
  public LostApiClient getApiClient(Context context) {
    return new LostApiClient.Builder(context).build();
  }
}
