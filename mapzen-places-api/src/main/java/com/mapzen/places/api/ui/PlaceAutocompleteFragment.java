package com.mapzen.places.api.ui;

import com.mapzen.places.api.AutocompleteFilter;
import com.mapzen.places.api.LatLngBounds;
import com.mapzen.places.api.Place;
import com.mapzen.places.api.R;
import com.mapzen.places.api.internal.PlaceAutocompleteView;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static android.app.Activity.RESULT_OK;

/**
 * A fragment that provides auto-completion for places.
 */
public class PlaceAutocompleteFragment extends Fragment {

  private PlaceAutocompleteView autocompleteView;
  private PlaceSelectionListener listener;

  @Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater,
      @NonNull ViewGroup container, Bundle savedInstanceState) {
    autocompleteView = (PlaceAutocompleteView) inflater.inflate(
        R.layout.place_autocomplete_fragment, container, false);
    autocompleteView.setFragment(this);
    return autocompleteView;
  }

  @Override public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    if (resultCode == RESULT_OK) {
      Place place = PlaceAutocomplete.getPlace(this.getActivity(), data);
      String address = null;
      if (place.getAddress() != null) {
        address = place.getAddress().toString();
      }
      autocompleteView.setText(address);
      if (listener != null) {
        listener.onPlaceSelected(place);
      }
    }
  }

  public void setPlaceSelectionListener(@Nullable PlaceSelectionListener listener) {
    this.listener = listener;
  }

  /**
   * Set the {@link PlaceAutocompleteView}'s bounds bias. If not set, you should
   * request android.permission.ACCESS_COARSE_LOCATION in your manifest so that the map will be
   * centered on the user's current location. If this permission is not present results will not
   * be limited by any bounds.
   * @param boundsBias
   */
  public void setBoundsBias(@Nullable LatLngBounds boundsBias) {
    autocompleteView.setBoundsBias(boundsBias);
  }

  /**
   * Set the {@link PlaceAutocompleteView}'s filter.
   * @param filter
   */
  public void setFilter(@Nullable AutocompleteFilter filter) {
    autocompleteView.setFilter(filter);
  }

  /**
   * Set the {@link PlaceAutocompleteView}'s hint.
   * @param hint
   */
  public void setHint(@Nullable CharSequence hint) {
    autocompleteView.setHint(hint);
  }

  /**
   * Set the {@link PlaceAutocompleteView}'s text.
   * @param text
   */
  public void setText(@Nullable CharSequence text) {
    autocompleteView.setText(text);
  }
}
