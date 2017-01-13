package com.mapzen.places.api.internal;

import com.mapzen.pelias.Pelias;
import com.mapzen.pelias.gson.Feature;
import com.mapzen.pelias.gson.Result;
import com.mapzen.places.api.LatLng;
import com.mapzen.places.api.LatLngBounds;
import com.mapzen.places.api.Place;
import com.mapzen.tangram.LngLat;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.mapzen.places.api.internal.PlacePickerPresenterImpl.PROPERTY_NAME;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Detail fetcher which uses pelias as the backing data source.
 */
class PeliasPlaceDetailFetcher implements PlaceDetailFetcher {

  private Pelias pelias;

  /**
   * Constructs a new object.
   */
  public PeliasPlaceDetailFetcher() {
    pelias = new Pelias();
  }

  @Override public void fetchDetails(LngLat coordinates, final Map<String, String> properties,
      final OnPlaceDetailsFetchedListener listener) {
    pelias.reverse(coordinates.latitude, coordinates.longitude, new Callback<Result>() {
          @Override public void onResponse(Call<Result> call, Response<Result> response) {
            String title = properties.get(PROPERTY_NAME);
            for (Feature feature : response.body().getFeatures()) {
              if (feature.properties.name.equals(title)) {
                Place place = getFetchedPlace(feature);
                String details = getDetails(feature, title);
                listener.onFetchSuccess(place, details);
              }
            }
          }

          @Override public void onFailure(Call<Result> call, Throwable t) {
            listener.onFetchFailure();
          }
        }
    );
  }

  @Override public void fetchDetails(String gid, final OnPlaceDetailsFetchedListener listener) {
    pelias.place(gid, new Callback<Result>() {
      @Override public void onResponse(Call<Result> call, Response<Result> response) {
        Feature feature = response.body().getFeatures().get(0);
        String title = feature.properties.name;
        Place place = getFetchedPlace(feature);
        String details = getDetails(feature, title);
        listener.onFetchSuccess(place, details);
      }

      @Override public void onFailure(Call<Result> call, Throwable t) {
        listener.onFetchFailure();
      }
    });
  }

  //TODO: fill in missing values
  private Place getFetchedPlace(Feature feature) {
    final CharSequence address = feature.properties.label;
    final CharSequence attributions = "";
    final String id = feature.properties.id;
    final LatLng latLng = new LatLng(feature.geometry.coordinates.get(1),
        feature.geometry.coordinates.get(0));
    final Locale locale = Locale.US;
    final CharSequence name = feature.properties.name;
    final CharSequence phoneNumber = "";
    final List<Integer> placeTypes = new ArrayList<>();
    final int priceLevel = 0;
    final float rating = 0;
    final LatLngBounds viewport = new LatLngBounds(latLng, latLng);
    final Uri websiteUri = new Uri.Builder().build();
    return new PlaceImpl.Builder()
        .setAddress(address)
        .setAttributions(attributions)
        .setId(id)
        .setLatLng(latLng)
        .setLocale(locale)
        .setName(name)
        .setPhoneNumber(phoneNumber)
        .setPlaceTypes(placeTypes)
        .setPriceLevel(priceLevel)
        .setRating(rating)
        .setViewPort(viewport)
        .setWebsiteUri(websiteUri)
        .build();
  }

  private String getDetails(Feature feature, String title) {
    String label = feature.properties.label;
    label = label.replace(title + ",", "").trim();
    return title + "\n" + label;
  }
}
