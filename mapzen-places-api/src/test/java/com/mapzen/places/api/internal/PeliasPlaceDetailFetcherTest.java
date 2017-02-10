package com.mapzen.places.api.internal;

import com.mapzen.pelias.Pelias;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.mapzen.places.api.internal.PlaceDetailFetcher.PELIAS_GID_BASE;
import static com.mapzen.places.api.internal.PlaceDetailFetcher.PELIAS_GID_NODE;
import static com.mapzen.places.api.internal.PlaceDetailFetcher.PELIAS_GID_WAY;
import static com.mapzen.places.api.internal.PlaceDetailFetcher.PROP_AREA;
import static com.mapzen.places.api.internal.PlaceDetailFetcher.PROP_ID;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import retrofit2.Callback;

public class PeliasPlaceDetailFetcherTest {

  private Pelias pelias;
  private PeliasPlaceDetailFetcher fetcher;

  @Before public void setup() {
    pelias = mock(Pelias.class);
    PeliasCallbackHandler callbackHandler = mock(PeliasCallbackHandler.class);
    fetcher = new PeliasPlaceDetailFetcher(pelias, callbackHandler);
  }

  @Test public void fetchDetails_props_shouldCallPelias() throws Exception {
    Map props = new HashMap();
    fetcher.fetchDetails(props, null);
    verify(pelias).place(anyString(), any(Callback.class));
  }

  @Test public void fetchDetails_gid_shouldCallPelias() throws Exception {
    String gid = "123";
    fetcher.fetchDetails(gid, null);
    verify(pelias).place(eq(gid), any(Callback.class));
  }

  @Test public void fetchDetails_props_shouldConstructGidWithWayIfHasArea() throws Exception {
    Map props = new HashMap();
    props.put(PROP_ID, "123.000");
    props.put(PROP_AREA, "456.000");
    fetcher.fetchDetails(props, null);
    verify(pelias).place(eq(PELIAS_GID_BASE + PELIAS_GID_WAY + "123"), any(Callback.class));
  }

  @Test public void fetchDetails_props_shouldConstructGidWithWayIfHasAreaYes() throws Exception {
    Map props = new HashMap();
    props.put(PROP_ID, "123.000");
    props.put(PROP_AREA, "yes");
    fetcher.fetchDetails(props, null);
    verify(pelias).place(eq(PELIAS_GID_BASE + PELIAS_GID_WAY + "123"), any(Callback.class));
  }

  @Test public void fetchDetails_props_shouldConstructGidWithNodeIfNoArea() throws Exception {
    Map props = new HashMap();
    props.put(PROP_ID, "123.000");
    fetcher.fetchDetails(props, null);
    verify(pelias).place(eq(PELIAS_GID_BASE + PELIAS_GID_NODE + "123"), any(Callback.class));
  }
}
