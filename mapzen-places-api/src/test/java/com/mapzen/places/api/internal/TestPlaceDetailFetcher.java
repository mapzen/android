package com.mapzen.places.api.internal;

import java.util.Map;


class TestPlaceDetailFetcher implements PlaceDetailFetcher {

  @Override public void fetchDetails(Map<String, String> properties,
      OnPlaceDetailsFetchedListener listener) {
  }

  @Override public void fetchDetails(String gid, OnPlaceDetailsFetchedListener listener) {
  }
}
