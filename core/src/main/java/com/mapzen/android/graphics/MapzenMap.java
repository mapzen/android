package com.mapzen.android.graphics;

import com.mapzen.android.graphics.model.CameraType;
import com.mapzen.android.graphics.model.EaseType;
import com.mapzen.android.graphics.model.MapStyle;
import com.mapzen.android.graphics.model.Marker;
import com.mapzen.android.graphics.model.Polygon;
import com.mapzen.android.graphics.model.Polyline;
import com.mapzen.tangram.LngLat;
import com.mapzen.tangram.MapController;
import com.mapzen.tangram.MapData;
import com.mapzen.tangram.SceneUpdate;
import com.mapzen.tangram.TouchInput;

import android.graphics.PointF;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

  boolean pickFeatureOnSingleTapConfirmed = false;
  boolean pickLabelOnSingleTapConfirmed = false;

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

  private static final HashMap<EaseType, MapController.EaseType>
      EASE_TYPE_TO_MAP_CONTROLLER_EASE_TYPE = new HashMap();

  static {
    EASE_TYPE_TO_MAP_CONTROLLER_EASE_TYPE.put(EaseType.LINEAR, MapController.EaseType.LINEAR);
    EASE_TYPE_TO_MAP_CONTROLLER_EASE_TYPE.put(EaseType.CUBIC, MapController.EaseType.CUBIC);
    EASE_TYPE_TO_MAP_CONTROLLER_EASE_TYPE.put(EaseType.QUINT, MapController.EaseType.QUINT);
    EASE_TYPE_TO_MAP_CONTROLLER_EASE_TYPE.put(EaseType.SINE, MapController.EaseType.SINE);
  }

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

  /**
   * Creates a new map based on the given {@link MapView} and {@link MapController}.
   */
  MapzenMap(MapView mapView, MapController mapController, OverlayManager overlayManager,
      MapStateManager mapStateManager, LabelPickHandler labelPickHandler) {
    this.mapView = mapView;
    this.mapController = mapController;
    this.overlayManager = overlayManager;
    this.mapStateManager = mapStateManager;
    this.labelPickHandler = labelPickHandler;
    mapView.setMapzenMap(this);
    mapController.setPanResponder(internalPanResponder);
    mapController.setRotateResponder(internalRotateResponder);
    overlayManager.restoreMapData();
    restoreMapState();
  }

  /**
   * Provides access to the underlying Tangram {@link MapController}.
   */
  public MapController getMapController() {
    return mapController;
  }

  /**
   * Provides access to the underlying {@link OverlayManager}.
   */
  public OverlayManager getOverlayManager() {
    return overlayManager;
  }

  /**
   * Sets the map's underlying stylesheet.
   */
  public void setStyle(MapStyle mapStyle) {
    mapStateManager.setMapStyle(mapStyle);
    mapController.loadSceneFile(mapStyle.getSceneFile());
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
  public void setZoom(float zoom, int duration, EaseType easeType) {
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
  public void setPosition(LngLat lngLat) {
    mapStateManager.setPosition(lngLat);
    mapController.setPosition(lngLat);
  }

  /**
   * Set map position with animation and default {@code EaseType.CUBIC} easing.
   *
   * @param lngLat position to center map on
   * @param duration animation duration in millis
   */
  public void setPosition(LngLat lngLat, int duration) {
    setPosition(lngLat, duration, EaseType.CUBIC);
  }

  /**
   * Set map position with animation and easing.
   *
   * @param lngLat position to center map on
   * @param duration animation duration in millis
   * @param easeType map ease type
   */
  public void setPosition(LngLat lngLat, int duration, EaseType easeType) {
    mapStateManager.setPosition(lngLat);
    mapController.setPositionEased(lngLat, duration,
        EASE_TYPE_TO_MAP_CONTROLLER_EASE_TYPE.get(easeType));
  }

  /**
   * Returns map position.
   */
  public LngLat getPosition() {
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
  public void setRotation(float radians, int duration, EaseType easeType) {
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
  public void setTilt(float radians, int duration, EaseType easeType) {
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
  public void setCameraType(CameraType type) {
    mapStateManager.setCameraType(type);
    mapController.setCameraType(CAMERA_TYPE_TO_MAP_CONTROLLER_CAMERA_TYPE.get(type));
  }

  /**
   * Get the camera type currently in use for the map view.
   * @return A {@code CameraType}
   */
  public CameraType getCameraType() {
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
  public void setCompassOnClickListener(View.OnClickListener listener) {
    overlayManager.setCompassOnClickListener(listener);
  }

  /**
   * Set an external click listener to be invoked after the internal listener.
   */
  public void setFindMeOnClickListener(View.OnClickListener listener) {
    overlayManager.setFindMeOnClickListener(listener);
  }

  /**
   * Set an external click listener to be invoked after the internal listener.
   */
  public void setZoomInOnClickListener(View.OnClickListener listener) {
    overlayManager.setZoomInOnClickListener(listener);
  }

  /**
   * Set an external click listener to be invoked after the internal listener.
   */
  public void setZoomOutOnClickListener(View.OnClickListener listener) {
    overlayManager.setZoomOutOnClickListener(listener);
  }

  /**
   * Adds marker overlay to map. Returned {@link MapData} should be removed from map
   */
  public MapData addMarker(Marker marker) {
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
  public MapData addPolyline(Polyline polyline) {
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
  public MapData addPolygon(Polygon polygon) {
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
  public LngLat screenPositionToLngLat(PointF position) {
    return mapController.screenPositionToLngLat(position);
  }

  /**
   * Find the position on screen corresponding to the given geographic coordinates.
   * @param lngLat Geographic coordinates
   * @return Position in pixels from the top-left corner of the map area (the point
   * may not lie within the viewable screen area)
   */
  public PointF lngLatToScreenPosition(LngLat lngLat) {
    return mapController.lngLatToScreenPosition(lngLat);
  }

  /**
   * Set tap responder for tap gestures on map.
   */
  public void setTapResponder(final TouchInput.TapResponder tapResponder) {
    this.tapResponder = tapResponder;
    mapController.setTapResponder(internalTapResponder);
  }

  /**
   * Get the map's tap responder.
   */
  public TouchInput.TapResponder getTapResponder() {
    return tapResponder;
  }

  /**
   * Set double tap responder for tap gestures on map.
   */
  public void setDoubleTapResponder(TouchInput.DoubleTapResponder doubleTapResponder) {
    this.doubleTapResponder = doubleTapResponder;
    mapController.setDoubleTapResponder(this.doubleTapResponder);
  }

  /**
   * Get the map's double tap responder.
   */
  public TouchInput.DoubleTapResponder getDoubleTapResponder() {
    return doubleTapResponder;
  }

  /**
   * Set long press responder for tap gestures on map.
   */
  public void setLongPressResponder(TouchInput.LongPressResponder longPressResponder) {
    this.longPressResponder = longPressResponder;
    mapController.setLongPressResponder(this.longPressResponder);
  }

  /**
   * Get the map's long press responder.
   */
  public TouchInput.LongPressResponder getLongPressResponder() {
    return longPressResponder;
  }

  /**
   * Set pan responder for tap gestures on map.
   */
  public void setPanResponder(TouchInput.PanResponder panResponder) {
    this.panResponder = panResponder;
  }

  /**
   * Get the map's pan responder.
   */
  public TouchInput.PanResponder getPanResponder() {
    return panResponder;
  }

  /**
   * Set rotate responder for tap gestures on map.
   */
  public void setRotateResponder(TouchInput.RotateResponder rotateResponder) {
    this.rotateResponder = rotateResponder;
  }

  /**
   * Get the map's rotate responder.
   */
  public TouchInput.RotateResponder getRotateResponder() {
    return rotateResponder;
  }

  /**
   * Set scale responder for tap gestures on map.
   */
  public void setScaleResponder(TouchInput.ScaleResponder scaleResponder) {
    this.scaleResponder = scaleResponder;
    mapController.setScaleResponder(scaleResponder);
  }

  /**
   * Get the map's scale responder.
   */
  public TouchInput.ScaleResponder getScaleResponder() {
    return scaleResponder;
  }

  /**
   * Set shove responder for tap gestures on map.
   */
  public void setShoveResponder(TouchInput.ShoveResponder shoveResponder) {
    this.shoveResponder = shoveResponder;
    mapController.setShoveResponder(this.shoveResponder);
  }

  /**
   * Get the map's shove responder.
   */
  public TouchInput.ShoveResponder getShoveResponder() {
    return shoveResponder;
  }

  /**
   * Set whether the gesture {@code second} can be recognized while {@code first} is in progress.
   *
   * @param first Initial gesture type
   * @param second Subsequent gesture type
   * @param allowed True if {@code second} should be recognized, else false
   */
  public void setSimultaneousGestureAllowed(TouchInput.Gestures first, TouchInput.Gestures second,
      boolean allowed) {
    mapController.setSimultaneousGestureAllowed(first, second, allowed);
  }

  /**
   * Get whether the gesture {@code second} can be recognized while {@code first} is in progress.
   *
   * @param first Initial gesture type
   * @param second Subsequent gesture type
   * @return True if {@code second} will be recognized, else false
   */
  public boolean isSimultaneousGestureAllowed(TouchInput.Gestures first,
      TouchInput.Gestures second) {
    return mapController.isSimultaneousGestureAllowed(first, second);
  }

  /**
   * Set a listener for feature pick events.
   *
   * @param listener Listener to call
   */
  public void setFeaturePickListener(final FeaturePickListener listener) {
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
  public void setLabelPickListener(final LabelPickListener listener) {
    labelPickHandler.setListener(listener);
    mapController.setLabelPickListener(labelPickHandler);
    pickLabelOnSingleTapConfirmed = (listener != null);
    mapController.setTapResponder(internalTapResponder);
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
   * Set a listener for when view is fully loaded and no ease or label animations running.
   */
  public void setViewCompleteListener(final ViewCompleteListener listener) {
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
  public void queueEvent(Runnable r) {
    mapController.queueEvent(r);
  }

  /**
   * Enqueue a scene component update with its corresponding YAML node value.
   *
   * @param componentPath The YAML component path delimited by a '.' (example "scene.animated")
   * @param value A YAML valid string (example "{ property: true }" or "true")
   */
  public void queueSceneUpdate(String componentPath, String value) {
    mapController.queueSceneUpdate(new SceneUpdate(componentPath, value));
  }

  /**
   * Apply updates queued by queueSceneUpdate; this empties the current queue of updates.
   */
  public void applySceneUpdates() {
    mapController.applySceneUpdates();
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
  public void drawRoutePins(LngLat start, LngLat end) {
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
  public void drawDroppedPin(LngLat point) {
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
  public void drawSearchResult(LngLat point) {
    drawSearchResult(point, true);
  }

  /**
   * Draws a search result on the map at the point supplied.
   */
  public void drawSearchResult(LngLat point, boolean active) {
    overlayManager.drawSearchResult(point, active);
  }

  /**
   * Draws search results on the map. All pins are displayed as active.
   */
  public void drawSearchResults(List<LngLat> points) {
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
  public void drawSearchResults(List<LngLat> points, int... activeIndexes) {
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
  public void drawRouteLocationMarker(LngLat point) {
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
  public void drawRouteLine(List<LngLat> points) {
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
  }

  /**
   * Invoked by {@link MapView} when the parent activity or fragment is destroyed.
   */
  void onDestroy() {
    mapStateManager.setPosition(mapController.getPosition());
    mapStateManager.setZoom(mapController.getZoom());
    mapStateManager.setRotation(mapController.getRotation());
    mapStateManager.setTilt(mapController.getTilt());
  }
}
