package com.mapzen.places.api.sample;

import com.mapzen.android.lost.api.Status;
import com.mapzen.places.api.AutocompleteFilter;
import com.mapzen.places.api.Place;
import com.mapzen.places.api.ui.PlaceAutocompleteFragment;
import com.mapzen.places.api.ui.PlaceSelectionListener;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import static com.mapzen.places.api.AutocompleteFilter.TYPE_FILTER_ADDRESS;

/**
 * Demonstrates how to launch the Places Autocomplete UI.
 */
public class AutocompleteDemoActivity extends AppCompatActivity implements PlaceSelectionListener {

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_autocomplete);

    PlaceAutocompleteFragment fragment = (PlaceAutocompleteFragment) getFragmentManager().
        findFragmentById(R.id.place_autocomplete_fragment);
    AutocompleteFilter filter = new AutocompleteFilter.Builder()
        .setTypeFilter(TYPE_FILTER_ADDRESS)
        .build();
    fragment.setPlaceSelectionListener(this);
    fragment.setFilter(filter);
  }

  @Override public void onPlaceSelected(Place place) {
    // Do something with selected place
  }

  @Override public void onError(Status status) {

  }
}
