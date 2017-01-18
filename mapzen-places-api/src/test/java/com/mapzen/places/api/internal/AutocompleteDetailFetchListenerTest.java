package com.mapzen.places.api.internal;

import com.mapzen.android.lost.api.Status;
import com.mapzen.places.api.Place;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AutocompleteDetailFetchListenerTest {

  PlaceAutocompleteController controller = mock(PlaceAutocompleteController.class);
  OnPlaceDetailsFetchedListener listener = new AutocompleteDetailFetchListener(controller);

  @Test public void onFetchSuccess_shouldSetResult() throws Exception {
    Place place = mock(Place.class);
    String details = "details";
    listener.onFetchSuccess(place, details);
    verify(controller).setResult(eq(place), eq(details), any(Status.class));
  }

  @Test public void onFetchSuccess_shouldHaveSuccessStatus() throws Exception {
    Place place = mock(Place.class);
    String details = "details";
    ArgumentCaptor<Status> status = ArgumentCaptor.forClass(Status.class);
    listener.onFetchSuccess(place, details);
    verify(controller).setResult(eq(place), eq(details), status.capture());
    assertEquals(Status.SUCCESS, status.getValue().getStatusCode());
  }

  @Test public void onFetchSuccess_shouldFinish() throws Exception {
    Place place = mock(Place.class);
    listener.onFetchSuccess(place, "details");
    verify(controller).finish();
  }

  @Test public void onFetchFailure_shouldSetResult() throws Exception {
    listener.onFetchFailure();
    verify(controller).setResult(isNull(Place.class), isNull(String.class), any(Status.class));
  }

  @Test public void onFetchFailure_shouldHaveInternalErrorStatus() throws Exception {
    ArgumentCaptor<Status> status = ArgumentCaptor.forClass(Status.class);
    listener.onFetchFailure();
    verify(controller).setResult(isNull(Place.class), isNull(String.class), status.capture());
    assertEquals(Status.INTERNAL_ERROR, status.getValue().getStatusCode());
  }

  @Test public void onFetchFailure_shouldFinish() throws Exception {
    listener.onFetchFailure();
    verify(controller).finish();
  }
}
