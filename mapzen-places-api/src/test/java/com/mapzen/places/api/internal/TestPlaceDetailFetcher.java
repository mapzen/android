package com.mapzen.places.api.internal;

import com.mapzen.tangram.LngLat;

import java.util.Map;


public class TestPlaceDetailFetcher implements PlaceDetailFetcher {

  @Override public void fetchDetails(LngLat coordinates, Map<String, String> properties,
      OnPlaceDetailsFetchedListener listener) {

  }

  @Override public void fetchDetails(String gid, OnPlaceDetailsFetchedListener listener) {

  }
}
