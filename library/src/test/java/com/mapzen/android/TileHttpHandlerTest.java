package com.mapzen.android;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class TileHttpHandlerTest {
  private static final String TEST_URL = "http://vector.mapzen.com/osm/all/1/2/3.json";
  private static final String TEST_KEY = "vector-tiles-test";

  private TileHttpHandler tileHttpHandler = new TileHttpHandler(TEST_KEY);

  @Test public void shouldNotBeNull() throws Exception {
    assertThat(tileHttpHandler).isNotNull();
  }

  @Test public void onRequest_shouldAppendApiKey() throws Exception {
    TestRequestBuilder requestBuilder = new TestRequestBuilder();
    tileHttpHandler = new TileHttpHandler(TEST_KEY, requestBuilder);
    tileHttpHandler.onRequest(TEST_URL, new TestCallback());
    assertThat(requestBuilder.request.uri().toString()).contains(TileHttpHandler.PARAM_API_KEY);
    assertThat(requestBuilder.request.uri().toString()).contains(TEST_KEY);
  }

  private class TestRequestBuilder extends Request.Builder {
    private Request request;

    @Override public Request build() {
      request = super.build();
      return request;
    }
  }

  private class TestCallback implements Callback {
    @Override public void onFailure(Request request, IOException e) {
    }

    @Override public void onResponse(Response response) throws IOException {
    }
  }
}
