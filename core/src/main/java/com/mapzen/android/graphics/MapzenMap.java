package com.mapzen.android.graphics;

import com.mapzen.android.core.ApiKeyChangeListener;
import com.mapzen.android.core.MapzenManager;
import com.mapzen.android.graphics.internal.StyleStringGenerator;
import com.mapzen.android.graphics.model.BitmapMarker;
import com.mapzen.android.graphics.model.BitmapMarkerManager;
import com.mapzen.android.graphics.model.BitmapMarkerOptions;
import com.mapzen.android.graphics.model.CameraType;
import com.mapzen.android.graphics.model.EaseType;
import com.mapzen.android.graphics.model.MapStyle;
import com.mapzen.android.graphics.model.Marker;
import com.mapzen.android.graphics.model.MarkerOptions;
import com.mapzen.android.graphics.model.Polygon;
import com.mapzen.android.graphics.model.Polyline;
import com.mapzen.android.graphics.model.ThemeColor;
import com.mapzen.android.graphics.model.ThemedMapStyle;
import com.mapzen.tangram.LngLat;
import com.mapzen.tangram.MapController;
import com.mapzen.tangram.MapData;
import com.mapzen.tangram.MarkerPickResult;
import com.mapzen.tangram.SceneError;
import com.mapzen.tangram.SceneUpdate;
import com.mapzen.tangram.TouchInput;

import android.graphics.PointF;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static
    com.mapzen.android.graphics.internal.EaseTypeConverter.EASE_TYPE_TO_MAP_CONTROLLER_EASE_TYPE;
import static com.mapzen.android.graphics.model.ThemedMapStyle.NONE;

/**
 * This is the main class of the Mapzen Android API and is the entry point for all methods related
 * to the map. You cannot instantiate a {@link MapzenMap} object directly. Rather you must obtain
 * one from {@link MapFragment#getMapAsync(OnMapReadyCallback)} or
 * {@link MapView#getMapAsync(OnMapReadyCallback)}.
 */
public class MapzenMap {

  private final MapView mapView;
  private final MapController mapController;
  private final OverlayManager overlayManager;
  private final MapStateManager mapStateManager;
  private final LabelPickHandler labelPickHandler;
  private final BitmapMarkerManager bitmapMarkerManager;
  private final SceneUpdateManager sceneUpdateManager;
  private final MapzenManager mapzenManager;
  private Locale locale;
  private ImportYamlGenerator yamlGenerator;

  boolean pickFeatureOnSingleTapConfirmed = false;
  boolean pickLabelOnSingleTapConfirmed = false;
  boolean pickMarkerOnSingleTapConfirmed = false;

  private TouchInput.TapResponder internalTapResponder = new TouchInput.TapResponder() {
    @Override public boolean onSingleTapUp(float x, float y) {
      if (tapResponder != null) {
        tapResponder.onSingleTapUp(x, y);
      }
      return false;
    }

    @Override public boolean onSingleTapConfirmed(float x, float y) {
      if (tapResponder != null) {
        tapResponder.onSingleTapConfirmed(x, y);
      }
      if (pickFeatureOnSingleTapConfirmed) {
        mapController.pickFeature(x, y);
      }
      if (pickLabelOnSingleTapConfirmed) {
        mapController.pickLabel(x, y);
      }
      if (pickMarkerOnSingleTapConfirmed) {
        mapController.pickMarker(x, y);
      }
      return false;
    }
  };

  private TouchInput.TapResponder tapResponder;
  private TouchInput.DoubleTapResponder doubleTapResponder;
  private TouchInput.LongPressResponder longPressResponder;
  private TouchInput.PanResponder panResponder;
  private TouchInput.RotateResponder rotateResponder;
  private TouchInput.ScaleResponder scaleResponder;
  private TouchInput.ShoveResponder shoveResponder;

  private List<SceneUpdate> queuedSceneUpdates = new ArrayList<>();

  private static final HashMap<CameraType, MapController.CameraType>
      CAMERA_TYPE_TO_MAP_CONTROLLER_CAMERA_TYPE = new HashMap<>();
  private static final HashMap<MapController.CameraType, CameraType>
      MAP_CONTROLLER_CAMERA_TYPE_TO_CAMERA_TYPE = new HashMap<>();

  static {
    CAMERA_TYPE_TO_MAP_CONTROLLER_CAMERA_TYPE.put(CameraType.PERSPECTIVE,
        MapController.CameraType.PERSPECTIVE);
    CAMERA_TYPE_TO_MAP_CONTROLLER_CAMERA_TYPE.put(CameraType.ISOMETRIC,
        MapController.CameraType.ISOMETRIC);
    CAMERA_TYPE_TO_MAP_CONTROLLER_CAMERA_TYPE.put(CameraType.FLAT, MapController.CameraType.FLAT);

    MAP_CONTROLLER_CAMERA_TYPE_TO_CAMERA_TYPE.put(MapController.CameraType.PERSPECTIVE,
        CameraType.PERSPECTIVE);
    MAP_CONTROLLER_CAMERA_TYPE_TO_CAMERA_TYPE.put(MapController.CameraType.ISOMETRIC,
        CameraType.ISOMETRIC);
    MAP_CONTROLLER_CAMERA_TYPE_TO_CAMERA_TYPE.put(MapController.CameraType.FLAT, CameraType.FLAT);
  }

  /**
   * Internal map pan gesture listener that forwards events to both external listener and the
   * {@link OverlayManager}.
   */
  TouchInput.PanResponder internalPanResponder = new TouchInput.PanResponder() {
    @Override public boolean onPan(float startX, float startY, float endX, float endY) {
      overlayManager.onPan(startX, startY, endX, endY);
      return panResponder != null && panResponder.onPan(startX, startY, endX, endY);
    }

    @Override public boolean onFling(float posX, float posY, float velocityX, float velocityY) {
      overlayManager.onFling(posX, posY, velocityX, velocityY);
      return panResponder != null && panResponder.onFling(posX, posY, velocityX, velocityY);
    }
  };

  /**
   * Internal map rotate gesture listener that forwards events to both external listener and the
   * {@link OverlayManager}.
   */
  TouchInput.RotateResponder internalRotateResponder = new TouchInput.RotateResponder() {
    public boolean onRotate(float x, float y, float rotation) {
      overlayManager.onRotate(x, y, rotation);
      return rotateResponder != null && rotateResponder.onRotate(x, y, rotation);
    }
  };

  MapController.SceneLoadListener internalSceneLoadListener
      = new MapController.SceneLoadListener() {
    @Override public void onSceneReady(int sceneId, SceneError sceneError) {
      bitmapMarkerManager.restoreMarkers();
      if (sceneId == currSceneId) {
        if (styleLoadedListener != null) {
          styleLoadedListener.onStyleLoaded();
          styleLoadedListener = null;
        }
        currSceneId = Integer.MIN_VALUE;
      }
    }
  };

  ApiKeyChangeListener apiKeyChangeListener = new ApiKeyChangeListener() {
    @Override public void onApiKeyChanged(String apiKey) {
      List<SceneUpdate> updates = new ArrayList<>();
      updates.add(sceneUpdateManager.getApiKeyUpdate(apiKey));
      mapController.updateSceneAsync(updates);
    }
  };

  OnStyleLoadedListener styleLoadedListener = null;
  int currSceneId = Integer.MIN_VALUE;

  /**
   * Creates a new map based on the given {@link MapView} and {@link MapController}.
   */
  MapzenMap(MapView mapView, MapController mapController, OverlayManager overlayManager,
      MapStateManager mapStateManager, LabelPickHandler labelPickHandler,
      BitmapMarkerManager bitmapMarkerManager, SceneUpdateManager sceneUpdateManager, Locale locale,
      MapzenManager mapzenManager, ImportYamlGenerator yamlGenerator) {
    this.mapView = mapView;
    this.mapController = mapController;
    this.mapController.setSceneLoadListener(internalSceneLoadListener);
    this.overlayManager = overlayManager;
    this.mapStateManager = mapStateManager;
    this.labelPickHandler = labelPickHandler;
    this.bitmapMarkerManager = bitmapMarkerManager;
    this.sceneUpdateManager = sceneUpdateManager;
    this.locale = locale;
    this.mapzenManager = mapzenManager;
    this.mapzenManager.addApiKeyChangeListener(apiKeyChangeListener);
    this.yamlGenerator = yamlGenerator;
    mapView.setMapzenMap(this);
    mapController.setPanResponder(internalPanResponder);
    mapController.setRotateResponder(internalRotateResponder);
    overlayManager.restoreMapData();
    restoreMapState();
  }

  /**
   * Provides access to the underlying Tangram {@link MapController}.
   */
  @NonNull public MapController getMapController() {
    return mapController;
  }

  /**
   * Provides access to the underlying {@link OverlayManager}.
   */
  @NonNull public OverlayManager getOverlayManager() {
    return overlayManager;
  }

  /**
   * Sets the map's underlying stylesheet asynchronously.
   */
  public void setStyleAsync(@NonNull MapStyle mapStyle, @NonNull OnStyleLoadedListener listener) {
    styleLoadedListener = listener;
    currSceneId = internalSetStyle(mapStyle, true);
  }

  /**
   * Sets the map's underlying stylesheet.
   */
  public void setStyle(@NonNull MapStyle mapStyle) {
    internalSetStyle(mapStyle, false);
  }

  /**
   * Sets the map style with given label level and default detail and theme color values. If the
   * label level is not supported by this theme then this method throws an
   * {@link IllegalArgumentException}.
   * @param themedMapStyle
   * @param labelLevel
   */
  public void setStyleAndLabelLevelAsync(@NonNull ThemedMapStyle themedMapStyle, int labelLevel,
      @NonNull OnStyleLoadedListener listener) {
    setStyleLabelLevelLodThemeColorAsync(themedMapStyle, labelLevel,
        themedMapStyle.getDefaultLod(), themedMapStyle.getDefaultColor(), listener);
  }

  /**
   * Sets the map style with given label level and default detail and theme color values. If the
   * label level is not supported by this theme then this method throws an
   * {@link IllegalArgumentException}.
   * @param themedMapStyle
   * @param labelLevel
   */
  public void setStyleAndLabelLevel(@NonNull ThemedMapStyle themedMapStyle, int labelLevel) {
    setStyleLabelLevelLodThemeColor(themedMapStyle, labelLevel,
        themedMapStyle.getDefaultLod(), themedMapStyle.getDefaultColor());
  }

  /**
   * Sets the map style with given detail level and default label and theme color values. If the
   * detail level is not supported by this theme then this method throws an
   * {@link IllegalArgumentException}.
   * @param themedMapStyle
   * @param detailLevel
   */
  public void setStyleAndLodAsync(@NonNull ThemedMapStyle themedMapStyle, int detailLevel,
      @NonNull OnStyleLoadedListener listener) {
    setStyleLabelLevelLodThemeColorAsync(themedMapStyle, themedMapStyle.getDefaultLabelLevel(),
        detailLevel, themedMapStyle.getDefaultColor(), listener);
  }

  /**
   * Sets the map style with given detail level and default label and theme color values. If the
   * detail level is not supported by this theme then this method throws an
   * {@link IllegalArgumentException}.
   * @param themedMapStyle
   * @param detailLevel
   */
  public void setStyleAndLod(@NonNull ThemedMapStyle themedMapStyle, int detailLevel) {
    setStyleLabelLevelLodThemeColor(themedMapStyle, themedMapStyle.getDefaultLabelLevel(),
        detailLevel, themedMapStyle.getDefaultColor());
  }

  /**
   * Sets the map style with given theme color and default label and detail levels.
   * @param themedMapStyle
   * @param color
   */
  public void setStyleAndThemeColorAsync(@NonNull ThemedMapStyle themedMapStyle,
      @NonNull ThemeColor color, @NonNull OnStyleLoadedListener listener) {
    setStyleLabelLevelLodThemeColorAsync(themedMapStyle, themedMapStyle.getDefaultLabelLevel(),
        themedMapStyle.getDefaultLod(), color, listener);
  }

  /**
   * Sets the map style with given theme color and default label and detail levels.
   * @param themedMapStyle
   * @param color
   */
  public void setStyleAndThemeColor(@NonNull ThemedMapStyle themedMapStyle,
      @NonNull ThemeColor color) {
    setStyleLabelLevelLodThemeColor(themedMapStyle, themedMapStyle.getDefaultLabelLevel(),
        themedMapStyle.getDefaultLod(), color);
  }

  /**
   * Sets the map style with given label level, detail level, and theme color. If either the label
   * or detail level are not supported, this method will throw an {@link IllegalArgumentException}.
   * @param themedMapStyle
   * @param labelLevel
   * @param detailLevel
   * @param color
   */
  public void setStyleLabelLevelLodThemeColorAsync(@NonNull ThemedMapStyle themedMapStyle,
      int labelLevel, int detailLevel, @NonNull ThemeColor color,
      @NonNull OnStyleLoadedListener listener) {
    styleLoadedListener = listener;
    mapStateManager.setMapStyle(themedMapStyle);
    currSceneId = setLabelLevelLodThemeColor(labelLevel, detailLevel, color, true);
  }

  /**
   * Sets the map style with given label level, detail level, and theme color. If either the label
   * or detail level are not supported, this method will throw an {@link IllegalArgumentException}.
   * @param themedMapStyle
   * @param labelLevel
   * @param detailLevel
   * @param color
   */
  public void setStyleLabelLevelLodThemeColor(@NonNull ThemedMapStyle themedMapStyle,
      int labelLevel, int detailLevel, @NonNull ThemeColor color) {
    mapStateManager.setMapStyle(themedMapStyle);
    setLabelLevelLodThemeColor(labelLevel, detailLevel, color, false);
  }

  /**
   * Sets map zoom without animation.
   */
  public void setZoom(float zoom) {
    mapStateManager.setZoom(zoom);
    mapController.setZoom(zoom);
  }

  /**
   * Sets map zoom with animation and default, {@code EaseType.CUBIC} easing.
   *
   * @param zoom map zoom level
   * @param duration animation duration in millis
   */
  public void setZoom(float zoom, int duration) {
    setZoom(zoom, duration, EaseType.CUBIC);
  }

  /**
   * Sets map zoom with animation and easing.
   *
   * @param zoom map zoom level
   * @param duration animation duration in millis
   * @param easeType map ease type
   */
  public void setZoom(float zoom, int duration, @NonNull EaseType easeType) {
    mapStateManager.setZoom(zoom);
    mapController.setZoomEased(zoom, duration, EASE_TYPE_TO_MAP_CONTROLLER_EASE_TYPE.get(easeType));
  }

  /**
   * Returns map zoom.
   */
  public float getZoom() {
    return mapController.getZoom();
  }

  /**
   * Set map position without animation.
   */
  public void setPosition(@NonNull LngLat lngLat) {
    mapStateManager.setPosition(lngLat);
    mapController.setPosition(lngLat);
  }

  /**
   * Set map position with animation and default {@code EaseType.CUBIC} easing.
   *
   * @param lngLat position to center map on
   * @param duration animation duration in millis
   */
  public void setPosition(@NonNull LngLat lngLat, int duration) {
    setPosition(lngLat, duration, EaseType.CUBIC);
  }

  /**
   * Set map position with animation and easing.
   *
   * @param lngLat position to center map on
   * @param duration animation duration in millis
   * @param easeType map ease type
   */
  public void setPosition(@NonNull LngLat lngLat, int duration, @NonNull EaseType easeType) {
    mapStateManager.setPosition(lngLat);
    mapController.setPositionEased(lngLat, duration,
        EASE_TYPE_TO_MAP_CONTROLLER_EASE_TYPE.get(easeType));
  }

  /**
   * Returns map position.
   */
  @NonNull public LngLat getPosition() {
    return mapController.getPosition();
  }

  /**
   * Set map rotation in radians without animation.
   */
  public void setRotation(float radians) {
    mapStateManager.setRotation(radians);
    mapController.setRotation(radians);
    if (overlayManager.isCompassEnabled()) {
      overlayManager.setCompassButtonEnabled(true);
    }
  }

  /**
   * Set map rotation in radians with animation and default {@code EaseType.CUBIC} easing.
   *
   * @param radians map rotation
   * @param duration animation duration in millis
   */
  public void setRotation(float radians, int duration) {
    setRotation(radians, duration, EaseType.CUBIC);
  }

  /**
   * Set map rotation in radians with animation and custom easing.
   *
   * @param radians map rotation
   * @param duration animation duration in millis
   * @param easeType map ease type
   */
  public void setRotation(float radians, int duration, @NonNull EaseType easeType) {
    mapStateManager.setRotation(radians);
    mapController.setRotationEased(radians, duration,
        EASE_TYPE_TO_MAP_CONTROLLER_EASE_TYPE.get(easeType));
    if (overlayManager.isCompassEnabled()) {
      new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
          overlayManager.setCompassButtonEnabled(true);
        }
      }, duration);
    }
  }

  /**
   * Get map rotation in radians.
   */
  public float getRotation() {
    return mapController.getRotation();
  }

  /**
   * Set map tilt in radians without animation.
   *
   * @param radians tilt in radians
   */
  public void setTilt(float radians) {
    mapStateManager.setTilt(radians);
    mapController.setTilt(radians);
  }

  /**
   * Set map tilt in radians with animation and default {@code EaseType.CUBIC} easing.
   *
   * @param radians tilt in radians
   * @param duration duration in millis
   */
  public void setTilt(float radians, int duration) {
    setTilt(radians, duration, EaseType.CUBIC);
  }

  /**
   * Set map tilt in radians with animation and custom easing.
   *
   * @param radians tilt in radians
   * @param duration duration in millis
   * @param easeType map ease type
   */
  public void setTilt(float radians, int duration, @NonNull EaseType easeType) {
    mapStateManager.setTilt(radians);
    mapController.setTiltEased(radians, duration,
        EASE_TYPE_TO_MAP_CONTROLLER_EASE_TYPE.get(easeType));
  }

  /**
   * Get map tilt in radians.
   */
  public float getTilt() {
    return mapController.getTilt();
  }

  /**
   * Set the camera type for the map view.
   * @param type A {@code CameraType}
   */
  public void setCameraType(@NonNull CameraType type) {
    mapStateManager.setCameraType(type);
    mapController.setCameraType(CAMERA_TYPE_TO_MAP_CONTROLLER_CAMERA_TYPE.get(type));
  }

  /**
   * Get the camera type currently in use for the map view.
   * @return A {@code CameraType}
   */
  @NonNull public CameraType getCameraType() {
    return MAP_CONTROLLER_CAMERA_TYPE_TO_CAMERA_TYPE.get(mapController.getCameraType());
  }

  /**
   * When enabled, shows icon on map to allow centering map on current location. While
   * enabled, the user's current location will be updated in the background. When disabled, the
   * icon is hidden from the map.
   *
   * To use this function, your app must request permission for
   * {@code Manifest.permission.ACCESS_FINE_LOCATION} and
   * {@code Manifest.permission.ACCESS_COARSE_LOCATION}
   */
  public void setMyLocationEnabled(boolean enabled) {
    overlayManager.setMyLocationEnabled(enabled);
  }

  /**
   * Are we displaying the current location and find me icon.
   */
  public boolean isMyLocationEnabled() {
    return overlayManager.isMyLocationEnabled();
  }

  /**
   * Set an external click listener to be invoked after the internal listener.
   */
  public void setCompassOnClickListener(@Nullable View.OnClickListener listener) {
    overlayManager.setCompassOnClickListener(listener);
  }

  /**
   * Set an external click listener to be invoked after the internal listener.
   */
  public void setFindMeOnClickListener(@Nullable View.OnClickListener listener) {
    overlayManager.setFindMeOnClickListener(listener);
  }

  /**
   * Set an external click listener to be invoked after the internal listener.
   */
  public void setZoomInOnClickListener(@Nullable View.OnClickListener listener) {
    overlayManager.setZoomInOnClickListener(listener);
  }

  /**
   * Set an external click listener to be invoked after the internal listener.
   */
  public void setZoomOutOnClickListener(@Nullable View.OnClickListener listener) {
    overlayManager.setZoomOutOnClickListener(listener);
  }

  /**
   * Adds marker overlay to map. Returned {@link MapData} should be removed from map
   */
  @NonNull public MapData addMarker(@NonNull Marker marker) {
    return overlayManager.addMarker(marker);
  }

  /**
   * Removes marker overlay from map.
   */
  public void removeMarker() {
    overlayManager.removeMarker();
  }

  /**
   * Adds polyline overlay to map.
   */
  @NonNull public MapData addPolyline(@NonNull Polyline polyline) {
    return overlayManager.addPolyline(polyline);
  }

  /**
   * Removes polyline overlay from map.
   */
  public void removePolyline() {
    overlayManager.removePolyline();
  }

  /**
   * Adds polygon overlay to map.
   */
  @NonNull public MapData addPolygon(@NonNull Polygon polygon) {
    return overlayManager.addPolygon(polygon);
  }

  /**
   * Removes polygon overlay from map.
   */
  public void removePolygon() {
    overlayManager.removePolygon();
  }

  /**
   * Find the geographic coordinates corresponding to the given position on screen.
   *
   * @param position Pixels from the top-left edge of the screen
   * @return LngLat corresponding to the given point
   */
  @NonNull public LngLat screenPositionToLngLat(@NonNull PointF position) {
    return mapController.screenPositionToLngLat(position);
  }

  /**
   * Find the position on screen corresponding to the given geographic coordinates.
   * @param lngLat Geographic coordinates
   * @return Position in pixels from the top-left corner of the map area (the point
   * may not lie within the viewable screen area)
   */
  @NonNull public PointF lngLatToScreenPosition(@NonNull LngLat lngLat) {
    return mapController.lngLatToScreenPosition(lngLat);
  }

  /**
   * Set tap responder for tap gestures on map.
   */
  public void setTapResponder(@Nullable TouchInput.TapResponder tapResponder) {
    this.tapResponder = tapResponder;
    mapController.setTapResponder(internalTapResponder);
  }

  /**
   * Get the map's tap responder.
   */
  @Nullable public TouchInput.TapResponder getTapResponder() {
    return tapResponder;
  }

  /**
   * Set double tap responder for tap gestures on map.
   */
  public void setDoubleTapResponder(@Nullable TouchInput.DoubleTapResponder doubleTapResponder) {
    this.doubleTapResponder = doubleTapResponder;
    mapController.setDoubleTapResponder(this.doubleTapResponder);
  }

  /**
   * Get the map's double tap responder.
   */
  @Nullable public TouchInput.DoubleTapResponder getDoubleTapResponder() {
    return doubleTapResponder;
  }

  /**
   * Set long press responder for tap gestures on map.
   */
  public void setLongPressResponder(@Nullable TouchInput.LongPressResponder longPressResponder) {
    this.longPressResponder = longPressResponder;
    mapController.setLongPressResponder(this.longPressResponder);
  }

  /**
   * Get the map's long press responder.
   */
  @Nullable public TouchInput.LongPressResponder getLongPressResponder() {
    return longPressResponder;
  }

  /**
   * Set pan responder for tap gestures on map.
   */
  public void setPanResponder(@Nullable TouchInput.PanResponder panResponder) {
    this.panResponder = panResponder;
  }

  /**
   * Get the map's pan responder.
   */
  @Nullable public TouchInput.PanResponder getPanResponder() {
    return panResponder;
  }

  /**
   * Set rotate responder for tap gestures on map.
   */
  public void setRotateResponder(@Nullable TouchInput.RotateResponder rotateResponder) {
    this.rotateResponder = rotateResponder;
  }

  /**
   * Get the map's rotate responder.
   */
  @Nullable public TouchInput.RotateResponder getRotateResponder() {
    return rotateResponder;
  }

  /**
   * Set scale responder for tap gestures on map.
   */
  public void setScaleResponder(@Nullable TouchInput.ScaleResponder scaleResponder) {
    this.scaleResponder = scaleResponder;
    mapController.setScaleResponder(scaleResponder);
  }

  /**
   * Get the map's scale responder.
   */
  @Nullable public TouchInput.ScaleResponder getScaleResponder() {
    return scaleResponder;
  }

  /**
   * Set shove responder for tap gestures on map.
   */
  public void setShoveResponder(@Nullable TouchInput.ShoveResponder shoveResponder) {
    this.shoveResponder = shoveResponder;
    mapController.setShoveResponder(this.shoveResponder);
  }

  /**
   * Get the map's shove responder.
   */
  @Nullable public TouchInput.ShoveResponder getShoveResponder() {
    return shoveResponder;
  }

  /**
   * Set whether the gesture {@code second} can be recognized while {@code first} is in progress.
   *
   * @param first Initial gesture type
   * @param second Subsequent gesture type
   * @param allowed True if {@code second} should be recognized, else false
   */
  public void setSimultaneousGestureAllowed(@NonNull TouchInput.Gestures first,
      @NonNull TouchInput.Gestures second, boolean allowed) {
    mapController.setSimultaneousGestureAllowed(first, second, allowed);
  }

  /**
   * Get whether the gesture {@code second} can be recognized while {@code first} is in progress.
   *
   * @param first Initial gesture type
   * @param second Subsequent gesture type
   * @return True if {@code second} will be recognized, else false
   */
  public boolean isSimultaneousGestureAllowed(@NonNull TouchInput.Gestures first,
      @NonNull TouchInput.Gestures second) {
    return mapController.isSimultaneousGestureAllowed(first, second);
  }

  /**
   * Set a listener for feature pick events.
   *
   * @param listener Listener to call when {@link Marker}s are selected.
   */
  public void setFeaturePickListener(@Nullable final FeaturePickListener listener) {
    mapController.setFeaturePickListener(new MapController.FeaturePickListener() {
      @Override public void onFeaturePick(final Map<String, String> properties,
          final float positionX, final float positionY) {
        postFeaturePickRunnable(properties, positionX, positionY, listener);
      }
    });
    pickFeatureOnSingleTapConfirmed = (listener != null);
    mapController.setTapResponder(internalTapResponder);
  }

  /**
   * Set a listener for label pick events.
   *
   * @param listener Listener to receive callback when labels are selected.
   */
  public void setLabelPickListener(@Nullable final LabelPickListener listener) {
    labelPickHandler.setListener(listener);
    mapController.setLabelPickListener(labelPickHandler);
    pickLabelOnSingleTapConfirmed = (listener != null);
    mapController.setTapResponder(internalTapResponder);
  }

  /**
   * Set a listener for marker pick events.
   *
   * @param listener Listener to receive callback when {@link BitmapMarker}s are selected.
   */
  public void setMarkerPickListener(@Nullable final MarkerPickListener listener) {
    mapController.setMarkerPickListener(new MapController.MarkerPickListener() {
      @Override
      public void onMarkerPick(final MarkerPickResult markerPickResult, final float positionX,
          final float positionY) {
        mapView.post(new Runnable() {
          @Override public void run() {
            if (markerPickResult != null) {
              listener.onMarkerPick(new BitmapMarker(bitmapMarkerManager,
                  markerPickResult.getMarker(), new StyleStringGenerator()));
            }
          }
        });
      }
    });
    pickMarkerOnSingleTapConfirmed = (listener != null);
    mapController.setTapResponder(internalTapResponder);
  }

  /**
   * Set a listener for when view is fully loaded and no ease or label animations running.
   */
  public void setViewCompleteListener(@Nullable final ViewCompleteListener listener) {
    mapController.setViewCompleteListener(new MapController.ViewCompleteListener() {
      @Override public void onViewComplete() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
          @Override public void run() {
            listener.onViewComplete();
          }
        });
      }
    });
  }

  /**
   * Enqueue a Runnable to be executed synchronously on the rendering thread.
   *
   * @param r Runnable to run
   */
  public void queueEvent(@NonNull Runnable r) {
    mapController.queueEvent(r);
  }

  /**
   * Enqueue a scene component update with its corresponding YAML node value.
   *
   * @param componentPath The YAML component path delimited by a '.' (example "scene.animated")
   * @param value A YAML valid string (example "{ property: true }" or "true")
   */
  @Deprecated
  public void queueSceneUpdate(@NonNull String componentPath, @NonNull String value) {
    queuedSceneUpdates.add(new SceneUpdate(componentPath, value));
  }

  /**
   * Apply updates queued by queueSceneUpdate; this empties the current queue of updates.
   */
  @Deprecated
  public void applySceneUpdates() {
    if (queuedSceneUpdates.isEmpty()) {
      return;
    }
    mapController.updateSceneAsync(queuedSceneUpdates);
    queuedSceneUpdates = new ArrayList<>();
  }

  /**
   * Sets zoom buttons enabled.
   * @param enabled if true zoom buttonswill be showed, otherwise they will be hidden.
   */
  public void setZoomButtonsEnabled(boolean enabled) {
    overlayManager.setZoomButtonsEnabled(enabled);
  }

  /**
   * Sets compass button enabled.
   * @param enabled if true compass button will be showed, otherwise it will be hidden.
   */
  public void setCompassButtonEnabled(boolean enabled) {
    overlayManager.setCompassButtonEnabled(enabled);
  }

  /**
   * Draws two pins on the map. The start pin is active and the end pin is inactive.
   */
  public void drawRoutePins(@NonNull LngLat start, @NonNull LngLat end) {
    overlayManager.drawRoutePins(start, end);
  }

  /**
   * Clears the start and end pins from the map.
   */
  public void clearRoutePins() {
    overlayManager.clearRoutePins();
  }

  /**
   * Draws a dropped pin on the map at the point supplied.
   */
  public void drawDroppedPin(@NonNull LngLat point) {
    overlayManager.drawDroppedPin(point);
  }

  /**
   * Clears the dropped pin from the map.
   */
  public void clearDroppedPins() {
    overlayManager.clearDroppedPin();
  }

  /**
   * Draws a search result on the map at the point supplied. The pin will be active.
   */
  public void drawSearchResult(@NonNull LngLat point) {
    drawSearchResult(point, true);
  }

  /**
   * Draws a search result on the map at the point supplied.
   */
  public void drawSearchResult(@NonNull LngLat point, boolean active) {
    overlayManager.drawSearchResult(point, active);
  }

  /**
   * Draws search results on the map. All pins are displayed as active.
   */
  public void drawSearchResults(@NonNull List<LngLat> points) {
    int index = 0;
    for (LngLat point : points) {
      overlayManager.drawSearchResult(point, true, index);
      index++;
    }
  }

  /**
   * Draws search results on the map. All pins will be inactive except for the one at the active
   * index supplied.
   */
  public void drawSearchResults(@NonNull List<LngLat> points, int... activeIndexes) {
    int index = 0;
    for (LngLat point : points) {
      boolean status = false;
      for (int i = 0; i < activeIndexes.length; i++) {
        if (activeIndexes[i] == index) {
          status = true;
          break;
        }
      }
      overlayManager.drawSearchResult(point, status, index);
      index++;
    }
  }

  /**
   * Clears search results from the map.
   */
  public void clearSearchResults() {
    overlayManager.clearSearchResult();
  }

  /**
   * Draws route pin at the point supplied.
   */
  public void drawRouteLocationMarker(@NonNull LngLat point) {
    overlayManager.drawRouteLocationMarker(point);
  }

  /**
   * Clears route pin from the map.
   */
  public void clearRouteLocationMarker() {
    overlayManager.clearRouteLocationMarker();
  }

  /**
   * Draws route line on the map for the points supplied.
   */
  public void drawRouteLine(@NonNull List<LngLat> points) {
    overlayManager.drawRouteLine(points);
  }

  /**
   * Clears route line from the map.
   */
  public void clearRouteLine() {
    overlayManager.clearRouteLine();
  }

  /**
   * Draws transit route line on the map for the points supplied. Also draws station icons for each
   * point.
   */
  public void drawTransitRouteLine(@NonNull List<LngLat> points, @Nullable List<LngLat> stations,
      @NonNull String hexColor) {
    overlayManager.drawTransitRouteLine(points, stations, hexColor);
  }

  /**
   * Clears transit route line from the map.
   */
  public void clearTransitRouteLine() {
    overlayManager.clearTransitRouteLine();
  }

  /**
   * By default all {@link MapData} objects are removed from the map when it is destroyed. To
   * persist this data such as in the case of orientation changes, use this method.
   * @param persistOnRecreation persist {@link MapData} across orientation changes
   */
  public void setPersistMapData(boolean persistOnRecreation) {
    overlayManager.setPersistMapData(persistOnRecreation);
  }

  /**
   * By default map state is reset when the map is destroyed. To persist this information such as
   * in the case of orientation changes, use this method.
   * @param persistStateOnRecreation persist map position/zoom/rotation etc across orientation
   * changes
   */
  public void setPersistMapState(boolean persistStateOnRecreation) {
    mapStateManager.setPersistMapState(persistStateOnRecreation);
  }

  /**
   * All Mapzen basemap styles support a transit overlay. This method toggles its visibility.
   * @param transitOverlayEnabled whether or not the transit overlay should be enabled.
   */
  public void setTransitOverlayEnabled(boolean transitOverlayEnabled) {
    mapStateManager.setTransitOverlayEnabled(transitOverlayEnabled);
    mapController.updateSceneAsync(Arrays.asList(sceneUpdateManager.getTransitOverlayUpdate(
        transitOverlayEnabled)));
  }

  /**
   * The {@link com.mapzen.android.graphics.model.WalkaboutStyle} supports a bike overlay. This
   * method toggles its visibility.
   * @param bikeOverlayEnabled whether or not the bike overlay should be enabled.
   */
  public void setBikeOverlayEnabled(boolean bikeOverlayEnabled) {
    mapStateManager.setBikeOverlayEnabled(bikeOverlayEnabled);
    mapController.updateSceneAsync(Arrays.asList(sceneUpdateManager.getBikeOverlayUpdate(
        bikeOverlayEnabled)));
  }

  /**
   * The {@link com.mapzen.android.graphics.model.WalkaboutStyle} supports a path overlay. This
   * method toggles its visibility.
   * @param pathOverlayEnabled whether or not the path overlay should be enabled.
   */
  public void setPathOverlayEnabled(boolean pathOverlayEnabled) {
    mapStateManager.setPathOverlayEnabled(pathOverlayEnabled);
    mapController.updateSceneAsync(Arrays.asList(sceneUpdateManager.getPathOverlayUpdate(
        pathOverlayEnabled)));
  }

  /**
   * Method to facilitate enabling/disabling multiple overlays at once.
   * @param transitOverlayEnabled whether or not the transit overlay should be enabled.
   * @param bikeOverlayEnabled whether or not the bike overlay should be enabled.
   * @param pathOverlayEnabled whether or not the path overlay should be enabled.
   */
  public void setOverlaysEnabled(boolean transitOverlayEnabled, boolean bikeOverlayEnabled,
      boolean pathOverlayEnabled) {
    mapStateManager.setTransitOverlayEnabled(transitOverlayEnabled);
    mapStateManager.setBikeOverlayEnabled(bikeOverlayEnabled);
    mapStateManager.setPathOverlayEnabled(pathOverlayEnabled);
    List<SceneUpdate> updates = new ArrayList<>();
    updates.add(sceneUpdateManager.getTransitOverlayUpdate(transitOverlayEnabled));
    updates.add(sceneUpdateManager.getBikeOverlayUpdate(bikeOverlayEnabled));
    updates.add(sceneUpdateManager.getPathOverlayUpdate(pathOverlayEnabled));
    mapController.updateSceneAsync(updates);
  }

  /**
   * Sets the object used to add query parameters and headers to each request.
   * @param handler
   */
  public void setHttpHandler(@NonNull MapzenMapHttpHandler handler) {
    mapController.setHttpHandler(handler.httpHandler());
  }

  /**
   * Adds a custom bitmap marker to the map.
   *
   * @param markerOptions options used to define marker appearance.
   * @return a new bitmap marker instance.
   */
  public BitmapMarker addBitmapMarker(MarkerOptions markerOptions) {
    return bitmapMarkerManager.addMarker(markerOptions);
  }

  /**
   * Adds a custom bitmap marker to the map.
   *
   * @param markerOptions options used to define marker appearance.
   * @return a new bitmap marker instance.
   */
  @NonNull public BitmapMarker addBitmapMarker(@NonNull BitmapMarkerOptions markerOptions) {
    return bitmapMarkerManager.addMarker(markerOptions);
  }

  /**
   * Invoked by {@link MapView} when the parent activity or fragment is destroyed.
   */
  void onDestroy() {
    mapStateManager.setPosition(mapController.getPosition());
    mapStateManager.setZoom(mapController.getZoom());
    mapStateManager.setRotation(mapController.getRotation());
    mapStateManager.setTilt(mapController.getTilt());

    mapzenManager.removeApiKeyChangeListener(apiKeyChangeListener);

    mapController.setSceneLoadListener(null);
    mapController.setRotateResponder(null);
    mapController.setPanResponder(null);
    mapController.setDoubleTapResponder(null);
    mapController.setTapResponder(null);
    mapController.setLongPressResponder(null);
    mapController.setScaleResponder(null);
    mapController.setShoveResponder(null);
    mapController.setLabelPickListener(null);
    mapController.setMarkerPickListener(null);
    mapController.setFeaturePickListener(null);
  }

  private void postFeaturePickRunnable(final Map<String, String> properties, final float positionX,
      final float positionY, final FeaturePickListener listener) {
    mapView.post(new Runnable() {
      @Override public void run() {
        listener.onFeaturePick(properties, positionX, positionY);
      }
    });
  }

  /**
   * Restores all aspects of the map EXCEPT the style, this is restored in the
   * {@link MapInitializer}.
   */
  private void restoreMapState() {
    if (!mapStateManager.getPersistMapState()) {
      return;
    }
    setPosition(mapStateManager.getPosition());
    setZoom(mapStateManager.getZoom());
    setRotation(mapStateManager.getRotation());
    setTilt(mapStateManager.getTilt());
    setCameraType(mapStateManager.getCameraType());
    setOverlaysEnabled(mapStateManager.isTransitOverlayEnabled(),
        mapStateManager.isBikeOverlayEnabled(), mapStateManager.isPathOverlayEnabled());
  }

  /**
   * Returns all {@link SceneUpdate}s that should be applied when a new map style is set.
   * @return
   */
  private List<SceneUpdate> getGlobalSceneUpdates() {
    String apiKey = mapzenManager.getApiKey();
    return sceneUpdateManager.getUpdatesFor(apiKey, locale,
        mapStateManager.isTransitOverlayEnabled(), mapStateManager.isBikeOverlayEnabled(),
        mapStateManager.isPathOverlayEnabled());
  }

  /**
   * Sets the {@link MapStyle} and relevant theme configuration for {@link ThemedMapStyle}s. Loads
   * the scene file either synchronously or asynchronously and returns the sceneId.
   * @param mapStyle
   * @param async
   * @return
   */
  private int internalSetStyle(MapStyle mapStyle, boolean async) {
    mapStateManager.setMapStyle(mapStyle);
    if (currentMapStyleIsThemed()) {
      mapStateManager.setLabelLevel(getThemedMapStyle().getDefaultLabelLevel());
      mapStateManager.setLod(getThemedMapStyle().getDefaultLod());
      mapStateManager.setThemeColor(getThemedMapStyle().getDefaultColor());
      return loadSceneYaml(async);
    } else {
      return loadSceneFile(async);
    }
  }

  /**
   * Internal convenience method for loading scene file when the current style is a
   * {@link MapStyle}.
   * Applies all global scene updates. Loads asynchronously or
   * synchronously. Returns the sceneId for the {@link MapController} scene update.
   */
  private int loadSceneFile(boolean async) {
    if (async) {
      return mapController.loadSceneFileAsync(mapStateManager.getMapStyle().getSceneFile(),
          getGlobalSceneUpdates());
    } else {
      return mapController.loadSceneFile(mapStateManager.getMapStyle().getSceneFile(),
          getGlobalSceneUpdates());
    }
  }

  /**
   * Internal convenience method for loading scene yaml when the current style is a
   * {@link ThemedMapStyle}. Applies all global scene updates. Loads asynchronously or
   * synchronously. Returns the sceneId for the {@link MapController} scene update.
   */
  private int loadSceneYaml(boolean async) {
    String yaml = yamlGenerator.getImportYaml(getThemedMapStyle(), mapStateManager.getLabelLevel(),
        mapStateManager.getLod(), mapStateManager.getThemeColor());
    String resourceRoot = getThemedMapStyle().getStyleRootPath();
    if (async) {
      return mapController.loadSceneYamlAsync(yaml, resourceRoot, getGlobalSceneUpdates());
    } else {
      return mapController.loadSceneYaml(yaml, resourceRoot, getGlobalSceneUpdates());
    }
  }

  /**
   * Queries the {@link MapStateManager} to determine if the current style supports themes. Use to
   * determine if scene yaml or simply scene file should be loaded.
   * @return
   */
  private boolean currentMapStyleIsThemed() {
    return mapStateManager.getMapStyle() instanceof ThemedMapStyle;
  }

  /**
   * Internal convenience method. Returns the current style downcasted to {@link ThemedMapStyle}.
   * Users of this method should first check that the style is a {@link ThemedMapStyle}.
   * @return
   */
  private ThemedMapStyle getThemedMapStyle() {
    return (ThemedMapStyle) mapStateManager.getMapStyle();
  }

  /**
   * Checks the given label level against the current map style to determine if the level is
   * supported by the theme. Users of this method should first check that the style is a
   * {@link ThemedMapStyle}.
   * @param labelLevel
   * @return
   */
  private boolean isValidLabelLevel(int labelLevel) {
    if (labelLevel == NONE) {
      return true;
    }
    return labelLevel >= 0 && labelLevel < getThemedMapStyle().getLabelCount();
  }

  /**
   * Checks the given detail level against the current map style to determine if the level is
   * supported by the theme. Users of this method should first check that the style is a
   * {@link ThemedMapStyle}.
   * @param lod
   * @return
   */
  private boolean isValidLod(int lod) {
    if (lod == NONE) {
      return true;
    }
    return lod >= 0 && lod < getThemedMapStyle().getLodCount();
  }

  /**
   * Checks the given theme color against the current map style to determine if the color is
   * supported by the theme. Users of this method should first check that the style is a
   * {@link ThemedMapStyle}.
   * @param color
   * @return
   */
  private boolean isValidColor(ThemeColor color) {
    if (color == ThemeColor.NONE) {
      return true;
    }
    return getThemedMapStyle().getColors().contains(color);
  }


  /**
   * Sets the label level, detail level, and theme color when the current style is of type
   * {@link ThemedMapStyle}. If the label level, detail level, or theme color are not supported by
   * the current style, this method will throw an {@link IllegalArgumentException}. If the current
   * map style is not {@link ThemedMapStyle} then this method does nothing.
   * @param labelLevel
   * @param detailLevel
   * @param color
   */
  private int setLabelLevelLodThemeColor(int labelLevel, int detailLevel,
      ThemeColor color, boolean async) {
    if (!isValidLabelLevel(labelLevel)) {
      throw new IllegalArgumentException("Invalid label level for " +
          getThemedMapStyle().getClass().getSimpleName());
    }
    if (!isValidLod(detailLevel)) {
      throw new IllegalArgumentException("Invalid detail level for " +
          getThemedMapStyle().getClass().getSimpleName());
    }
    if (!isValidColor(color)) {
      throw new IllegalArgumentException("Invalid theme color for " +
          getThemedMapStyle().getClass().getSimpleName());
    }
    mapStateManager.setLabelLevel(labelLevel);
    mapStateManager.setLod(detailLevel);
    mapStateManager.setThemeColor(color);
    return loadSceneYaml(async);
  }
}
