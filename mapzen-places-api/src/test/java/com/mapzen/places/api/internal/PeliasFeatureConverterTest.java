package com.mapzen.places.api.internal;

import com.mapzen.pelias.SimpleFeature;
import com.mapzen.pelias.gson.Feature;
import com.mapzen.places.api.Place;

import org.junit.Test;

import java.util.Locale;

import static com.mapzen.places.api.Place.TYPE_ESTABLISHMENT;
import static com.mapzen.places.api.Place.TYPE_POINT_OF_INTEREST;
import static org.assertj.core.api.Assertions.assertThat;

public class PeliasFeatureConverterTest {

  PeliasFeatureConverter converter = new PeliasFeatureConverter();

  @Test
  public void getFetchedPlace_shouldHaveCorrectAddress() throws Exception {
    Feature feature = getTestFeature();
    Place place = converter.getFetchedPlace(feature);
    assertThat(place.getAddress()).isEqualTo("label");
  }

  @Test(expected = UnsupportedOperationException.class)
  public void getFetchedPlace_shouldNotHaveAttributions() throws Exception {
    Feature feature = getTestFeature();
    Place place = converter.getFetchedPlace(feature);
    place.getAttributions();
  }

  @Test
  public void getFetchedPlace_shouldHaveCorrectId() throws Exception {
    Feature feature = getTestFeature();
    Place place = converter.getFetchedPlace(feature);
    assertThat(place.getId()).isEqualTo("id");
  }

  @Test
  public void getFetchedPlace_shouldHaveCorrectLatLng() throws Exception {
    Feature feature = getTestFeature();
    Place place = converter.getFetchedPlace(feature);
    assertThat(place.getLatLng().getLatitude()).isEqualTo(40.0);
    assertThat(place.getLatLng().getLongitude()).isEqualTo(70.0);
  }

  @Test
  public void getFetchedPlace_shouldHaveCorrectLocale() throws Exception {
    Feature feature = getTestFeature();
    Place place = converter.getFetchedPlace(feature);
    assertThat(place.getLocale()).isEqualTo(Locale.US);
  }

  @Test
  public void getFetchedPlace_shouldHaveCorrectName() throws Exception {
    Feature feature = getTestFeature();
    Place place = converter.getFetchedPlace(feature);
    assertThat(place.getName()).isEqualTo("name");
  }

  @Test(expected = UnsupportedOperationException.class)
  public void getFetchedPlace_shouldNotHavePhoneNumber() throws Exception {
    Feature feature = getTestFeature();
    Place place = converter.getFetchedPlace(feature);
    place.getPhoneNumber();
  }

  @Test
  public void getFetchedPlace_shouldHaveCorrectPlaceTypes() throws Exception {
    Feature feature = getTestFeature();
    Place place = converter.getFetchedPlace(feature);
    assertThat(place.getPlaceTypes()).contains(TYPE_POINT_OF_INTEREST);
    assertThat(place.getPlaceTypes()).contains(TYPE_ESTABLISHMENT);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void getFetchedPlace_shouldNotHavePriceLevel() throws Exception {
    Feature feature = getTestFeature();
    Place place = converter.getFetchedPlace(feature);
    place.getPriceLevel();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void getFetchedPlace_shouldNotHaveRating() throws Exception {
    Feature feature = getTestFeature();
    Place place = converter.getFetchedPlace(feature);
    place.getRating();
  }

  @Test
  public void getFetchedPlace_shouldHaveCorrectViewport() throws Exception {
    Feature feature = getTestFeature();
    Place place = converter.getFetchedPlace(feature);
    assertThat(place.getViewport().getCenter().getLatitude()).isEqualTo(40.0);
    assertThat(place.getViewport().getCenter().getLongitude()).isEqualTo(70.0);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void getFetchedPlace_shouldNotHaveWebsiteUri() throws Exception {
    Feature feature = getTestFeature();
    Place place = converter.getFetchedPlace(feature);
    place.getWebsiteUri();
  }

  private Feature getTestFeature() {
    return SimpleFeature.create("id", "gid", "name", "country", "co", "region", "reg", "county",
        "localadmin", "locality", "neighborhood", 1.0, "label", "venue", 40.0, 70.0).toFeature();
  }
}
