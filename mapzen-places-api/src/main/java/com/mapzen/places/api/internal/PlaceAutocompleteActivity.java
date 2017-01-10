package com.mapzen.places.api.internal;

import com.mapzen.pelias.BoundingBox;
import com.mapzen.pelias.Pelias;
import com.mapzen.pelias.PeliasLocationProvider;
import com.mapzen.pelias.gson.Result;
import com.mapzen.pelias.widget.AutoCompleteAdapter;
import com.mapzen.pelias.widget.AutoCompleteListView;
import com.mapzen.pelias.widget.PeliasSearchView;
import com.mapzen.places.api.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Activity that provides auto-completion for places.
 */
public class PlaceAutocompleteActivity extends AppCompatActivity {
  private static final String TAG  = PlaceAutocompleteActivity.class.getSimpleName();

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.place_autcomplete_activity);

    AutoCompleteListView listView = (AutoCompleteListView) findViewById(R.id.list_view);
    AutoCompleteAdapter autocompleteAdapter =
        new AutoCompleteAdapter(this, android.R.layout.simple_list_item_1);
    listView.setAdapter(autocompleteAdapter);

    PeliasSearchView peliasSearchView = new PeliasSearchView(this);
    peliasSearchView.setIconifiedByDefault(false);
    peliasSearchView.setCallback(new Callback<Result>() {
      @Override public void onResponse(Call<Result> call, Response<Result> response) {
        final Intent intent = new Intent();

        // TODO: Fetch place details
        intent.putExtra("mapzen_place", response.body().getFeatures().get(0).properties.name);
        setResult(RESULT_OK, intent);
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
}
