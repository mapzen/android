package com.mapzen.android.graphics;

import com.mapzen.android.core.DI;
import com.mapzen.android.graphics.model.BubbleWrapStyle;
import com.mapzen.android.graphics.model.MapStyle;
import com.mapzen.tangram.MapController;

import javax.inject.Inject;

/**
 * Class responsible for initializing the map.
 */
public class MapInitializer {

  @Inject TileHttpHandler httpHandler;

  @Inject MapDataManager mapDataManager;

  @Inject MapStateManager mapStateManager;

  /**
   * Creates a new instance.
   */
  @Inject MapInitializer() {
    DI.component().inject(this);
  }

  /**
   * Initialize map for the current {@link MapView} and notify via {@link OnMapReadyCallback}.
   */
  public void init(final MapView mapView, final OnMapReadyCallback callback) {
    init(mapView, new BubbleWrapStyle(), callback);
  }

  /**
   * Initialize map for current {@link MapView} and {@link MapStyle} before notifying via
   * {@link OnMapReadyCallback}.
   */
  public void init(final MapView mapView, MapStyle mapStyle, final OnMapReadyCallback callback) {
    loadMap(mapView, mapStyle, callback);
  }

  /**
   * Initialize map for the current {@link MapView} with given API key and notify via
   * {@link OnMapReadyCallback}.
   */
  public void init(final MapView mapView, String key, final OnMapReadyCallback callback) {
    httpHandler.setApiKey(key);
    loadMap(mapView, new BubbleWrapStyle(), callback);
  }

  /**
   * Initialize map for the current {@link MapView} with given API key and notify via
   * {@link OnMapReadyCallback}.
   */
  public void init(final MapView mapView, String key, MapStyle mapStyle,
      final OnMapReadyCallback callback) {
    httpHandler.setApiKey(key);
    loadMap(mapView, mapStyle, callback);
  }

  private TangramMapView getTangramView(final MapView mapView) {
    return mapView.getTangramMapView();
  }

  private void loadMap(final MapView mapView, MapStyle mapStyle,
      final OnMapReadyCallback callback) {
    if (mapStateManager.getPersistMapState()) {
      MapStyle restoredMapStyle = mapStateManager.getMapStyle();
      mapStyle = restoredMapStyle;
    }
    mapStateManager.setMapStyle(mapStyle);
    loadMap(mapView, mapStyle.getSceneFile(), callback);
  }

  private void loadMap(final MapView mapView, String sceneFile, final OnMapReadyCallback callback) {
    getTangramView(mapView).getMapAsync(new com.mapzen.tangram.MapView.OnMapReadyCallback() {
      @Override public void onMapReady(MapController mapController) {
        mapController.setHttpHandler(httpHandler);
        callback.onMapReady(
            new MapzenMap(mapController, new OverlayManager(mapView, mapController, mapDataManager,
                mapStateManager), mapStateManager));
      }
    }, sceneFile);
  }
}
