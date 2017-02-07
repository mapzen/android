package com.mapzen.android.search;

import com.mapzen.android.core.MapzenManager;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SearchRequestHandlerTest {

  SearchRequestHandler adapter = new SearchRequestHandler();

  @Test public void setApiKey_shouldSetKey() {
    adapter.setApiKey("TEST_KEY");
    assertThat(adapter.getApiKey()).isEqualTo("TEST_KEY");
  }

  @Test public void queryParamsForRequest_shouldReturnApiKey() {
    adapter.setApiKey("TEST_KEY");
    assertThat(adapter.queryParamsForRequest().get(MapzenManager.API_KEY_PARAM_NAME))
        .isEqualTo("TEST_KEY");
  }
}
