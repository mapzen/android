package com.mapzen.places.api.internal;

import com.mapzen.places.api.Place;

class TestPlacePickerController implements PlacePickerViewController {

  boolean dialogShown = false;
  String dialogTitle = null;
  boolean finished = false;
  boolean myLocationEnabled = false;
  Place place = null;

  @Override public void showDialog(String id, String title) {
    dialogShown = true;
    dialogTitle = title;
  }

  @Override public void updateDialog(String id, String message) {

  }

  @Override public void finishWithPlace(Place place) {
    this.place = place;
    finished = true;
  }

  @Override public void setMyLocationEnabled(boolean enabled) {
    myLocationEnabled = enabled;
  }
}
