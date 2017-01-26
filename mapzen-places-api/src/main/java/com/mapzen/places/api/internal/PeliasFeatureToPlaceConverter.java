package com.mapzen.places.api.internal;

import com.mapzen.pelias.gson.Feature;
import com.mapzen.places.api.LatLng;
import com.mapzen.places.api.LatLngBounds;
import com.mapzen.places.api.Place;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Handles converting Pelias {@link Feature} objects into {@link Place} objects for
 * {@link PeliasCallbackHandler}.
 */
class PeliasFeatureToPlaceConverter {

  private PeliasLayerToPlaceTypeConverter layerConverter;

  /**
   * Constructor.
   */
  PeliasFeatureToPlaceConverter() {
    layerConverter = new PeliasLayerToPlaceTypeConverter();
  }

  /**
   * Construct a {@link Place} object from the given feature.
   * @param feature
   * @return
   */
  Place getFetchedPlace(Feature feature) {
    //TODO: fill in missing values
    final CharSequence address = feature.properties.label;
    final String id = feature.properties.id;
    final LatLng latLng = new LatLng(feature.geometry.coordinates.get(1),
        feature.geometry.coordinates.get(0));
    final Locale locale = Locale.US;
    final CharSequence name = feature.properties.name;
    final List<Integer> placeTypes = new ArrayList<>();
    if (feature.properties.layer != null) {
      placeTypes.addAll(layerConverter.getPlaceTypes(feature.properties.layer));
    }
    final LatLngBounds viewport = new LatLngBounds(latLng, latLng);
    return new PlaceImpl.Builder()
        .setAddress(address)
        .setAttributions(null)
        .setId(id)
        .setLatLng(latLng)
        .setLocale(locale)
        .setName(name)
        .setPhoneNumber(null)
        .setPlaceTypes(placeTypes)
        .setPriceLevel(0)
        .setRating(0)
        .setViewPort(viewport)
        .setWebsiteUri(null)
        .build();
  }
}
