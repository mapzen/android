package com.mapzen.places.api.internal;

import com.mapzen.android.lost.api.ResultCallback;
import com.mapzen.android.lost.api.Status;
import com.mapzen.pelias.Pelias;
import com.mapzen.pelias.gson.Result;
import com.mapzen.places.api.AutocompletePredictionBuffer;
import com.mapzen.places.api.LatLng;
import com.mapzen.places.api.LatLngBounds;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import android.support.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import retrofit2.Callback;
import retrofit2.Response;

public class AutocompletePendingResultTest {

  AutocompletePendingResult result;
  Pelias pelias;
  String query;
  LatLngBounds bounds;

  @Before public void setup() {
    pelias = spy(new Pelias());
    query = "test";
    bounds = new LatLngBounds(new LatLng(-40, -40), new LatLng(40, 40));
    result = new AutocompletePendingResult(pelias, query, bounds, null);
  }

  @Test(expected = RuntimeException.class)
  public void await_shouldThrowException() {
    result.await();
  }

  @Test(expected = RuntimeException.class)
  public void awaitTimeout_shouldThrowException() {
    result.await(1, TimeUnit.SECONDS);
  }

  @Test(expected = RuntimeException.class)
  public void cancel_shouldThrowException() {
    result.cancel();
  }

  @Test(expected = RuntimeException.class)
  public void isCanceled_shouldThrowException() {
    result.isCanceled();
  }

  @Test public void setResultCallback_shouldInvokePeliasSuggest() {
    TestResultCallback callback = new TestResultCallback();
    result.setResultCallback(callback);
    LatLng center = bounds.getCenter();
    verify(pelias).suggest(eq(query), eq(center.getLatitude()), eq(center.getLongitude()),
        any(Callback.class));
  }

  @Test public void setResultCallback_shouldReturnSuccessStatus() {
    doAnswer(new Answer() {
      public Object answer(InvocationOnMock invocation) {
        Object[] args = invocation.getArguments();
        Callback callback = (Callback) args[3];
        final Result result = new Result();
        callback.onResponse(null, Response.success(result));
        return null;
      }
    }).when(pelias).suggest(anyString(), anyDouble(), anyDouble(), any(Callback.class));

    TestResultCallback callback = new TestResultCallback();
    result.setResultCallback(callback);
    assertThat(callback.getBuffer().getStatus().getStatusCode()).isEqualTo(Status.SUCCESS);
  }

  @Test public void setResultCallback_shouldReturnFailureStatus() {
    doAnswer(new Answer() {
      public Object answer(InvocationOnMock invocation) {
        Object[] args = invocation.getArguments();
        Callback callback = (Callback) args[3];
        callback.onFailure(null, null);
        return null;
      }
    }).when(pelias).suggest(anyString(), anyDouble(), anyDouble(), any(Callback.class));

    TestResultCallback callback = new TestResultCallback();
    result.setResultCallback(callback);
    assertThat(callback.getBuffer().getStatus().getStatusCode()).isEqualTo(Status.INTERNAL_ERROR);
  }

  @Test(expected = RuntimeException.class)
  public void setResultCallbackTimeout_shouldThrowException() {
    result.setResultCallback(null, 1, TimeUnit.SECONDS);
  }

  class TestResultCallback implements ResultCallback<AutocompletePredictionBuffer> {

    private AutocompletePredictionBuffer buffer;

    @Override public void onResult(@NonNull AutocompletePredictionBuffer result) {
      buffer = result;
    }

    public AutocompletePredictionBuffer getBuffer() {
      return buffer;
    }
  }
}
