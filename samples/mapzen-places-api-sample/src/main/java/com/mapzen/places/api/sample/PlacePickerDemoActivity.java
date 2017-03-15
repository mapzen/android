package com.mapzen.places.api.sample;

import com.mapzen.places.api.LatLng;
import com.mapzen.places.api.LatLngBounds;
import com.mapzen.places.api.Place;
import com.mapzen.places.api.ui.PlacePicker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Demonstrates how to launch the Places PlacePicker UI.
 */
public class PlacePickerDemoActivity extends AppCompatActivity {

  private static final int PERMISSIONS_REQUEST_CODE = 1;
  private static final int NUMBER_OF_PERMISSIONS = 1;
  private static final int PLACE_PICKER_REQUEST = 1;

  private static boolean isPickingPlace = false;

  TextView placeName;
  TextView placeAddress;
  TextView placeAttribution;
  TextView placeBounds;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.place_picker_demo);
    setupTextViews();

    if (!isPickingPlace) {
      safeLaunchPicker();
    }
  }

  @Override public void onRequestPermissionsResult(int requestCode, String[] permissions,
      int[] grantResults) {
    switch (requestCode) {
      case PERMISSIONS_REQUEST_CODE:
        if (grantResults.length == NUMBER_OF_PERMISSIONS
            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          launchPlacePicker();
        } else {
          showToastNeedPermissions();
        }
        break;
      default:
        showToastNeedPermissions();
        break;
    }
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    isPickingPlace = false;

    if (requestCode == PLACE_PICKER_REQUEST && resultCode == RESULT_OK) {
      Place place = PlacePicker.getPlace(this, data);
      placeName.setText(place.getName());
      placeAddress.setText(place.getAddress());

      LatLngBounds bounds = PlacePicker.getLatLngBounds(data);
      placeBounds.setText("SW lat:" + bounds.getSouthwest().getLatitude() + "\nSW lng:" +
          bounds.getSouthwest().getLongitude() + "\nNE lat:" + bounds.getNortheast().getLatitude() +
          "\nNE lng:" + bounds.getNortheast().getLongitude());
    }
  }

  private void setupTextViews() {
    placeName = (TextView) findViewById(R.id.place_name_val);
    placeAddress = (TextView) findViewById(R.id.place_address_val);
    placeAttribution = (TextView) findViewById(R.id.place_attribution_val);
    placeBounds = (TextView) findViewById(R.id.place_bounds_val);
  }

  private void safeLaunchPicker() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      checkRuntimePermissions();
    } else {
      launchPlacePicker();
    }
  }

  private boolean permissionNotGranted() {
    return (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(this,
        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED);
  }

  private void requestPermission() {
    ActivityCompat.requestPermissions(this, new String[] {
        Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
    }, PERMISSIONS_REQUEST_CODE);
  }

  private void checkRuntimePermissions() {
    if (permissionNotGranted()) {
      requestPermission();
    } else {
      launchPlacePicker();
    }
  }

  private void launchPlacePicker() {
    LatLng southwest = new LatLng(42.80749, -73.14697);
    LatLng northeast = new LatLng(44.98423, -71.58691);
    Intent intent = new PlacePicker.IntentBuilder()
        .setLatLngBounds(new LatLngBounds(southwest, northeast))
        .build(this);
    isPickingPlace = true;
    startActivityForResult(intent, PLACE_PICKER_REQUEST);
  }

  private void showToastNeedPermissions() {
    Toast.makeText(PlacePickerDemoActivity.this, getString(R.string.need_permissions),
        Toast.LENGTH_SHORT).show();
  }
}
