package com.mapzen.places.api.internal.ui;

import com.mapzen.pelias.BoundingBox;
import com.mapzen.pelias.Pelias;
import com.mapzen.pelias.PeliasLocationProvider;
import com.mapzen.pelias.PeliasRequestHandler;
import com.mapzen.pelias.widget.AutoCompleteAdapter;
import com.mapzen.pelias.widget.AutoCompleteListView;
import com.mapzen.pelias.widget.PeliasSearchView;
import com.mapzen.places.api.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class PlaceAutocompleteActivity extends AppCompatActivity {
  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.place_autcomplete_activity);

    AutoCompleteListView listView = (AutoCompleteListView) findViewById(R.id.list_view);
    AutoCompleteAdapter autocompleteAdapter =
        new AutoCompleteAdapter(this, android.R.layout.simple_list_item_1);
    listView.setAdapter(autocompleteAdapter);

    PeliasSearchView peliasSearchView = new PeliasSearchView(this);
    peliasSearchView.setIconifiedByDefault(false);
    ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
        ActionBar.LayoutParams.MATCH_PARENT);
    getSupportActionBar().setCustomView(peliasSearchView, lp);
    getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

    Pelias pelias = new Pelias();
    pelias.setDebug(true);
    pelias.setRequestHandler( new PeliasRequestHandler() {
      @Override public Map<String, String> headersForRequest() {
        return null;
      }

      @Override public Map<String, String> queryParamsForRequest() {
        HashMap<String, String> params = new HashMap<>();
        params.put("api_key",  "mapzen-ZTA7CTR");
        return params;
      }
    });

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
