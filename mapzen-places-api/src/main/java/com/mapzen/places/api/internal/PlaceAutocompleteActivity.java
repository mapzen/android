package com.mapzen.places.api.internal;

import com.mapzen.android.lost.api.Status;
import com.mapzen.pelias.BoundingBox;
import com.mapzen.pelias.Pelias;
import com.mapzen.pelias.PeliasLocationProvider;
import com.mapzen.pelias.gson.Result;
import com.mapzen.pelias.widget.AutoCompleteAdapter;
import com.mapzen.pelias.widget.AutoCompleteListView;
import com.mapzen.pelias.widget.PeliasSearchView;
import com.mapzen.places.api.Place;
import com.mapzen.places.api.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import static com.mapzen.places.api.internal.PlaceIntentConsts.EXTRA_PLACE;
import static com.mapzen.places.api.ui.PlaceAutocomplete.EXTRA_STATUS;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Activity that provides auto-completion for places.
 */
public class PlaceAutocompleteActivity extends AppCompatActivity
    implements PlaceAutocompleteController {
  private static final String TAG  = PlaceAutocompleteActivity.class.getSimpleName();

  private PlaceAutocompletePresenter presenter;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.place_autcomplete_activity);

    // TODO inject
    presenter = new PlaceAutocompletePresenter(this);

    AutoCompleteListView listView = (AutoCompleteListView) findViewById(R.id.list_view);
    AutoCompleteAdapter autocompleteAdapter =
        new AutoCompleteAdapter(this, android.R.layout.simple_list_item_1);
    listView.setAdapter(autocompleteAdapter);

    PeliasSearchView peliasSearchView = new PeliasSearchView(this);
    peliasSearchView.setIconifiedByDefault(false);
    peliasSearchView.setCallback(new Callback<Result>() {
      @Override public void onResponse(Call<Result> call, Response<Result> response) {
        presenter.onResponse(response);

        finish();
      }

      @Override public void onFailure(Call<Result> call, Throwable t) {
        Log.e(TAG, "Error fetching results", t);
      }
    });

    ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
        ActionBar.LayoutParams.MATCH_PARENT);
    getSupportActionBar().setCustomView(peliasSearchView, lp);
    getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

    Pelias pelias = new Pelias();
    pelias.setDebug(true);
    pelias.setLocationProvider(new PeliasLocationProvider() {

      // TODO: Replace with real location integration
      @Override public double getLat() {
        return 40.7443;
      }

      @Override public double getLon() {
        return  -73.9903;
      }

      @Override public BoundingBox getBoundingBox() {
        return null;
      }
    });

    peliasSearchView.setAutoCompleteListView(listView);
    peliasSearchView.setPelias(pelias);
  }

  @Override public void setResult(Place place, Status status) {
    final Intent intent = new Intent();
    intent.putExtra(EXTRA_PLACE, (Parcelable) place);
    //TODO: update LOST, make Status Parcelable
    //intent.putExtra(EXTRA_STATUS, (Parcelable) status);
    setResult(RESULT_OK, intent);
  }

  @Override public void finish() {
    super.finish();
  }
}
