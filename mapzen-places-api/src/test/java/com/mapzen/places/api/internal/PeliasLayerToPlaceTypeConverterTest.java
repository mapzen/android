package com.mapzen.places.api.internal;

import org.junit.Test;

import java.util.List;

import static com.mapzen.places.api.Place.TYPE_ADMINISTRATIVE_AREA_LEVEL_1;
import static com.mapzen.places.api.Place.TYPE_COUNTRY;
import static com.mapzen.places.api.Place.TYPE_ESTABLISHMENT;
import static com.mapzen.places.api.Place.TYPE_LOCALITY;
import static com.mapzen.places.api.Place.TYPE_NEIGHBORHOOD;
import static com.mapzen.places.api.Place.TYPE_POINT_OF_INTEREST;
import static com.mapzen.places.api.Place.TYPE_POLITICAL;
import static com.mapzen.places.api.Place.TYPE_ROUTE;
import static com.mapzen.places.api.Place.TYPE_STREET_ADDRESS;
import static com.mapzen.places.api.Place.TYPE_SUBLOCALITY;
import static com.mapzen.places.api.Place.TYPE_SUBLOCALITY_LEVEL_1;
import static com.mapzen.places.api.internal.PeliasLayerConsts.PELIAS_LAYER_ADDRESS;
import static com.mapzen.places.api.internal.PeliasLayerConsts.PELIAS_LAYER_BOROUGH;
import static com.mapzen.places.api.internal.PeliasLayerConsts.PELIAS_LAYER_COUNTRY;
import static com.mapzen.places.api.internal.PeliasLayerConsts.PELIAS_LAYER_COUNTY;
import static com.mapzen.places.api.internal.PeliasLayerConsts.PELIAS_LAYER_LOCALITY;
import static com.mapzen.places.api.internal.PeliasLayerConsts.PELIAS_LAYER_LOCAL_ADMIN;
import static com.mapzen.places.api.internal.PeliasLayerConsts.PELIAS_LAYER_MACRO_COUNTY;
import static com.mapzen.places.api.internal.PeliasLayerConsts.PELIAS_LAYER_MACRO_REGION;
import static com.mapzen.places.api.internal.PeliasLayerConsts.PELIAS_LAYER_NEIGHBOURHOOD;
import static com.mapzen.places.api.internal.PeliasLayerConsts.PELIAS_LAYER_REGION;
import static com.mapzen.places.api.internal.PeliasLayerConsts.PELIAS_LAYER_STREET;
import static com.mapzen.places.api.internal.PeliasLayerConsts.PELIAS_LAYER_VENUE;
import static org.assertj.core.api.Assertions.assertThat;

public class PeliasLayerToPlaceTypeConverterTest {

  PeliasLayerToPlaceTypeConverter converter = new PeliasLayerToPlaceTypeConverter();

  @Test public void getPlaceTypes_shouldReturnCorrectLocalityTypes() {
    List<Integer> types = converter.getPlaceTypes(PELIAS_LAYER_LOCALITY);
    assertThat(types).contains(TYPE_LOCALITY);
    assertThat(types).contains(TYPE_POLITICAL);
    assertThat(types).hasSize(2);
  }

  @Test public void getPlaceTypes_shouldReturnCorrectCountryTypes() {
    List<Integer> types = converter.getPlaceTypes(PELIAS_LAYER_COUNTRY);
    assertThat(types).contains(TYPE_COUNTRY);
    assertThat(types).contains(TYPE_POLITICAL);
    assertThat(types).hasSize(2);
  }

  @Test public void getPlaceTypes_shouldReturnCorrectVenueTypes() {
    List<Integer> types = converter.getPlaceTypes(PELIAS_LAYER_VENUE);
    assertThat(types).contains(TYPE_POINT_OF_INTEREST);
    assertThat(types).contains(TYPE_ESTABLISHMENT);
    assertThat(types).hasSize(2);
  }

  @Test public void getPlaceTypes_shouldReturnCorrectAddressTypes() {
    List<Integer> types = converter.getPlaceTypes(PELIAS_LAYER_ADDRESS);
    assertThat(types).contains(TYPE_STREET_ADDRESS);
    assertThat(types).hasSize(1);
  }

  @Test public void getPlaceTypes_shouldReturnCorrectMacroCountyTypes() {
    List<Integer> types = converter.getPlaceTypes(PELIAS_LAYER_MACRO_COUNTY);
    assertThat(types).contains(TYPE_LOCALITY);
    assertThat(types).contains(TYPE_POLITICAL);
    assertThat(types).hasSize(2);
  }

  @Test public void getPlaceTypes_shouldReturnCorrectCountyTypes() {
    List<Integer> types = converter.getPlaceTypes(PELIAS_LAYER_COUNTY);
    assertThat(types).contains(TYPE_LOCALITY);
    assertThat(types).contains(TYPE_POLITICAL);
    assertThat(types).hasSize(2);
  }

  @Test public void getPlaceTypes_shouldReturnCorrectLocalAdminTypes() {
    List<Integer> types = converter.getPlaceTypes(PELIAS_LAYER_LOCAL_ADMIN);
    assertThat(types).contains(TYPE_LOCALITY);
    assertThat(types).contains(TYPE_POLITICAL);
    assertThat(types).hasSize(2);
  }

  @Test public void getPlaceTypes_shouldReturnCorrectBoroughTypes() {
    List<Integer> types = converter.getPlaceTypes(PELIAS_LAYER_BOROUGH);
    assertThat(types).contains(TYPE_SUBLOCALITY);
    assertThat(types).contains(TYPE_SUBLOCALITY_LEVEL_1);
    assertThat(types).contains(TYPE_POLITICAL);
    assertThat(types).hasSize(3);
  }

  @Test public void getPlaceTypes_shouldReturnCorrectNeighborhoodTypes() {
    List<Integer> types = converter.getPlaceTypes(PELIAS_LAYER_NEIGHBOURHOOD);
    assertThat(types).contains(TYPE_NEIGHBORHOOD);
    assertThat(types).contains(TYPE_POLITICAL);
    assertThat(types).hasSize(2);
  }

  @Test public void getPlaceTypes_shouldReturnCorrectStreetTypes() {
    List<Integer> types = converter.getPlaceTypes(PELIAS_LAYER_STREET);
    assertThat(types).contains(TYPE_ROUTE);
    assertThat(types).hasSize(1);
  }

  @Test public void getPlaceTypes_shouldReturnCorrectRegionTypes() {
    List<Integer> types = converter.getPlaceTypes(PELIAS_LAYER_REGION);
    assertThat(types).contains(TYPE_ADMINISTRATIVE_AREA_LEVEL_1);
    assertThat(types).contains(TYPE_POLITICAL);
    assertThat(types).hasSize(2);
  }

  @Test public void getPlaceTypes_shouldReturnCorrectMacroRegionTypes() {
    List<Integer> types = converter.getPlaceTypes(PELIAS_LAYER_MACRO_REGION);
    assertThat(types).contains(TYPE_ADMINISTRATIVE_AREA_LEVEL_1);
    assertThat(types).contains(TYPE_POLITICAL);
    assertThat(types).hasSize(2);
  }
}
