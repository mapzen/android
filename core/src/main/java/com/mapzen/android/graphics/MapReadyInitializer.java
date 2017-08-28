package com.mapzen.android.graphics;

import com.mapzen.android.core.MapzenManager;
import com.mapzen.android.graphics.model.BitmapMarkerManager;
import com.mapzen.tangram.MapController;

import java.util.Locale;

/**
 * Class to handle creating a {@link MapzenMap} and invoking {@link OnMapReadyCallback}.
 */
class MapReadyInitializer {

  /**
   * Creates a {@link MapzenMap} configured using the provided parameters and invokes the
   * {@link OnMapReadyCallback} with this new map.
   * @param mapView
   * @param mapzenMapHttpHandler
   * @param callback
   * @param mapDataManager
   * @param mapStateManager
   * @param sceneUpdateManager
   * @param locale
   */
  void onMapReady(MapView mapView, MapzenMapHttpHandler mapzenMapHttpHandler,
      OnMapReadyCallback callback, MapDataManager mapDataManager, MapStateManager mapStateManager,
      SceneUpdateManager sceneUpdateManager, Locale locale,
      BitmapMarkerManager bitmapMarkerManager) {
    MapController mapController = mapView.getTangramMapView().getMap(null);
    mapController.setSceneLoadListener(null);
    mapController.setHttpHandler(mapzenMapHttpHandler.httpHandler());
    MapzenManager mapzenManager = MapzenManager.instance(mapView.getContext());
    bitmapMarkerManager.setMapController(mapController);
    callback.onMapReady(
        new MapzenMap(mapView, mapController, new OverlayManager(mapView, mapController,
            mapDataManager, mapStateManager), mapStateManager, new LabelPickHandler(mapView),
            bitmapMarkerManager, sceneUpdateManager, locale, mapzenManager));
  }
}
