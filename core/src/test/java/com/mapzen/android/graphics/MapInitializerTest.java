package com.mapzen.android.graphics;

import com.mapzen.android.core.CoreDI;
import com.mapzen.android.core.MapzenManager;
import com.mapzen.android.graphics.model.BitmapMarkerManager;
import com.mapzen.android.graphics.model.BubbleWrapStyle;
import com.mapzen.android.graphics.model.RefillStyle;
import com.mapzen.android.graphics.model.ThemeColor;
import com.mapzen.android.graphics.model.ThemedMapStyle;
import com.mapzen.tangram.MapController;
import com.mapzen.tangram.SceneUpdate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

import android.content.Context;

import java.util.ArrayList;
import java.util.Locale;

import static com.mapzen.TestHelper.getMockContext;
import static com.mapzen.android.graphics.SceneUpdateManager.STYLE_GLOBAL_VAR_API_KEY;
import static com.mapzen.android.graphics.SceneUpdateManager.STYLE_GLOBAL_VAR_BIKE_OVERLAY;
import static com.mapzen.android.graphics.SceneUpdateManager.STYLE_GLOBAL_VAR_LANGUAGE;
import static com.mapzen.android.graphics.SceneUpdateManager.STYLE_GLOBAL_VAR_PATH_OVERLAY;
import static com.mapzen.android.graphics.SceneUpdateManager.STYLE_GLOBAL_VAR_TRANSIT_OVERLAY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.net.ssl.*")
@SuppressStaticInitializationFor("com.mapzen.tangram.MapController")
public class MapInitializerTest {

  private MapInitializer mapInitializer;
  private MapStateManager mapStateManager;
  private PersistDataManagers persistDataManagers;

  @Before public void setUp() throws Exception {
    CoreDI.init(getMockContext());
    persistDataManagers = new PersistDataManagers();
    mapStateManager = persistDataManagers.getDefaultMapStateManager();
    mapInitializer = new MapInitializer(mock(Context.class), mock(MapzenMapHttpHandler.class),
        persistDataManagers, new SceneUpdateManager(),
        new BitmapMarkerManager(null, null), new ImportYamlGenerator());
  }

  @Test public void shouldNotBeNull() throws Exception {
    assertThat(mapInitializer).isNotNull();
  }

  @Test public void init_shouldReturnMapzenMap() throws Exception {
    final TestCallback callback = new TestCallback();
    final TestMapView mapView = new TestMapView(callback);
    MapzenManager.instance(getMockContext()).setApiKey("fake-mapzen-api-key");
    mapInitializer.init(mapView, callback);
    assertThat(callback.map).isInstanceOf(MapzenMap.class);
  }

  @Test public void init_shouldSetDefaultMapLocale() throws Exception {
    // Arrange
    TestCallback callback = mock(TestCallback.class);
    TestMapView mapView = mock(TestMapView.class);
    TestTangramMapView tangramMapView = mock(TestTangramMapView.class);
    MapController mapController = mock(MapController.class);
    when(tangramMapView.getMap(any(MapController.SceneLoadListener.class))).thenReturn(
        mapController);
    tangramMapView.mapView = mapView;
    tangramMapView.callback = callback;
    when(mapView.getTangramMapView()).thenReturn(tangramMapView);
    MapzenManager.instance(getMockContext()).setApiKey("fake-mapzen-api-key");

    // Act
    mapInitializer.init(mapView, new BubbleWrapStyle(), null);

    // Assert
    ArrayList<SceneUpdate> expected = new ArrayList<>();
    expected.add(new SceneUpdate(STYLE_GLOBAL_VAR_API_KEY, "fake-mapzen-api-key"));
    expected.add(new SceneUpdate(STYLE_GLOBAL_VAR_LANGUAGE, Locale.getDefault().getLanguage()));
    expected.add(new SceneUpdate(STYLE_GLOBAL_VAR_TRANSIT_OVERLAY, "false"));
    expected.add(new SceneUpdate(STYLE_GLOBAL_VAR_BIKE_OVERLAY, "false"));
    expected.add(new SceneUpdate(STYLE_GLOBAL_VAR_PATH_OVERLAY, "true"));
    verify(tangramMapView).getMap(any(MapController.SceneLoadListener.class));
    verify(mapController).loadSceneYamlAsync(anyString(), anyString(), argThat(
        new SceneUpdatesMatcher(expected)));
  }

  @Test public void init_shouldSetGivenMapLocale() throws Exception {
    // Arrange
    TestCallback callback = mock(TestCallback.class);
    TestMapView mapView = mock(TestMapView.class);
    TestTangramMapView tangramMapView = mock(TestTangramMapView.class);
    MapController mapController = mock(MapController.class);
    when(tangramMapView.getMap(any(MapController.SceneLoadListener.class))).thenReturn(
        mapController);
    tangramMapView.mapView = mapView;
    tangramMapView.callback = callback;
    when(mapView.getTangramMapView()).thenReturn(tangramMapView);
    MapzenManager.instance(getMockContext()).setApiKey("fake-mapzen-api-key");

    // Act
    mapInitializer.init(mapView, new BubbleWrapStyle(), Locale.FRENCH, null);

    // Assert
    ArrayList<SceneUpdate> expected = new ArrayList<>();
    expected.add(new SceneUpdate(STYLE_GLOBAL_VAR_API_KEY, "fake-mapzen-api-key"));
    expected.add(new SceneUpdate(STYLE_GLOBAL_VAR_LANGUAGE, Locale.FRENCH.getLanguage()));
    expected.add(new SceneUpdate(STYLE_GLOBAL_VAR_TRANSIT_OVERLAY, "false"));
    expected.add(new SceneUpdate(STYLE_GLOBAL_VAR_BIKE_OVERLAY, "false"));
    expected.add(new SceneUpdate(STYLE_GLOBAL_VAR_PATH_OVERLAY, "true"));
    verify(tangramMapView).getMap(any(MapController.SceneLoadListener.class));
    verify(mapController).loadSceneYamlAsync(anyString(), anyString(), argThat(
        new SceneUpdatesMatcher(expected)));
  }

  @Test public void init_shouldDefaultToOverlaysDisabledExceptPath() throws Exception {
    // Arrange
    TestCallback callback = mock(TestCallback.class);
    TestMapView mapView = mock(TestMapView.class);
    TestTangramMapView tangramMapView = mock(TestTangramMapView.class);
    MapController mapController = mock(MapController.class);
    when(tangramMapView.getMap(any(MapController.SceneLoadListener.class))).thenReturn(
        mapController);
    tangramMapView.mapView = mapView;
    tangramMapView.callback = callback;
    when(mapView.getTangramMapView()).thenReturn(tangramMapView);
    MapzenManager.instance(getMockContext()).setApiKey("fake-mapzen-api-key");

    // Act
    mapInitializer.init(mapView, new BubbleWrapStyle(), null);

    // Assert
    ArrayList<SceneUpdate> expected = new ArrayList<>();
    expected.add(new SceneUpdate(STYLE_GLOBAL_VAR_API_KEY, "fake-mapzen-api-key"));
    expected.add(new SceneUpdate(STYLE_GLOBAL_VAR_LANGUAGE, Locale.getDefault().getLanguage()));
    expected.add(new SceneUpdate(STYLE_GLOBAL_VAR_TRANSIT_OVERLAY, "false"));
    expected.add(new SceneUpdate(STYLE_GLOBAL_VAR_BIKE_OVERLAY, "false"));
    expected.add(new SceneUpdate(STYLE_GLOBAL_VAR_PATH_OVERLAY, "true"));
    verify(tangramMapView).getMap(any(MapController.SceneLoadListener.class));
    verify(mapController).loadSceneYamlAsync(anyString(), anyString(), argThat(
        new SceneUpdatesMatcher(expected)));
  }

  @Test public void init_shouldCallLoadSceneYamlAsync() throws Exception {
    // Arrange
    TestCallback callback = mock(TestCallback.class);
    TestMapView mapView = mock(TestMapView.class);
    TestTangramMapView tangramMapView = mock(TestTangramMapView.class);
    MapController mapController = mock(MapController.class);
    when(tangramMapView.getMap(any(MapController.SceneLoadListener.class))).thenReturn(
        mapController);
    tangramMapView.mapView = mapView;
    tangramMapView.callback = callback;
    when(mapView.getTangramMapView()).thenReturn(tangramMapView);
    MapzenManager.instance(getMockContext()).setApiKey("fake-mapzen-api-key");

    // Act
    RefillStyle refillStyle = new RefillStyle();
    mapInitializer.init(mapView, refillStyle, null);

    // Assert
    ArrayList<SceneUpdate> expected = new ArrayList<>();
    expected.add(new SceneUpdate(STYLE_GLOBAL_VAR_API_KEY, "fake-mapzen-api-key"));
    expected.add(new SceneUpdate(STYLE_GLOBAL_VAR_LANGUAGE, Locale.getDefault().getLanguage()));
    expected.add(new SceneUpdate(STYLE_GLOBAL_VAR_TRANSIT_OVERLAY, "false"));
    expected.add(new SceneUpdate(STYLE_GLOBAL_VAR_BIKE_OVERLAY, "false"));
    expected.add(new SceneUpdate(STYLE_GLOBAL_VAR_PATH_OVERLAY, "true"));
    String yaml = "{ import: [ refill-style.yaml, themes/label-5.yaml, "
        + "themes/detail-10.yaml, themes/color-black.yaml ] }";
    verify(mapController).loadSceneYamlAsync(eq(yaml), eq(refillStyle.getStyleRootPath()),
    argThat(new SceneUpdatesMatcher(expected)));
  }

  @Test public void init_shouldSetDefaultLodForThemedStyle() throws Exception {
    TestMapView mapView = mock(TestMapView.class);
    TestTangramMapView tangramMapView = mock(TestTangramMapView.class);
    MapController mapController = mock(MapController.class);
    when(tangramMapView.getMap(any(MapController.SceneLoadListener.class))).thenReturn(
        mapController);
    when(mapView.getTangramMapView()).thenReturn(tangramMapView);
    ThemedMapStyle style = new RefillStyle();
    mapStateManager.setLod(-1);
    mapInitializer.init(mapView, style, new TestCallback());
    assertThat(mapStateManager.getLod()).isEqualTo(style.getDefaultLod());
  }

  @Test public void init_shouldSetDefaultLabelLevelForThemedStyle() throws Exception {
    TestMapView mapView = mock(TestMapView.class);
    TestTangramMapView tangramMapView = mock(TestTangramMapView.class);
    MapController mapController = mock(MapController.class);
    when(tangramMapView.getMap(any(MapController.SceneLoadListener.class))).thenReturn(
        mapController);
    when(mapView.getTangramMapView()).thenReturn(tangramMapView);
    ThemedMapStyle style = new RefillStyle();
    mapStateManager.setLabelLevel(-1);
    mapInitializer.init(mapView, style, new TestCallback());
    assertThat(mapStateManager.getLabelLevel()).isEqualTo(style.getDefaultLabelLevel());
  }

  @Test public void init_shouldSetDefaultColorForThemedStyle() throws Exception {
    TestMapView mapView = mock(TestMapView.class);
    TestTangramMapView tangramMapView = mock(TestTangramMapView.class);
    MapController mapController = mock(MapController.class);
    when(tangramMapView.getMap(any(MapController.SceneLoadListener.class))).thenReturn(
        mapController);
    when(mapView.getTangramMapView()).thenReturn(tangramMapView);
    ThemedMapStyle style = new RefillStyle();
    mapStateManager.setThemeColor(ThemeColor.NONE);
    mapInitializer.init(mapView, style, new TestCallback());
    assertThat(mapStateManager.getThemeColor()).isEqualTo(style.getDefaultColor());
  }

  @Test public void init_shouldUseCorrectMapStateManager() throws Exception {
    PersistDataManagers persistManagers = mock(PersistDataManagers.class);
    MapInitializer initializer = new MapInitializer(mock(Context.class),
        mock(MapzenMapHttpHandler.class), persistManagers, new SceneUpdateManager(),
        new BitmapMarkerManager(null, null),
        new ImportYamlGenerator());
    String mapId = "mapId";
    when(persistManagers.getMapStateManager(mapId)).thenReturn(mock(MapStateManager.class));
    TestMapView mapView = mock(TestMapView.class);
    TestTangramMapView tangramMapView = mock(TestTangramMapView.class);
    MapController mapController = mock(MapController.class);
    when(tangramMapView.getMap(any(MapController.SceneLoadListener.class))).thenReturn(
        mapController);
    when(mapView.getTangramMapView()).thenReturn(tangramMapView);
    initializer.init(mapView, mapId, new TestCallback());
    verify(persistManagers, times(2)).getMapStateManager(mapId);
  }

  @Test public void init_shouldUseCorrectMapDataManager() throws Exception {
    PersistDataManagers persistManagers = mock(PersistDataManagers.class);
    MapInitializer initializer = new MapInitializer(mock(Context.class),
        mock(MapzenMapHttpHandler.class), persistManagers, new SceneUpdateManager(),
        new BitmapMarkerManager(null, null),
        new ImportYamlGenerator());
    String mapId = "mapId";
    when(persistManagers.getMapStateManager(mapId)).thenReturn(mock(MapStateManager.class));
    TestMapView mapView = mock(TestMapView.class);
    TestTangramMapView tangramMapView = mock(TestTangramMapView.class);
    MapController mapController = mock(MapController.class);
    when(tangramMapView.getMap(any(MapController.SceneLoadListener.class))).thenReturn(
        mapController);
    when(mapView.getTangramMapView()).thenReturn(tangramMapView);
    initializer.init(mapView, mapId, new TestCallback());
    verify(persistManagers).getMapDataManager(mapId);
  }

  @Test public void takedown_shouldResetListener() throws Exception {
    MapController controller = mock(MapController.class);
    mapInitializer.controller = controller;
    mapInitializer.takeDown();
    verify(controller).setSceneLoadListener(null);
  }
}
