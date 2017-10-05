package com.mapzen.places.api.internal;

import com.mapzen.android.graphics.MapView;
import com.mapzen.android.graphics.OnMapReadyCallback;
import com.mapzen.places.api.Place;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PlacePickerPresenterTest {

  PlacePickerPresenter presenter;
  TestPlacePickerController controller;

  @Before public void setup() {
    presenter = new PlacePickerPresenterImpl(new TestPlaceDetailFetcher());
    controller = new TestPlacePickerController();
    presenter.setController(controller);
  }

  @Test public void getMapAsync_shouldLoadPlacesBubbleWrapStyle() throws Exception {
    MapView mapView = mock(MapView.class);
    OnMapReadyCallback callback = mock(OnMapReadyCallback.class);
    presenter.getMapAsync(mapView, callback);
    verify(mapView).getMapAsync(any(PlacesBubbleWrapStyle.class), eq(callback));
  }

  @Test public void onLabelPicked_shouldShowDialog() {
    HashMap<String, String> properties = new HashMap<>();
    properties.put("name", "Hanjan");
    presenter.onLabelPicked(properties);
    assertThat(controller.dialogShown).isTrue();
    assertThat(controller.dialogTitle).isEqualTo("Hanjan");
  }

  @Test public void onPlaceConfirmed_shouldFinishActivity() {
    presenter.onPlaceConfirmed();
    assertThat(controller.finished).isTrue();
  }

  @Test public void onAutocompletePlacePicked_shouldShowDialog() {
    Place place = new PlaceImpl.Builder().build();
    presenter.onAutocompletePlacePicked(place, "details");
    assertThat(controller.dialogShown).isTrue();
  }

  @Test public void onAutocompletePlacePicked_shouldPersistPlaceOnRotation() throws Exception {
    Place place = new PlaceImpl.Builder().build();
    presenter.onAutocompletePlacePicked(place, "details");
    presenter = new PlacePickerPresenterImpl(new TestPlaceDetailFetcher());
    presenter.setController(controller);
    presenter.onPlaceConfirmed();
    assertThat(controller.place).isEqualTo(place);
  }

  @Test public void onHideView_shouldDisableLocationServices() throws Exception {
    controller.myLocationEnabled = true;
    presenter.onHideView();
    assertThat(controller.myLocationEnabled).isFalse();
  }

  @Test public void onShowView_shouldEnableLocationServices() throws Exception {
    controller.myLocationEnabled = false;
    presenter.onShowView();
    assertThat(controller.myLocationEnabled).isTrue();
  }
}
