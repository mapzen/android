package com.mapzen.places.api.internal;

import java.util.Map;

/**
 * Implementation of {@link PlacePickerPresenter}.
 */
public class PlacePickerPresenterImpl implements PlacePickerPresenter {

  private static final String PROPERTY_NAME = "name";

  PlacePickerViewController controller;

  @Override public void setController(PlacePickerViewController controller) {
    this.controller = controller;
  }

  @Override public void onLabelPicked(Map<String, String> properties) {
    String title = properties.get(PROPERTY_NAME);
    //TODO get more details from pelias
    controller.showDialog(title);
  }

  @Override public void onPlaceConfirmed() {
    //TODO pass info so controller can create intent
    controller.finishWithPlace();
  }
}
