package com.mapzen.android.search;

import com.mapzen.pelias.BoundingBox;
import com.mapzen.pelias.Pelias;
import com.mapzen.pelias.PeliasLocationProvider;
import com.mapzen.pelias.gson.Result;

import org.junit.Test;

import android.content.Context;

import static com.mapzen.android.TestHelper.getMockContext;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MapzenSearchTest {

  Context context = getMockContext();
  Pelias pelias = mock(Pelias.class);
  MapzenSearch search = new MapzenSearch(context, pelias);

  @Test public void shouldCreatePelias() {
    MapzenSearch mzSearch = new MapzenSearch(context, "API_KEY");
    assertThat(mzSearch.getPelias()).isNotNull();
    assertThat(mzSearch.searchInitializer.getRequestHandler().getApiKey()).isEqualTo("API_KEY");
  }

  @Test public void suggest_shouldCallPelias() {
    TestCallback callback = new TestCallback();
    search.suggest("query", callback);
    verify(search.getPelias()).suggest("query", callback);

    search.suggest("query", 40, 70, callback);
    verify(search.getPelias()).suggest("query", 40, 70, callback);
  }

  @Test public void search_shouldCallPelias() {
    TestCallback callback = new TestCallback();
    search.search("query", callback);
    verify(search.getPelias()).search("query", callback);

    BoundingBox boundingBox = new BoundingBox(0, 0, 40, 70);
    search.search("query", boundingBox, callback);
    verify(search.getPelias()).search("query", boundingBox, callback);

    search.search("query", 40, 70, callback);
    verify(search.getPelias()).search("query", 40, 70, callback);
  }

  @Test public void reverse_shouldCallPelias() {
    TestCallback callback = new TestCallback();
    search.reverse(40, 70, callback);
    verify(search.getPelias()).reverse(40, 70, callback);
  }

  @Test public void place_shouldCallPelias() {
    TestCallback callback = new TestCallback();
    search.place("123", callback);
    verify(search.getPelias()).place("123", callback);
  }

  @Test public void setLocationProvider_shouldCallPelias() {
    TestPeliasLocationProvider provider = new TestPeliasLocationProvider();
    search.setLocationProvider(provider);
    verify(search.getPelias()).setLocationProvider(provider);
  }

  class TestCallback implements Callback<Result> {

    @Override public void success(Result result, Response response) {

    }

    @Override public void failure(RetrofitError error) {

    }
  }

  class TestPeliasLocationProvider implements PeliasLocationProvider {

    @Override public double getLat() {
      return 0;
    }

    @Override public double getLon() {
      return 0;
    }

    @Override public BoundingBox getBoundingBox() {
      return null;
    }
  }
}
