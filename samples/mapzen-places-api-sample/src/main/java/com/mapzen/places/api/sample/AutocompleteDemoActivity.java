package com.mapzen.places.api.sample;

import com.mapzen.places.api.AutocompleteFilter;
import com.mapzen.places.api.Place;
import com.mapzen.places.api.ui.PlaceAutocomplete;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.mapzen.places.api.AutocompleteFilter.TYPE_FILTER_ADDRESS;
import static com.mapzen.places.api.AutocompleteFilter.TYPE_FILTER_ESTABLISHMENT;
import static com.mapzen.places.api.AutocompleteFilter.TYPE_FILTER_GEOCODE;
import static com.mapzen.places.api.AutocompleteFilter.TYPE_FILTER_REGIONS;
import static com.mapzen.places.api.ui.PlaceAutocomplete.MODE_OVERLAY;

public class AutocompleteDemoActivity extends AppCompatActivity {

  private static final int AUTOCOMPLETE_REQUEST_CODE = 1;

  private Intent autoCompleteIntent;
  private Button launchAutocompleteBtn;
  private TextView placeNameText;
  private TextView placeAddressText;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_autocomplete);
    setupBtn();
    setupTextViews();

    AutocompleteFilter filter = new AutocompleteFilter.Builder()
        .setTypeFilter(TYPE_FILTER_ADDRESS)
        .build();
   autoCompleteIntent = new PlaceAutocomplete.IntentBuilder(MODE_OVERLAY)
        .setFilter(filter)
        .build(this);
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == AUTOCOMPLETE_REQUEST_CODE && resultCode == RESULT_OK) {
      Place place = PlaceAutocomplete.getPlace(this, data);
      placeNameText.setText(place.getName());
      placeAddressText.setText(place.getAddress());
    }
  }

  private void setupBtn() {
    launchAutocompleteBtn = (Button) findViewById(R.id.launch_autocomplete_btn);
    launchAutocompleteBtn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        startActivityForResult(autoCompleteIntent, AUTOCOMPLETE_REQUEST_CODE);
      }
    });
  }

  private void setupTextViews() {
    placeNameText = (TextView) findViewById(R.id.place_name_text);
    placeAddressText = (TextView) findViewById(R.id.place_address_text);
  }
}
