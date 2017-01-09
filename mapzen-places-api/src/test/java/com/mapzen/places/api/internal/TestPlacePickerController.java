package com.mapzen.places.api.internal;

import com.mapzen.places.api.Place;

public class TestPlacePickerController implements PlacePickerViewController {

  public boolean dialogShown = false;
  public String dialogTitle = null;
  public boolean finished = false;

  @Override public void showDialog(String id, String title) {
    dialogShown = true;
    dialogTitle = title;
  }

  @Override public void updateDialog(String id, String message) {

  }

  @Override public void finishWithPlace(Place place) {
    finished = true;
  }
}
