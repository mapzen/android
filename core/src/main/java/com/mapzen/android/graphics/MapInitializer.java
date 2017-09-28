package com.mapzen.android.graphics;

import com.mapzen.android.core.MapzenManager;
import com.mapzen.android.graphics.model.BubbleWrapStyle;
import com.mapzen.android.graphics.model.MapStyle;
import com.mapzen.android.graphics.model.BitmapMarkerManager;
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

  private MapDataManager mapDataManager;

  private MapStateManager mapStateManager;

  private SceneUpdateManager sceneUpdateManager;

  private Locale locale = Locale.getDefault();

  MapReadyInitializer mapReadyInitializer;

  private BitmapMarkerManager bitmapMarkerManager;

  private ImportYamlGenerator yamlGenerator;

  /**
   * Creates a new instance.
   */
  @Inject MapInitializer(Context context, MapzenMapHttpHandler mapzenMapHttpHandler,
      MapDataManager mapDataManager, MapStateManager mapStateManager,
      SceneUpdateManager sceneUpdateManager, BitmapMarkerManager bitmapMarkerManager,
      ImportYamlGenerator yamlGenerator) {
    this.context = context;
    this.mapzenMapHttpHandler = mapzenMapHttpHandler;
    this.mapDataManager = mapDataManager;
    this.mapStateManager = mapStateManager;
    this.sceneUpdateManager = sceneUpdateManager;
    mapReadyInitializer = new MapReadyInitializer();
    this.bitmapMarkerManager = bitmapMarkerManager;
    this.yamlGenerator = yamlGenerator;
  }

  /**
   * Initialize map for the current {@link MapView} and notify via {@link OnMapReadyCallback}.
   */
  public void init(final MapView mapView, final OnMapReadyCallback callback) {
    loadMap(mapView, new BubbleWrapStyle(), false, callback);
  }

  /**
   * Initialize map for current {@link MapView} and {@link MapStyle} before notifying via
   * {@link OnMapReadyCallback}.
   */
  public void init(final MapView mapView, MapStyle mapStyle, final OnMapReadyCallback callback) {
    loadMap(mapView, mapStyle, true, callback);
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
    loadMap(mapView, mapStyle, true, callback);
  }

  private void loadMap(final MapView mapView, MapStyle mapStyle, boolean styleExplicitlySet,
      final OnMapReadyCallback callback) {
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
    loadMap(mapView, mapStyle, callback);
  }

  private void loadMap(final MapView mapView, MapStyle mapStyle,
      final OnMapReadyCallback callback) {
    final String apiKey = MapzenManager.instance(context).getApiKey();
    final List<SceneUpdate> sceneUpdates = sceneUpdateManager.getUpdatesFor(apiKey, locale,
        mapStateManager.isTransitOverlayEnabled(), mapStateManager.isBikeOverlayEnabled(),
        mapStateManager.isPathOverlayEnabled());
    MapController controller = mapView.getTangramMapView().getMap(
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
}
