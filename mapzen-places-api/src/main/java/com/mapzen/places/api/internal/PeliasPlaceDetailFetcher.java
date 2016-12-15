package com.mapzen.places.api.internal;

import com.mapzen.pelias.Pelias;
import com.mapzen.pelias.gson.Feature;
import com.mapzen.pelias.gson.Result;
import com.mapzen.tangram.LngLat;

import java.util.Map;

import static com.mapzen.places.api.internal.PlacePickerPresenterImpl.PROPERTY_NAME;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Detail fetcher which uses pelias as the backing data source
 */
public class PeliasPlaceDetailFetcher implements PlaceDetailFetcher {

  Pelias pelias;

  public PeliasPlaceDetailFetcher() {
    pelias = new Pelias();
  }

  @Override public void fetchDetails(LngLat coordinates, final Map<String, String> properties,
      final OnPlaceDetailsFetchedListener listener) {
    pelias.reverse(coordinates.latitude, coordinates.longitude, new Callback<Result>() {
      @Override public void success(Result result, Response response) {
        String title = properties.get(PROPERTY_NAME);
        for (Feature feature :result.getFeatures()) {
          if (feature.properties.name.equals(title)) {
            String label = result.getFeatures().get(0).properties.label;
            label.replace(title + ", ", "");
            listener.onPlaceDetailsFetched(title + "\n" + label);
          }
        }
      }

      @Override public void failure(RetrofitError error) {
      }
    });
  }
}
