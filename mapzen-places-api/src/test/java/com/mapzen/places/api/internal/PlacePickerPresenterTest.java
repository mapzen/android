package com.mapzen.places.api.internal;

import com.mapzen.places.api.Place;
import com.mapzen.tangram.LngLat;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class PlacePickerPresenterTest {

  PlacePickerPresenter presenter;
  TestPlacePickerController controller;

  @Before public void setup() {
    presenter = new PlacePickerPresenterImpl(new TestPlaceDetailFetcher());
    controller = new TestPlacePickerController();
    presenter.setController(controller);
  }

  @Test public void onLabelPicked_shouldShowDialog() {
    HashMap<String, String> properties = new HashMap<>();
    properties.put("name", "Hanjan");
    presenter.onLabelPicked(new LngLat(0.0, 0.0), properties);
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
}
