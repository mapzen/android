package com.mapzen.android.graphics;

import com.mapzen.android.core.MapzenManager;
import com.mapzen.android.graphics.model.BitmapMarkerManager;
import com.mapzen.android.graphics.model.BubbleWrapStyle;
import com.mapzen.android.graphics.model.MapStyle;
import com.mapzen.android.graphics.model.ThemedMapStyle;
import com.mapzen.tangram.MapController;
import com.mapzen.tangram.SceneError;
import com.mapzen.tangram.SceneUpdate;

import android.content.Context;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

/**
 * Class responsible for initializing the map.
 */
public class MapInitializer {

  private Context context;

  private MapzenMapHttpHandler mapzenMapHttpHandler;

  private PersistDataManagers persistDataManagers;

  private SceneUpdateManager sceneUpdateManager;

  private Locale locale = Locale.getDefault();

  MapReadyInitializer mapReadyInitializer;

  private BitmapMarkerManager bitmapMarkerManager;

  private ImportYamlGenerator yamlGenerator;

  MapController controller;

  /**
   * Creates a new instance.
   */
  @Inject MapInitializer(Context context, MapzenMapHttpHandler mapzenMapHttpHandler,
      PersistDataManagers persistDataManagers, SceneUpdateManager sceneUpdateManager,
      BitmapMarkerManager bitmapMarkerManager, ImportYamlGenerator yamlGenerator) {
    this.context = context;
    this.mapzenMapHttpHandler = mapzenMapHttpHandler;
    this.persistDataManagers = persistDataManagers;
    this.sceneUpdateManager = sceneUpdateManager;
    mapReadyInitializer = new MapReadyInitializer();
    this.bitmapMarkerManager = bitmapMarkerManager;
    this.yamlGenerator = yamlGenerator;
  }

  /**
   * Initialize map for the current {@link MapView} and notify via {@link OnMapReadyCallback}.
   */
  public void init(final MapView mapView, final OnMapReadyCallback callback) {
    loadMap(mapView, null, new BubbleWrapStyle(), false, callback);
  }

  /**
   * Initialize map for the current {@link MapView} and notify via {@link OnMapReadyCallback}.
   */
  public void init(final MapView mapView, String mapId, final OnMapReadyCallback callback) {
    loadMap(mapView, mapId, new BubbleWrapStyle(), false, callback);
  }

  /**
   * Initialize map for current {@link MapView} and {@link MapStyle} before notifying via
   * {@link OnMapReadyCallback}.
   */
  public void init(final MapView mapView, MapStyle mapStyle, final OnMapReadyCallback callback) {
    loadMap(mapView, null, mapStyle, true, callback);
  }

  /**
   * Initialize map for current {@link MapView} and {@link MapStyle} before notifying via
   * {@link OnMapReadyCallback}.
   */
  public void init(final MapView mapView, String mapId, MapStyle mapStyle,
      final OnMapReadyCallback callback) {
    loadMap(mapView, mapId, mapStyle, true, callback);
  }

  /**
   * Initialize map for current {@link MapView} and {@link MapStyle} before notifying via
   * {@link OnMapReadyCallback}.
   *
   * Also sets {@link Locale} used to determine default language when rendering map labels.
   */
  public void init(final MapView mapView, MapStyle mapStyle, Locale locale,
      final OnMapReadyCallback callback) {
    this.locale = locale;
    loadMap(mapView, null, mapStyle, true, callback);
  }

  /**
   * Initialize map for current {@link MapView} and {@link MapStyle} before notifying via
   * {@link OnMapReadyCallback}.
   *
   * Also sets {@link Locale} used to determine default language when rendering map labels.
   */
  public void init(final MapView mapView, String mapId, MapStyle mapStyle, Locale locale,
      final OnMapReadyCallback callback) {
    this.locale = locale;
    loadMap(mapView, mapId, mapStyle, true, callback);
  }

  private void loadMap(final MapView mapView, String mapId, MapStyle mapStyle,
      boolean styleExplicitlySet, final OnMapReadyCallback callback) {
    MapStateManager mapStateManager = persistDataManagers.getMapStateManager(mapId);
    if (mapStateManager.getPersistMapState() && !styleExplicitlySet) {
      MapStyle restoredMapStyle = mapStateManager.getMapStyle();
      mapStyle = restoredMapStyle;
    }
    mapStateManager.setMapStyle(mapStyle);
    if (mapStyle instanceof ThemedMapStyle) {
      ThemedMapStyle themedStyle = (ThemedMapStyle) mapStyle;
      mapStateManager.setLabelLevel(themedStyle.getDefaultLabelLevel());
      mapStateManager.setLod(themedStyle.getDefaultLod());
      mapStateManager.setThemeColor(themedStyle.getDefaultColor());
    }
    loadMap(mapView, mapId, mapStyle, callback);
  }

  private void loadMap(final MapView mapView, final String mapId, MapStyle mapStyle,
      final OnMapReadyCallback callback) {
    final MapStateManager mapStateManager = persistDataManagers.getMapStateManager(mapId);
    final String apiKey = MapzenManager.instance(context).getApiKey();
    final List<SceneUpdate> sceneUpdates = sceneUpdateManager.getUpdatesFor(apiKey, locale,
        mapStateManager.isTransitOverlayEnabled(), mapStateManager.isBikeOverlayEnabled(),
        mapStateManager.isPathOverlayEnabled());
    final MapDataManager mapDataManager = persistDataManagers.getMapDataManager(mapId);
    controller = mapView.getTangramMapView().getMap(
        new MapController.SceneLoadListener() {
      @Override public void onSceneReady(int sceneId, SceneError sceneError) {
        mapReadyInitializer.onMapReady(mapView, mapzenMapHttpHandler, callback, mapDataManager,
            mapStateManager, sceneUpdateManager, locale, bitmapMarkerManager, yamlGenerator);
      }
    });
    if (mapStyle instanceof ThemedMapStyle) {
      ThemedMapStyle themedMapStyle = (ThemedMapStyle) mapStyle;
      String yaml = yamlGenerator.getImportYaml(themedMapStyle, mapStateManager.getLabelLevel(),
          mapStateManager.getLod(), mapStateManager.getThemeColor());
      controller.loadSceneYamlAsync(yaml, themedMapStyle.getStyleRootPath(), sceneUpdates);
    } else {
      controller.loadSceneFileAsync(mapStyle.getSceneFile(), sceneUpdates);
    }
  }

  /**
   * Called my {@link MapView} when the view has been destroyed. Use this method to prevent scene
   * updates and other future interaction with {@link MapController}.
   */
  public void takeDown() {
    controller.setSceneLoadListener(null);
  }
}
