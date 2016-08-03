package com.mapzen.android.sample;

import com.mapzen.android.graphics.MapView;
import com.mapzen.android.graphics.MapzenMap;
import com.mapzen.android.graphics.MapzenMapPeliasLocationProvider;
import com.mapzen.android.search.MapzenSearch;
import com.mapzen.android.graphics.OnMapReadyCallback;
import com.mapzen.pelias.gson.Feature;
import com.mapzen.pelias.gson.Result;
import com.mapzen.pelias.widget.AutoCompleteAdapter;
import com.mapzen.pelias.widget.AutoCompleteListView;
import com.mapzen.pelias.widget.PeliasSearchView;
import com.mapzen.tangram.LngLat;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Demonstrates use of {@link MapzenSearch} with a {@link PeliasSearchView} and {@link MapzenMap}.
 * Allows the user to search for a place, displays autocomplete results in a list and search
 * results on a map.
 */
public class MapzenSearchViewActivity extends AppCompatActivity {

  AutoCompleteListView listView;

  MapzenMapPeliasLocationProvider peliasLocationProvider;
  MapzenMap mapzenMap;
  MapzenSearch mapzenSearch;

  AutoCompleteAdapter autocompleteAdapter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search);

    listView = (AutoCompleteListView) findViewById(R.id.list_view);
    autocompleteAdapter = new AutoCompleteAdapter(this, android.R.layout.simple_list_item_1);
    listView.setAdapter(autocompleteAdapter);

    peliasLocationProvider = new MapzenMapPeliasLocationProvider(this);

    MapView mapView = (MapView) findViewById(R.id.map_view);
    mapView.getMapAsync(new OnMapReadyCallback() {
      @Override public void onMapReady(MapzenMap mapzenMap) {
        MapzenSearchViewActivity.this.mapzenMap = mapzenMap;
        configMap();
      }
    });

    mapzenSearch = new MapzenSearch(this);
    mapzenSearch.setLocationProvider(peliasLocationProvider);

    PeliasSearchView peliasSearchView = new PeliasSearchView(this);
    ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
        ActionBar.LayoutParams.MATCH_PARENT);
    getSupportActionBar().setCustomView(peliasSearchView, lp);
    getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
    setupPeliasSearchView(peliasSearchView);
  }

  private void configMap() {
    peliasLocationProvider.setMapzenMap(mapzenMap);
    mapzenMap.setMyLocationEnabled(true);
  }

  private void setupPeliasSearchView(PeliasSearchView searchView) {
    searchView.setAutoCompleteListView(listView);
    searchView.setPelias(mapzenSearch.getPelias());
    searchView.setCallback(new Callback<Result>() {
      @Override public void success(Result result, Response response) {
        mapzenMap.clearSearchResults();
        for (Feature feature : result.getFeatures()) {
          List<Double> coordinates = feature.geometry.coordinates;
          LngLat point = new LngLat(coordinates.get(0), coordinates.get(1));
          mapzenMap.drawSearchResult(point);
        }
      }

      @Override public void failure(RetrofitError error) {
      }
    });
    searchView.setIconifiedByDefault(false);
    searchView.setQueryHint(this.getString(R.string.search_hint));
    searchView.setOnBackPressListener(new PeliasSearchView.OnBackPressListener() {
      @Override public void onBackPressed() {
        mapzenMap.clearSearchResults();
      }
    });
  }
}
