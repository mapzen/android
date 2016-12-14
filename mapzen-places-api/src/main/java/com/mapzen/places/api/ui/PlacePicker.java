package com.mapzen.places.api.ui;

import com.mapzen.places.api.LatLngBounds;
import com.mapzen.places.api.Place;
import com.mapzen.places.api.internal.ui.PlacePickerActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class PlacePicker {

  public static String getAttributions(Intent intent) {
    throw new RuntimeException("Not implemented yet");
  }

  public static LatLngBounds getLatLngBounds(Intent intent) {
    throw new RuntimeException("Not implemented yet");
  }

  public static Place getPlace(Context context, Intent intent) {
    throw new RuntimeException("Not implemented yet");
  }

  public static class IntentBuilder {

    private LatLngBounds bounds;

    public Intent build(Activity activity) {
      Intent intent = new Intent(activity, PlacePickerActivity.class);
      //TODO: add bounds if not null
      return intent;
    }

    public PlacePicker.IntentBuilder setLatLngBounds(LatLngBounds latLngBounds) {
      this.bounds = latLngBounds;
      return this;
    }
  }
}
