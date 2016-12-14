package com.mapzen.places.api.internal;

public class TestPlacePickerController implements PlacePickerViewController {

  public boolean dialogShown = false;
  public String dialogTitle = null;
  public boolean finished = false;

  @Override public void showDialog(String title) {
    dialogShown = true;
    dialogTitle = title;
  }

  @Override public void finishWithPlace() {
    finished = true;
  }
}
