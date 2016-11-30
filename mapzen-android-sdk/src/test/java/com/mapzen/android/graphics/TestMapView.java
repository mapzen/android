package com.mapzen.android.graphics;

import com.mapzen.tangram.MapController;

import android.content.Context;
import android.support.annotation.NonNull;

import static com.mapzen.android.TestHelper.getMockContext;
import static org.mockito.Mockito.mock;

public class TestMapView extends MapView {
  private Runnable action;

  TestMapView() {
    super(getMockContext());
  }

  @Override public void getMapAsync(@NonNull String sceneFilePath,
      @NonNull OnMapReadyCallback callback) {
    callback.onMapReady(new MapzenMap(mock(MapView.class), mock(MapController.class),
        mock(OverlayManager.class), mock(MapStateManager.class), mock(LabelPickHandler.class)));
  }

  @Override public TangramMapView getTangramMapView() {
    return new TestTangramMapView(mock(Context.class));
  }

  @Override public boolean post(Runnable action) {
    this.action = action;
    new Thread(action).run();
    return true;
  }

  public Runnable getAction() {
    return action;
  }
}
