package com.mapzen.android.location;

import com.mapzen.android.lost.api.LostApiClient;

import org.junit.Test;
import org.mockito.Mockito;

import android.content.Context;

import static org.assertj.core.api.Assertions.assertThat;

public class LocationFactoryTest {

  @Test public void sharedClient_shouldNotBeNull() throws Exception {
    assertThat(LocationFactory.sharedClient(Mockito.mock(Context.class))).isNotNull();
  }

  @Test public void sharedClientWithCallbacks_shouldNotBeNull() throws Exception {
    assertThat(LocationFactory.sharedClient(Mockito.mock(Context.class),
        new TestConnectionCallbacks())).isNotNull();
  }

  @Test public void getApiClient_shouldNotBeNull() throws Exception {
    assertThat(new LocationFactory().getApiClient(Mockito.mock(Context.class))).isNotNull();
  }

  @Test public void sharedClient_shouldReturnSameClientForSameContext() throws Exception {
    Context context = Mockito.mock(Context.class);
    assertThat(LocationFactory.sharedClient(context))
        .isEqualTo(LocationFactory.sharedClient(context));
  }

  @Test public void sharedClientWithCallbacks_shouldReturnSameClientForSameContext()
      throws Exception {
    Context context = Mockito.mock(Context.class);
    LostApiClient.ConnectionCallbacks callbacks = new TestConnectionCallbacks();
    assertThat(LocationFactory.sharedClient(context, callbacks))
        .isEqualTo(LocationFactory.sharedClient(context, callbacks));
  }

  @Test public void sharedClient_shouldReturnNewClientForNewContext() throws Exception {
    assertThat(LocationFactory.sharedClient(Mockito.mock(Context.class)))
        .isNotEqualTo(LocationFactory.sharedClient(Mockito.mock(Context.class)));
  }

  @Test public void sharedClientWithCallbacks_shouldReturnNewClientForNewContext()
      throws Exception {
    LostApiClient.ConnectionCallbacks callbacks = new TestConnectionCallbacks();
    assertThat(LocationFactory.sharedClient(Mockito.mock(Context.class), callbacks))
        .isNotEqualTo(LocationFactory.sharedClient(Mockito.mock(Context.class), callbacks));
  }

  private class TestConnectionCallbacks implements LostApiClient.ConnectionCallbacks {
    @Override public void onConnected() {
    }

    @Override public void onConnectionSuspended() {
    }
  }
}
