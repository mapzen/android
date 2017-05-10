package com.mapzen.android.lost.internal;

/**
 * Created by sarahlensing on 5/11/17.
 */

public class FusedApiServiceUpdater {

  public static void updateApiService(FusedLocationProviderApiImpl api,
      IFusedLocationProviderService service) {
    api.service = service;
  }
}
