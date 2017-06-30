package com.mapzen.android.search;

import com.mapzen.android.core.MapzenManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.mapzen.TestHelper.getMockContext;
import static org.assertj.core.api.Assertions.assertThat;

public class SearchInitializerTest {
  private SearchInitializer searchInitializer;
  private MapzenSearch search;

  @Before public void setUp() throws Exception {
    MapzenManager.instance(getMockContext()).setApiKey("fake-mapzen-api-key");
    searchInitializer = new SearchInitializer();
    search = new MapzenSearch(getMockContext());
  }

  @After public void tearDown() throws Exception {
    MapzenManager.instance(getMockContext()).setApiKey(null);
  }

  @Test public void initSearch_shouldSetApiKey() {
    searchInitializer.initSearch(search, "TEST_API_KEY");
    assertThat(searchInitializer.getRequestHandler().searchHandler().getApiKey()).isEqualTo(
        "TEST_API_KEY");
  }
}
