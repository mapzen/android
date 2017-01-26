package com.mapzen.android.search;

import org.junit.Test;

import android.content.Context;

import static com.mapzen.TestHelper.getMockContext;
import static org.assertj.core.api.Assertions.assertThat;

public class SearchInitializerTest {

  SearchInitializer searchInitializer = new SearchInitializer();
  Context context = getMockContext();
  MapzenSearch search = new MapzenSearch(context);

  @Test public void initSearch_shouldSetApiKey() {
    searchInitializer.initSearch(search, "TEST_API_KEY");
    assertThat(searchInitializer.getRequestHandler().getApiKey()).isEqualTo("TEST_API_KEY");
  }
}
