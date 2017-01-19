package com.mapzen.places.api.internal;

import com.mapzen.android.lost.api.LocationServices;
import com.mapzen.android.lost.api.LostApiClient;
import com.mapzen.android.lost.internal.FusedLocationProviderApiImpl;
import com.mapzen.android.lost.internal.FusedLocationProviderService;
import com.mapzen.pelias.BoundingBox;
import com.mapzen.pelias.gson.Feature;
import com.mapzen.pelias.gson.Properties;
import com.mapzen.pelias.gson.Result;
import com.mapzen.places.api.LatLng;
import com.mapzen.places.api.LatLngBounds;

import org.junit.Test;

import android.location.Location;
import android.support.annotation.NonNull;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import retrofit2.Response;

public class PlaceAutocompletePresenterTest {

  private PlaceDetailFetcher detailFetcher = mock(PlaceDetailFetcher.class);
  private OnPlaceDetailsFetchedListener detailFetchListener = mock(
      OnPlaceDetailsFetchedListener.class);
  private PlaceAutocompletePresenter presenter = new PlaceAutocompletePresenter(detailFetcher,
      detailFetchListener, null);

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
    LatLng sw = new LatLng(0.0, 0.0);
    LatLng ne = new LatLng(50.0, 100.0);
    presenter.setBounds(new LatLngBounds(sw, ne));
    BoundingBox boundingBox = presenter.getBoundingBox();
    assertThat(boundingBox.getMinLat()).isEqualTo(0.0);
    assertThat(boundingBox.getMinLon()).isEqualTo(0.0);
    assertThat(boundingBox.getMaxLat()).isEqualTo(50.0);
    assertThat(boundingBox.getMaxLon()).isEqualTo(100.0);
  }

  @Test
  public void getBoundingBox_lostClient_shouldReturnCorrectBox() throws Exception {
    FusedLocationProviderService.FusedLocationProviderBinder stubBinder =
          mock(FusedLocationProviderService.FusedLocationProviderBinder.class);
    FusedLocationProviderService mockService = mock(FusedLocationProviderService.class);
      when(stubBinder.getService()).thenReturn(mockService);
    FusedLocationProviderApiImpl impl =
        (FusedLocationProviderApiImpl) LocationServices.FusedLocationApi;
    impl.onServiceConnected(stubBinder);

    LostApiClient client = mock(LostApiClient.class);
    Location location = mock(Location.class);
    when(location.getLatitude()).thenReturn(40.0);
    when(location.getLongitude()).thenReturn(70.0);
    when(mockService.getLastLocation(client)).thenReturn(location);
    presenter.setLostClient(client);
    when(client.isConnected()).thenReturn(true);
    BoundingBox boundingBox = presenter.getBoundingBox();
    double boundsRadius = 0.02;
    assertThat(boundingBox.getMinLat() + boundsRadius).isEqualTo(location.getLatitude());
    assertThat(boundingBox.getMinLon() + boundsRadius).isEqualTo(location.getLongitude());
    assertThat(boundingBox.getMaxLat() - boundsRadius).isEqualTo(location.getLatitude());
    assertThat(boundingBox.getMaxLon() - boundsRadius).isEqualTo(location.getLongitude());
  }

  @Test
  public void getLat_shouldReturnCorrectLat() throws Exception {
    LatLng sw = new LatLng(0.0, 0.0);
    LatLng ne = new LatLng(50.0, 100.0);
    presenter.setBounds(new LatLngBounds(sw, ne));
    double lat = presenter.getLat();
    assertThat(lat).isEqualTo(25.0);
  }

  @Test
  public void getLat_noBoundingBox_noLostClient_shouldReturnDefaultLat() throws Exception {
    double lat = presenter.getLat();
    assertThat(lat).isEqualTo(0.0);
  }

  @Test
  public void getLon_shouldReturnCorrectLon() throws Exception {
    LatLng sw = new LatLng(0.0, 0.0);
    LatLng ne = new LatLng(50.0, 100.0);
    presenter.setBounds(new LatLngBounds(sw, ne));
    double lat = presenter.getLon();
    assertThat(lat).isEqualTo(50.0);
  }

  @Test
  public void getLon_noBoundingBox_noLostClient_shouldReturnDefaultLat() throws Exception {
    double lat = presenter.getLon();
    assertThat(lat).isEqualTo(0.0);
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

}
