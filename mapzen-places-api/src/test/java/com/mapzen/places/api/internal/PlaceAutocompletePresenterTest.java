package com.mapzen.places.api.internal;

import com.mapzen.android.lost.api.Status;
import com.mapzen.pelias.gson.Feature;
import com.mapzen.pelias.gson.Properties;
import com.mapzen.pelias.gson.Result;
import com.mapzen.places.api.Place;

import org.junit.Test;

import android.support.annotation.NonNull;

import static org.assertj.core.api.Assertions.assertThat;
import retrofit2.Response;

public class PlaceAutocompletePresenterTest {

  private TestPlaceAutocompleteController controller = new TestPlaceAutocompleteController();
  private PlaceAutocompletePresenter presenter = new PlaceAutocompletePresenter(controller);

  @Test
  public void shouldNotBeNull() throws Exception {
    assertThat(presenter).isNotNull();
  }

  @Test
  public void onResponse_shouldSetResult() throws Exception {
    Response<Result> response = getTestResponse();
    presenter.onResponse(response);
    assertThat(controller.result.getName()).isEqualTo("Test Name");
  }

  @Test
  public void onResponse_shouldFinish() throws Exception {
    Response<Result> response = getTestResponse();
    presenter.onResponse(response);
    assertThat(controller.isFinishing).isTrue();
  }

  @NonNull private Response<Result> getTestResponse() {
    Result result = new Result();
    Feature feature = new Feature();
    Properties properties = new Properties();
    properties.name = "Test Name";
    feature.properties = properties;
    result.getFeatures().add(feature);
    return Response.success(result);
  }

  private static class TestPlaceAutocompleteController implements PlaceAutocompleteController {
    private Place result = null;
    private boolean isFinishing = false;

    @Override public void setResult(Place result, Status status) {
      this.result = result;
    }

    @Override public void finish() {
      isFinishing = true;
    }
  }
}
