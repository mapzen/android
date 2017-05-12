package com.mapzen.android.location;

import com.mapzen.android.lost.api.LostApiClient;
import com.mapzen.android.lost.api.LostApiClient.ConnectionCallbacks;

import android.content.Context;

/**
 * Manages {@link LostApiClient} instances.
 */
public class LocationFactory {

  private static LostApiClient shared;

  /**
   * Returns shared {@link LostApiClient}.
   *
   * @deprecated This method should not be used outside the SDK. Now that Lost has proper support
   * for multiple location clients exposing a shared client in no longer needed. Behavior has been
   * updated to create a new client instance for each new {@link Context}. This fixes a bug
   * where the shared client was created with a {@link Context} that had been subsequently destroyed
   * and future attempts to bind the fused location service would fail.
   */
  @Deprecated public static LostApiClient sharedClient(Context context) {
    if (shared == null) {
      shared = new LostApiClient.Builder(context).build();
    }

    return shared;
  }

  /**
   * Returns shared {@link LostApiClient} with {@link ConnectionCallbacks}.
   *
   * @deprecated This method should not be used outside the SDK. Now that Lost has proper support
   * for multiple location clients exposing a shared client in no longer needed. Behavior has been
   * updated to create a new client instance for each new {@link Context}. This fixes a bug
   * where the shared client was created with a {@link Context} that had been subsequently destroyed
   * and future attempts to bind the fused location service would fail.
   */
  @Deprecated public static LostApiClient sharedClient(Context context,
      ConnectionCallbacks callbacks) {
    if (shared == null) {
      shared = new LostApiClient.Builder(context).addConnectionCallbacks(callbacks).build();
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
