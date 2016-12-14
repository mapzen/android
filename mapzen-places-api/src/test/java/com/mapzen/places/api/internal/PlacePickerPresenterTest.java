package com.mapzen.places.api.internal;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class PlacePickerPresenterTest {

  PlacePickerPresenter presenter;
  TestPlacePickerController controller;

  @Before public void setup() {
    presenter = new PlacePickerPresenterImpl();
    controller = new TestPlacePickerController();
    presenter.setController(controller);
  }

  @Test public void onLabelPicked_shouldShowDialog() {
    HashMap<String, String> properties = new HashMap<>();
    properties.put("name", "Hanjan");
    presenter.onLabelPicked(properties);
    assertThat(controller.dialogShown).isTrue();
    assertThat(controller.dialogTitle).isEqualTo("Hanjan");
  }

  @Test public void onPlaceSelected_shouldFinishActivity() {
    presenter.onPlaceSelected();
    assertThat(controller.finished).isTrue();
  }
}
