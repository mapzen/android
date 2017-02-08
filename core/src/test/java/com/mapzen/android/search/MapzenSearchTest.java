package com.mapzen.android.search;

import com.mapzen.android.core.MapzenManager;
import com.mapzen.pelias.BoundingBox;
import com.mapzen.pelias.Pelias;
import com.mapzen.pelias.PeliasLocationProvider;
import com.mapzen.pelias.gson.Result;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import android.content.Context;

import static com.mapzen.TestHelper.getMockContext;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapzenSearchTest {
  private Context context;
  private Pelias pelias;
  private MapzenSearch search;

  @Before public void setUp() throws Exception {
    MapzenManager.instance(getMockContext()).setApiKey("fake-mapzen-api-key");
    context = getMockContext();
    pelias = mock(Pelias.class);
    search = new MapzenSearch(context, pelias);
  }

  @After public void tearDown() throws Exception {
    MapzenManager.instance(getMockContext()).setApiKey(null);
  }

  @Test public void shouldCreatePelias() {
    MapzenSearch mzSearch = new MapzenSearch(context, "API_KEY_PARAM_NAME");
    assertThat(mzSearch.getPelias()).isNotNull();
    assertThat(mzSearch.searchInitializer.getRequestHandler().getApiKey())
        .isEqualTo("API_KEY_PARAM_NAME");
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

  private class TestCallback implements Callback<Result> {
    @Override public void onResponse(Call<Result> call, Response<Result> response) {
    }

    @Override public void onFailure(Call<Result> call, Throwable t) {
    }
  }

  private class TestPeliasLocationProvider implements PeliasLocationProvider {

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
