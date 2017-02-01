package com.mapzen.android.graphics;

import com.mapzen.tangram.MapController;
import com.mapzen.tangram.SceneUpdate;

import org.mockito.Mockito;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;

import java.util.List;

public class TestTangramMapView extends TangramMapView {

  private List<SceneUpdate> sceneUpdates;

  public TestTangramMapView(Context context) {
    super(context);
  }

  public TestTangramMapView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override public void getMapAsync(@NonNull final OnMapReadyCallback callback,
      @NonNull final String sceneFilePath) {
    MapController mapController = Mockito.mock(MapController.class);
    callback.onMapReady(mapController);
  }

  @Override public void getMapAsync(@NonNull OnMapReadyCallback callback,
      @NonNull String sceneFilePath, List<SceneUpdate> sceneUpdates) {
    this.sceneUpdates = sceneUpdates;
    MapController mapController = Mockito.mock(MapController.class);
    callback.onMapReady(mapController);
  }

  public List<SceneUpdate> getSceneUpdates() {
    return sceneUpdates;
  }
}
