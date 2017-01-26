package com.mapzen.places.api.internal;

import com.mapzen.pelias.gson.Feature;
import com.mapzen.pelias.gson.Geometry;
import com.mapzen.pelias.gson.Properties;
import com.mapzen.pelias.gson.Result;
import com.mapzen.places.api.Place;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import retrofit2.Response;

public class PeliasCallbackHandlerTest {

  PeliasCallbackHandler callbackHandler = new PeliasCallbackHandler();

  @Test
  public void handleSuccess_titleFeatures_shouldCallListenerForMatchingTitle() throws Exception {
    String title = "Place";
    List<Feature> features = getTestFeatures("NotPlace", "Place");
    TestDetailsListener listener = new TestDetailsListener();
    callbackHandler.handleSuccess(title, features, listener);
    assertThat(listener.place.getName()).isEqualTo(title);
  }

  @Test
  public void handleSuccess_titleFeatures_shouldCreateCorrectDetails() throws Exception {
    String title = "Place";
    List<Feature> features = getTestFeatures("NotPlace", "", "Place", "Place, Label");
    TestDetailsListener listener = new TestDetailsListener();
    callbackHandler.handleSuccess(title, features, listener);
    assertThat(listener.details).isEqualTo("Place\nLabel");
  }

  @Test
  public void handleSuccess_response_shouldCallFetchSuccess() {
    Response<Result> response = getTestResponse("Place", "OtherPlace");
    TestDetailsListener listener = new TestDetailsListener();
    callbackHandler.handleSuccess(response, listener);
    assertThat(listener.place.getName()).isEqualTo("Place");
  }

  @Test
  public void handleSuccess_response_shouldCallFetchSuccessWithCorrectDetails() {
    Response<Result> response = getTestResponse("Place", "Place, Label", "OtherPlace", "");
    TestDetailsListener listener = new TestDetailsListener();
    callbackHandler.handleSuccess(response, listener);
    assertThat(listener.details).isEqualTo("Place\nLabel");
  }

  @Test
  public void handleSuccess_response_shouldCallFetchFailure() {
    TestDetailsListener listener = new TestDetailsListener();
    Place place = new PlaceImpl.Builder().build();
    listener.place = place;
    callbackHandler.handleSuccess(Response.<Result>success(null), listener);
    assertThat(listener.place).isNull();
    listener.place = place;
    Result result = new Result();
    callbackHandler.handleSuccess(Response.success(result), listener);
    assertThat(listener.place).isNull();
    listener.place = place;
    result.setFeatures(new ArrayList<Feature>());
    callbackHandler.handleSuccess(Response.success(result), listener);
    assertThat(listener.place).isNull();
  }

  @Test
  public void handleFailure_shouldCallListenerFetchFailure() {
    TestDetailsListener listener = new TestDetailsListener();
    Place place = new PlaceImpl.Builder().build();
    listener.place = place;
    callbackHandler.handleFailure(listener);
    assertThat(listener.place).isNull();
  }

  class TestDetailsListener implements OnPlaceDetailsFetchedListener {

    private Place place;
    private String details;

    @Override public void onFetchSuccess(Place place, String details) {
      this.place = place;
      this.details = details;
    }

    @Override public void onFetchFailure() {
      this.place = null;
      this.details = null;
    }
  }

  private List<Feature> getTestFeatures(String name1, String details1, String name2,
      String details2) {
    List<Feature> features = new ArrayList<>();
    features.add(getTestFeature(name1, details1));
    features.add(getTestFeature(name2, details2));
    return features;
  }

  private List<Feature> getTestFeatures(String name1, String name2) {
    List<Feature> features = new ArrayList<>();
    features.add(getTestFeature(name1, ""));
    features.add(getTestFeature(name2, ""));
    return features;
  }

  private Feature getTestFeature(String name, String label) {
    Feature feature = new Feature();
    feature.properties = new Properties();
    feature.properties.name = name;
    feature.properties.label = label;
    feature.properties.layer = "venue";
    feature.geometry = new Geometry();
    List<Double> coords = new ArrayList<>();
    coords.add(0.0);
    coords.add(0.0);
    feature.geometry.coordinates = coords;
    return feature;
  }

  private Response<Result> getTestResponse(String name1, String name2) {
    return getTestResponse(name1, "", name2, "");
  }

  private Response<Result> getTestResponse(String name1, String details1, String name2,
      String details2) {
    Result result = new Result();
    result.setFeatures(getTestFeatures(name1, details1, name2, details2));
    Response<Result> response = Response.success(result);
    return response;
  }
}
