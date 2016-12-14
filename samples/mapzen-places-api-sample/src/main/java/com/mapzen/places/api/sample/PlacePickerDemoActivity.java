package com.mapzen.places.api.sample;

import com.mapzen.places.api.ui.PlacePicker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class PlacePickerDemoActivity extends AppCompatActivity {

  private static final int PERMISSIONS_REQUEST_CODE = 1;
  private static final int NUMBER_OF_PERMISSIONS = 1;

  private static final int PLACE_PICKER_REQUEST = 1;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      checkRuntimePermissions();
    } else {
      launchPlacePicker();
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
    //TODO check result code and use PlacePicker static methods to get info about selected place
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
    Intent intent = new PlacePicker.IntentBuilder().build(this);
    startActivityForResult(intent, PLACE_PICKER_REQUEST);
  }

  private void showToastNeedPermissions() {
    Toast.makeText(PlacePickerDemoActivity.this, getString(R.string.need_permissions),
        Toast.LENGTH_SHORT).show();
  }
}
