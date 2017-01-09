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
  private TextView input;

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.place_autocomplete_fragment, container, false);
    input = (TextView) view.findViewById(R.id.place_autocomplete_search_input);
    input.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        startActivityForResult(new Intent(getActivity(), PlaceAutocompleteActivity.class), 0);
      }
    });
    return view;
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode == RESULT_OK) {
      // TODO: Replace with PlaceAutocomplete.getPlace(Context, Intent)
      input.setText(data.getStringExtra("mapzen_place"));
    }
  }
}
