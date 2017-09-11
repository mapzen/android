package com.mapzen.android.search;

import com.mapzen.android.core.MapzenManager;
import com.mapzen.pelias.Pelias;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.mapzen.TestHelper.getMockContext;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SearchInitializerTest {
  private SearchInitializer searchInitializer;
  private MapzenSearch search;
  private Pelias pelias;

  @Before public void setUp() throws Exception {
    MapzenManager.instance(getMockContext()).setApiKey("fake-mapzen-api-key");
    searchInitializer = new SearchInitializer(getMockContext());
    pelias = mock(Pelias.class);
    search = new MapzenSearch(getMockContext(), pelias);
  }

  @After public void tearDown() throws Exception {
    MapzenManager.instance(getMockContext()).setApiKey(null);
  }

  @Test public void initSearch_shouldSetHttpHandler() {
    searchInitializer.initSearch(search);
    verify(pelias).setRequestHandler(searchInitializer.getRequestHandler().searchHandler());
  }
}
