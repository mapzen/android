package com.mapzen.android.graphics;

import com.mapzen.tangram.HttpHandler;
import com.mapzen.tangram.MapController;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

import android.content.Context;

import java.util.Locale;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.net.ssl.*")
@SuppressStaticInitializationFor("com.mapzen.tangram.MapController")
public class MapReadyInitializerTest {

  MapReadyInitializer initializer = new MapReadyInitializer();

  @Test public void onMapReady_shouldSetMapControllerSceneLoadListenerNull() throws Exception {
    MapView mapView = mock(MapView.class);
    Context context = mock(Context.class);
    when(mapView.getContext()).thenReturn(context);
    when(context.getApplicationContext()).thenReturn(mock(Context.class));
    TangramMapView tangramMapView = mock(TangramMapView.class);
    MapController mapController = mock(MapController.class);
    when(mapView.getTangramMapView()).thenReturn(tangramMapView);
    when(tangramMapView.getMap(any(MapController.SceneLoadListener.class))).thenReturn(
        mapController);
    initializer.onMapReady(mapView, mock(MapzenMapHttpHandler.class),
        mock(OnMapReadyCallback.class), mock(MapDataManager.class), mock(MapStateManager.class),
        mock(SceneUpdateManager.class), new Locale("en_us"));
    verify(mapController).setSceneLoadListener(null);
  }

  @Test public void onMapReady_shouldSetMapControllerHttpHandler() throws Exception {
    MapView mapView = mock(MapView.class);
    Context context = mock(Context.class);
    when(mapView.getContext()).thenReturn(context);
    when(context.getApplicationContext()).thenReturn(mock(Context.class));
    TangramMapView tangramMapView = mock(TangramMapView.class);
    MapController mapController = mock(MapController.class);
    when(mapView.getTangramMapView()).thenReturn(tangramMapView);
    when(tangramMapView.getMap(any(MapController.SceneLoadListener.class))).thenReturn(
        mapController);
    MapzenMapHttpHandler mapzenHttpHandler = mock(MapzenMapHttpHandler.class);
    HttpHandler httpHandler = mock(HttpHandler.class);
    when(mapzenHttpHandler.httpHandler()).thenReturn(httpHandler);
    initializer.onMapReady(mapView, mapzenHttpHandler,
        mock(OnMapReadyCallback.class), mock(MapDataManager.class), mock(MapStateManager.class),
        mock(SceneUpdateManager.class), new Locale("en_us"));
    verify(mapController).setHttpHandler(httpHandler);
  }

  @Test public void onMapReady_shouldCallOnMapReady() throws Exception {
    MapView mapView = mock(MapView.class);
    Context context = mock(Context.class);
    when(mapView.getContext()).thenReturn(context);
    when(context.getApplicationContext()).thenReturn(mock(Context.class));
    TangramMapView tangramMapView = mock(TangramMapView.class);
    MapController mapController = mock(MapController.class);
    when(mapView.getTangramMapView()).thenReturn(tangramMapView);
    when(tangramMapView.getMap(any(MapController.SceneLoadListener.class))).thenReturn(
        mapController);
    OnMapReadyCallback callback = mock(OnMapReadyCallback.class);
    initializer.onMapReady(mapView, mock(MapzenMapHttpHandler.class),
        callback, mock(MapDataManager.class), mock(MapStateManager.class),
        mock(SceneUpdateManager.class), new Locale("en_us"));
    verify(callback).onMapReady(any(MapzenMap.class));
  }
}
