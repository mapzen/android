package com.mapzen.android.graphics;

import com.mapzen.android.core.ApiKeyConstants;
import com.mapzen.android.graphics.model.BubbleWrapStyle;
import com.mapzen.android.graphics.model.MapStyle;
import com.mapzen.tangram.MapController;
import com.mapzen.tangram.SceneUpdate;

import android.content.Context;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Class responsible for initializing the map.
 */
public class MapInitializer {

  private Context context;

  private TileHttpHandler tileHttpHandler;

  private MapDataManager mapDataManager;

  private MapStateManager mapStateManager;

  /**
   * Creates a new instance.
   */
  @Inject MapInitializer(Context context, TileHttpHandler tileHttpHandler,
      MapDataManager mapDataManager, MapStateManager mapStateManager) {
    this.context = context;
    this.tileHttpHandler = tileHttpHandler;
    this.mapDataManager = mapDataManager;
    this.mapStateManager = mapStateManager;
  }

  /**
   * Initialize map for the current {@link MapView} and notify via {@link OnMapReadyCallback}.
   */
  public void init(final MapView mapView, final OnMapReadyCallback callback) {
    loadMap(mapView, new BubbleWrapStyle(), false, callback, null);
  }

  /**
   * Initialize map for current {@link MapView} and {@link MapStyle} before notifying via
   * {@link OnMapReadyCallback}.
   */
  public void init(final MapView mapView, MapStyle mapStyle, final OnMapReadyCallback callback) {
    loadMap(mapView, mapStyle, true, callback, null);
  }

  /**
   * Initialize map for the current {@link MapView} with given API key and notify via
   * {@link OnMapReadyCallback}.
   */
  public void init(final MapView mapView, String key, final OnMapReadyCallback callback) {
    loadMap(mapView, new BubbleWrapStyle(), false, callback, key);
  }

  /**
   * Initialize map for the current {@link MapView} with given API key and notify via
   * {@link OnMapReadyCallback}.
   */
  public void init(final MapView mapView, String key, MapStyle mapStyle,
      final OnMapReadyCallback callback) {
    loadMap(mapView, mapStyle, true, callback, key);
  }

  private TangramMapView getTangramView(final MapView mapView) {
    return mapView.getTangramMapView();
  }

  private void loadMap(final MapView mapView, MapStyle mapStyle, boolean styleExplicitlySet,
      final OnMapReadyCallback callback, String apiKey) {
    if (mapStateManager.getPersistMapState() && !styleExplicitlySet) {
      MapStyle restoredMapStyle = mapStateManager.getMapStyle();
      mapStyle = restoredMapStyle;
    }
    mapStateManager.setMapStyle(mapStyle);
    loadMap(mapView, mapStyle.getSceneFile(), callback, apiKey);
  }

  private void loadMap(final MapView mapView, String sceneFile, final OnMapReadyCallback callback,
      String apiKey) {
    if (apiKey == null) {
      final int resId = context.getResources().getIdentifier(ApiKeyConstants.API_KEY_RES_NAME,
          ApiKeyConstants.API_KEY_RES_TYPE, context.getPackageName());
      apiKey = context.getString(resId);
    }

    final ArrayList<SceneUpdate> sceneUpdates = new ArrayList<>(1);
    sceneUpdates.add(new SceneUpdate("global.sdk_mapzen_api_key", apiKey));
    getTangramView(mapView).getMapAsync(new com.mapzen.tangram.MapView.OnMapReadyCallback() {
      @Override public void onMapReady(MapController mapController) {
        mapController.setHttpHandler(tileHttpHandler);
        callback.onMapReady(
            new MapzenMap(mapView, mapController, new OverlayManager(mapView, mapController,
                mapDataManager, mapStateManager), mapStateManager, new LabelPickHandler(mapView)));
      }
    }, sceneFile, sceneUpdates);
  }
}
