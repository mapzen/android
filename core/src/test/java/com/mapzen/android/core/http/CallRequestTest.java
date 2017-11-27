package com.mapzen.android.core.http;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import retrofit2.Call;

public class CallRequestTest {

  Call call = mock(Call.class);
  CallRequest request = new CallRequest(call);

  @Test
  public void cancel_shouldCancelCall() throws Exception {
    request.cancel();
    verify(call).cancel();
  }

  @Test
  public void isCanceled_shouldCallCall() throws Exception {
    request.isCanceled();
    verify(call).isCanceled();
  }
}
