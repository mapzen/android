package com.mapzen.places.api.ui;

import com.mapzen.places.api.R;
import com.mapzen.places.api.internal.PlaceAutocompleteActivity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static android.app.Activity.RESULT_OK;

/**
 * A fragment that provides auto-completion for places.
 */
public class PlaceAutocompleteFragment extends Fragment {
  private PlaceAutocompleteView autocompleteView;

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    autocompleteView = (PlaceAutocompleteView) inflater.inflate(
        R.layout.place_autocomplete_fragment, container, false);
    autocompleteView.setFragment(this);
    return autocompleteView;
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode == RESULT_OK) {
      // TODO: Replace with PlaceAutocomplete.getPlace(Context, Intent)
      autocompleteView.setText(data.getStringExtra("mapzen_place"));
    }
  }
}
