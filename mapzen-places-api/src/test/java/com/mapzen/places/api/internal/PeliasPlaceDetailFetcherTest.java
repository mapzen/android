package com.mapzen.places.api.internal;

import com.mapzen.pelias.Pelias;
import com.mapzen.tangram.LngLat;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import retrofit2.Callback;

public class PeliasPlaceDetailFetcherTest {

  Pelias pelias;
  PeliasPlaceDetailFetcher fetcher;

  @Before
  public void setup() {
    pelias = mock(Pelias.class);
    PeliasCallbackHandler callbackHandler = mock(PeliasCallbackHandler.class);
    fetcher = new PeliasPlaceDetailFetcher(pelias, callbackHandler);
  }

  @Test
  public void fetchDetails_coords_shouldCallPelias() throws Exception {
    LngLat coords = new LngLat(1, 2);
    Map props = new HashMap();
    fetcher.fetchDetails(coords, props, null);
    verify(pelias).reverse(eq(coords.latitude), eq(coords.longitude), any(Callback.class));
  }

  @Test
  public void fetchDetails_gid_shouldCallPelias() throws Exception {
    String gid = "123";
    fetcher.fetchDetails(gid, null);
    verify(pelias).place(eq(gid), any(Callback.class));
  }
}
