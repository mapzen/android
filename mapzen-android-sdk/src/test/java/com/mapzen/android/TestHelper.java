package com.mapzen.android;

import org.powermock.api.mockito.PowerMockito;

import android.content.Context;
import android.content.res.Resources;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class TestHelper {
  public static Context getMockContext() {
    final Context context = PowerMockito.mock(Context.class);
    when(context.getApplicationContext()).thenReturn(context);
    when(context.getResources()).thenReturn(mock(Resources.class));
    return context;
  }
}
