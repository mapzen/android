package com.mapzen.places.api.internal;

import com.mapzen.android.lost.api.Status;
import com.mapzen.pelias.BoundingBox;
import com.mapzen.pelias.gson.Feature;
import com.mapzen.pelias.gson.Properties;
import com.mapzen.pelias.gson.Result;
import com.mapzen.places.api.LatLng;
import com.mapzen.places.api.LatLngBounds;
import com.mapzen.places.api.Place;

import org.junit.Test;

import android.support.annotation.NonNull;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import retrofit2.Response;

public class PlaceAutocompletePresenterTest {

  private TestPlaceAutocompleteController controller = new TestPlaceAutocompleteController();
  private PlaceDetailFetcher detailFetcher = mock(PlaceDetailFetcher.class);
  private OnPlaceDetailsFetchedListener detailFetchListener = mock(
      OnPlaceDetailsFetchedListener.class);
  private PlaceAutocompletePresenter presenter = new PlaceAutocompletePresenter(controller,
      detailFetcher, detailFetchListener);

  @Test
  public void shouldNotBeNull() throws Exception {
    assertThat(presenter).isNotNull();
  }

  @Test
  public void onResponse_shouldFetchPlaceDetails() throws Exception {
    Response<Result> response = getTestResponse();
    presenter.onResponse(response);
    verify(detailFetcher).fetchDetails("123abc", detailFetchListener);
  }

  @Test
  public void getBoundingBox_shouldReturnCorrectBox() throws Exception {
    BoundingBox boundingBox = presenter.getBoundingBox();
    assertThat(boundingBox.getMinLat()).isEqualTo(0.0);
    assertThat(boundingBox.getMinLon()).isEqualTo(0.0);
    assertThat(boundingBox.getMaxLat()).isEqualTo(50.0);
    assertThat(boundingBox.getMaxLon()).isEqualTo(100.0);
  }

  @Test
  public void getLat_shouldReturnCorrectLat() throws Exception {
    double lat = presenter.getLat();
    assertThat(lat).isEqualTo(25.0);
  }

  @Test
  public void getLon_shouldReturnCorrectLon() throws Exception {
    double lat = presenter.getLon();
    assertThat(lat).isEqualTo(50.0);
  }

  @NonNull private Response<Result> getTestResponse() {
    Result result = new Result();
    Feature feature = new Feature();
    Properties properties = new Properties();
    properties.gid = "123abc";
    properties.name = "Test Name";
    feature.properties = properties;
    result.getFeatures().add(feature);
    return Response.success(result);
  }

  private static class TestPlaceAutocompleteController implements PlaceAutocompleteController {
    private Place result = null;
    private boolean isFinishing = false;
    LatLng sw = new LatLng(0.0, 0.0);
    LatLng ne = new LatLng(50.0, 100.0);
    LatLngBounds bounds = new LatLngBounds(sw, ne);

    @Override public void setResult(Place result, String details, Status status) {
      this.result = result;
    }

    @Override public void finish() {
      isFinishing = true;
    }

    @Override public LatLngBounds getBounds() {
      return bounds;
    }
  }
}
