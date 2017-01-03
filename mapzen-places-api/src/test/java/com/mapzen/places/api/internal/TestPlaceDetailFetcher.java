package com.mapzen.places.api.internal;

import com.mapzen.places.api.LatLng;
import com.mapzen.places.api.LatLngBounds;
import com.mapzen.places.api.Place;
import com.mapzen.tangram.LngLat;

import java.util.Locale;
import java.util.Map;


public class TestPlaceDetailFetcher implements PlaceDetailFetcher {

  @Override public void fetchDetails(LngLat coordinates, Map<String, String> properties,
      OnPlaceDetailsFetchedListener listener) {

  }

  @Override public Place getFetchedPlace() {
    return new Place("", "", "", new LatLng(0.0, 0.0), Locale.US, "", "", null, 0, 0.0f,
        new LatLngBounds(new LatLng(0.0, 0.0), new LatLng(0.0, 0.0)), null);
  }
}
