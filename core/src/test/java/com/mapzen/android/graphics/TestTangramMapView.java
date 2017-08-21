package com.mapzen.android.graphics;

import com.mapzen.tangram.MapController;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import android.content.Context;
import android.util.AttributeSet;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class TestTangramMapView extends TangramMapView {

  TestMapView mapView;
  OnMapReadyCallback callback;

  public TestTangramMapView(Context context, TestMapView mapView, OnMapReadyCallback callback) {
    super(context);
    this.mapView = mapView;
    this.callback = callback;
  }

  public TestTangramMapView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override public MapController getMap(final MapController.SceneLoadListener listener) {
    MapController controller = Mockito.mock(MapController.class);
    when(controller.loadSceneFileAsync(anyString(), any(List.class))).thenAnswer(
        new Answer<Void>() {
      @Override public Void answer(InvocationOnMock invocation) throws Throwable {
        mapView.getMapAsync(callback);
        return null;
      }
    });
    return controller;
  }
}
