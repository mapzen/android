package com.mapzen.android.search;

import com.mapzen.android.core.MapzenManager;
import com.mapzen.android.core.TestVals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.mapzen.TestHelper.getMockContext;
import static com.mapzen.android.core.GenericHttpHandler.HEADER_USER_AGENT;
import static com.mapzen.android.core.GenericHttpHandler.USER_AGENT;
import static com.mapzen.android.core.MapzenManager.API_KEY_PARAM_NAME;
import static org.assertj.core.api.Assertions.assertThat;

public class MapzenSearchHttpHandlerTest {

  Map testParams;
  Map testHeaders;

  MapzenSearchHttpHandler handler;

  @Before public void setup() {
    MapzenManager.instance(getMockContext()).setApiKey("test-api-key");
    handler = new MapzenSearchHttpHandler(getMockContext()) {
      @Override public Map<String, String> queryParamsForRequest() {
        return testParams;
      }

      @Override public Map<String, String> headersForRequest() {
        return testHeaders;
      }
    };
  }

  @After public void takedown() {
    MapzenManager.instance(getMockContext()).setApiKey(null);
  }

  @Test public void shouldPullApiKey() {
    assertThat(handler.searchHandler().getApiKey()).isEqualTo("test-api-key");
  }

  @Test public void shouldUpdateApiKeyAutomatically() {
    MapzenManager.instance(getMockContext()).setApiKey("updated-api-key");
    assertThat(handler.searchHandler().getApiKey()).isEqualTo("updated-api-key");
  }

  @Test public void setApiKey_shouldSetKey() {
    handler.searchHandler().setApiKey("TEST_KEY");
    assertThat(handler.searchHandler().getApiKey()).isEqualTo("TEST_KEY");
  }

  @Test public void queryParamsForRequest_shouldReturnApiKey() {
    handler.searchHandler().setApiKey("TEST_KEY");
    Map expected = new HashMap();
    expected.put(API_KEY_PARAM_NAME, "TEST_KEY");
    assertThat(handler.searchHandler().queryParamsForRequest()).isEqualTo(expected);
  }

  @Test public void headersForRequest_shouldIncludeUserAgent() {
    Map headers = handler.searchHandler().headersForRequest();
    Map expected = new HashMap();
    expected.put(HEADER_USER_AGENT, USER_AGENT);
    assertThat(headers).isEqualTo(expected);
  }

  @Test public void queryParamsForRequest_shouldReturnAllParams() {
    testParams = TestVals.testParams();
    handler.searchHandler().setApiKey("TEST_KEY");
    Map expected = new HashMap();
    expected.put(API_KEY_PARAM_NAME, "TEST_KEY");
    expected.putAll(testParams);
    assertThat(handler.searchHandler().queryParamsForRequest()).isEqualTo(expected);
  }

  @Test public void headersForRequest_shouldReturnAllHeaders() {
    testHeaders = TestVals.testHeaders();
    Map headers = handler.searchHandler().headersForRequest();
    Map expected = new HashMap();
    expected.put(HEADER_USER_AGENT, USER_AGENT);
    expected.putAll(testHeaders);
    assertThat(headers).isEqualTo(expected);
  }
}
