package com.mapzen.places.api.internal;

import java.util.Map;

public interface PlacePickerPresenter {
  void setController(PlacePickerViewController controller);
  void onLabelPicked(Map<String, String> properties);
  void onPlaceSelected();
}
