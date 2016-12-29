package com.mapzen.places.api.ui;

import com.mapzen.places.api.R;
import com.mapzen.places.api.internal.ui.PlaceAutocompleteActivity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A fragment that provides auto-completion for places.
 */
public class PlaceAutocompleteFragment extends Fragment {
  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.place_autocomplete_fragment, container, false);
    final View input = view.findViewById(R.id.place_autocomplete_search_input);
    input.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        getActivity().startActivity(new Intent(getActivity(), PlaceAutocompleteActivity.class));
      }
    });
    return view;
  }
}
