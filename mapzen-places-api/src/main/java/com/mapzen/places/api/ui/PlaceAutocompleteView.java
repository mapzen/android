package com.mapzen.places.api.ui;

import com.mapzen.places.api.R;
import com.mapzen.places.api.internal.PlaceAutocompleteActivity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Compound view which displays search icon, autocomplete text view and clear button.
 */
public class PlaceAutocompleteView extends LinearLayout {

  TextView input;

  public PlaceAutocompleteView(Context context) {
    super(context);
    setup(context);
  }

  public PlaceAutocompleteView(Context context, AttributeSet attrs) {
    super(context, attrs);
    setup(context);
  }

  public PlaceAutocompleteView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    setup(context);
  }

  private void setup(Context context) {
    LayoutInflater inflater = (LayoutInflater) context.getSystemService(
        Context.LAYOUT_INFLATER_SERVICE);
    inflater.inflate(R.layout.place_autocomplete_view, this, true);
    setBackgroundColor(getResources().getColor(R.color.mz_white));
  }

  public void setActivity(Activity a) {
    final Activity activity = a;
    input = (TextView) findViewById(R.id.place_autocomplete_search_input);
    input.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        activity.startActivityForResult(new Intent(activity.getBaseContext(),
            PlaceAutocompleteActivity.class), 0);
      }
    });
  }

  public void setFragment(Fragment f) {
    final Fragment fragment = f;
    input = (TextView) findViewById(R.id.place_autocomplete_search_input);
    input.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        fragment.startActivityForResult(new Intent(fragment.getActivity().getBaseContext(),
            PlaceAutocompleteActivity.class), 0);
      }
    });
  }

  public void setText(String s) {
    input.setText(s);
  }
}
