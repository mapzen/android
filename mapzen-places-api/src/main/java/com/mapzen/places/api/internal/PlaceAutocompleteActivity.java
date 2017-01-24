package com.mapzen.places.api.internal;

import com.mapzen.android.lost.api.LostApiClient;
import com.mapzen.android.lost.api.Status;
import com.mapzen.pelias.BoundingBox;
import com.mapzen.pelias.Pelias;
import com.mapzen.pelias.PeliasLocationProvider;
import com.mapzen.pelias.SuggestFilter;
import com.mapzen.pelias.gson.Result;
import com.mapzen.pelias.widget.AutoCompleteAdapter;
import com.mapzen.pelias.widget.AutoCompleteListView;
import com.mapzen.pelias.widget.PeliasSearchView;
import com.mapzen.pelias.widget.SearchSubmitListener;
import com.mapzen.places.api.AutocompleteFilter;
import com.mapzen.places.api.LatLngBounds;
import com.mapzen.places.api.Place;
import com.mapzen.places.api.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import static com.mapzen.places.api.internal.PlaceIntentConsts.EXTRA_BOUNDS;
import static com.mapzen.places.api.internal.PlaceIntentConsts.EXTRA_DETAILS;
import static com.mapzen.places.api.internal.PlaceIntentConsts.EXTRA_FILTER;
import static com.mapzen.places.api.internal.PlaceIntentConsts.EXTRA_MODE;
import static com.mapzen.places.api.internal.PlaceIntentConsts.EXTRA_PLACE;
import static com.mapzen.places.api.internal.PlaceIntentConsts.EXTRA_STATUS;
import static com.mapzen.places.api.internal.PlaceIntentConsts.EXTRA_TEXT;
import static com.mapzen.places.api.ui.PlaceAutocomplete.MODE_FULLSCREEN;
import static com.mapzen.places.api.ui.PlaceAutocomplete.MODE_OVERLAY;
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
    int mode = MODE_OVERLAY;
    if (getIntent().getExtras() != null) {
      mode = getIntent().getExtras().getInt(EXTRA_MODE);
    }

    if (mode == MODE_FULLSCREEN) {
      setTheme(R.style.PlacesFullscreen);
    }

    super.onCreate(savedInstanceState);

    if (mode == MODE_FULLSCREEN) {
      setContentView(R.layout.place_autcomplete_activity_fullscreen);
    } else {
      setContentView(R.layout.place_autocomplete_activity_overlay);
    }

    // TODO inject
    PlaceDetailFetcher detailFetcher = new PeliasPlaceDetailFetcher();
    OnPlaceDetailsFetchedListener detailFetchListener = new AutocompleteDetailFetchListener(this);
    FilterMapper filterMapper = new PeliasFilterMapper();
    presenter = new PlaceAutocompletePresenter(detailFetcher, detailFetchListener, filterMapper);
    presenter.setBounds((LatLngBounds) safeGetExtra(EXTRA_BOUNDS));
    presenter.setFilter((AutocompleteFilter) safeGetExtra(EXTRA_FILTER));
    LostApiClient client = new LostApiClient.Builder(this).build();
    presenter.setLostClient(client);

    AutoCompleteListView listView = (AutoCompleteListView) findViewById(R.id.list_view);
    AutoCompleteAdapter autocompleteAdapter =
        new AutoCompleteAdapter(this, android.R.layout.simple_list_item_1);
    listView.setAdapter(autocompleteAdapter);

    PeliasSearchView peliasSearchView;
    if (mode == MODE_FULLSCREEN) {
      peliasSearchView = new PeliasSearchView(this);
    } else {
      peliasSearchView = (PeliasSearchView) findViewById(R.id.pelias_search_view);
    }

    peliasSearchView.setIconifiedByDefault(false);
    peliasSearchView.setCallback(new Callback<Result>() {
      @Override public void onResponse(Call<Result> call, Response<Result> response) {
        presenter.onResponse(response);
      }

      @Override public void onFailure(Call<Result> call, Throwable t) {
        Log.e(TAG, "Error fetching results", t);
      }
    });
    ImageView searchMagBtn = (ImageView) peliasSearchView.findViewById(R.id.search_mag_icon);
    searchMagBtn.setImageResource(R.drawable.abc_ic_ab_back_material);
    searchMagBtn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        finish();
      }
    });

    if (mode == MODE_FULLSCREEN) {
      ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
          ActionBar.LayoutParams.MATCH_PARENT);
      getSupportActionBar().setCustomView(peliasSearchView, lp);
      getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
    }

    Pelias pelias = new Pelias();
    pelias.setDebug(true);
    pelias.setLocationProvider(new PeliasLocationProvider() {

      @Override public double getLat() {
        return presenter.getLat();
      }

      @Override public double getLon() {
        return presenter.getLon();
      }

      @Override public BoundingBox getBoundingBox() {
        return presenter.getBoundingBox();
      }
    });

    peliasSearchView.setAutoCompleteListView(listView);
    peliasSearchView.setPelias(pelias);
    peliasSearchView.setSuggestFilter(new SuggestFilter() {
      @Override public String getCountryFilter() {
        return presenter.getCountryFilter();
      }

      @Override public String getLayersFilter() {
        return presenter.getLayersFilter();
      }

      //TODO: filter only by wof (but discuss first bc it seriously limits # of results)
      @Override public String getSources() {
        return "wof,osm,oa,gn";
      }
    });
    if (getIntent().getExtras() != null) {
      peliasSearchView.setQuery(getIntent().getExtras().getCharSequence(EXTRA_TEXT), false);
    }
    peliasSearchView.setSearchSubmitListener(new SearchSubmitListener() {
      @Override public boolean searchOnSearchKeySubmit() {
        return false;
      }

      @Override public boolean hideAutocompleteOnSearchSubmit() {
        return false;
      }
    });
  }

  @Override public void setResult(Place place, String details, Status status) {
    final Intent intent = new Intent();
    intent.putExtra(EXTRA_PLACE, place);
    intent.putExtra(EXTRA_DETAILS, details);
    intent.putExtra(EXTRA_STATUS, status);
    setResult(RESULT_OK, intent);
  }

  @Override public void finish() {
    super.finish();
  }

  private Parcelable safeGetExtra(String key) {
    if (getIntent().getExtras() == null) {
      return null;
    }
    return getIntent().getExtras().getParcelable(key);
  }
}
