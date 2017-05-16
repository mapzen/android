package com.mapzen.android.graphics;

import com.mapzen.android.core.CoreDI;
import com.mapzen.android.core.MapzenManager;
import com.mapzen.android.graphics.model.BubbleWrapStyle;
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
import static com.mapzen.android.graphics.SceneUpdateManager.STYLE_GLOBAL_VAR_LANGUAGE;
import static com.mapzen.android.graphics.SceneUpdateManager.STYLE_GLOBAL_VAR_TRANSIT_OVERLAY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.net.ssl.*")
@SuppressStaticInitializationFor("com.mapzen.tangram.MapController")
public class MapInitializerTest {

  private MapInitializer mapInitializer;

  @Before public void setUp() throws Exception {
    CoreDI.init(getMockContext());
    mapInitializer = new MapInitializer(mock(Context.class), mock(TileHttpHandler.class),
        new MapDataManager(), new MapStateManager(), new SceneUpdateManager());
  }

  @Test public void shouldNotBeNull() throws Exception {
    assertThat(mapInitializer).isNotNull();
  }

  @Test public void init_shouldReturnMapzenMap() throws Exception {
    final TestCallback callback = new TestCallback();
    final TestMapView mapView = mock(TestMapView.class);
    Context context = mock(Context.class);
    when(context.getApplicationContext()).thenReturn(mock(Context.class));
    when(mapView.getContext()).thenReturn(context);
    when(mapView.getTangramMapView()).thenCallRealMethod();
    MapzenManager.instance(getMockContext()).setApiKey("fake-mapzen-api-key");
    mapInitializer.init(mapView, callback);
    assertThat(callback.map).isInstanceOf(MapzenMap.class);
  }

  @Test public void init_shouldSetDefaultMapLocale() throws Exception {
    // Arrange
    MapView mapView = mock(MapView.class);
    TangramMapView tangramMapView = mock(TangramMapView.class);
    when(mapView.getTangramMapView()).thenReturn(tangramMapView);
    MapzenManager.instance(getMockContext()).setApiKey("fake-mapzen-api-key");

    // Act
    mapInitializer.init(mapView, new BubbleWrapStyle(), null);

    // Assert
    ArrayList<SceneUpdate> expected = new ArrayList<>();
    expected.add(new SceneUpdate(STYLE_GLOBAL_VAR_API_KEY, "fake-mapzen-api-key"));
    expected.add(new SceneUpdate(STYLE_GLOBAL_VAR_LANGUAGE, Locale.getDefault().getLanguage()));
    verify(tangramMapView).getMapAsync(any(com.mapzen.tangram.MapView.OnMapReadyCallback.class),
        anyString(), argThat(new SceneUpdatesMatcher(expected)));
  }

  @Test public void init_shouldSetGivenMapLocale() throws Exception {
    // Arrange
    MapView mapView = mock(MapView.class);
    TangramMapView tangramMapView = mock(TangramMapView.class);
    when(mapView.getTangramMapView()).thenReturn(tangramMapView);
    MapzenManager.instance(getMockContext()).setApiKey("fake-mapzen-api-key");

    // Act
    mapInitializer.init(mapView, new BubbleWrapStyle(), Locale.FRENCH, null);

    // Assert
    ArrayList<SceneUpdate> expected = new ArrayList<>();
    expected.add(new SceneUpdate(STYLE_GLOBAL_VAR_API_KEY, "fake-mapzen-api-key"));
    expected.add(new SceneUpdate(STYLE_GLOBAL_VAR_LANGUAGE, Locale.FRENCH.getLanguage()));
    verify(tangramMapView).getMapAsync(any(com.mapzen.tangram.MapView.OnMapReadyCallback.class),
        anyString(), argThat(new SceneUpdatesMatcher(expected)));
  }

  @Test public void init_shouldDefaultToTransitOverlayDisabled() throws Exception {
    // Arrange
    MapView mapView = mock(MapView.class);
    TangramMapView tangramMapView = mock(TangramMapView.class);
    when(mapView.getTangramMapView()).thenReturn(tangramMapView);
    MapzenManager.instance(getMockContext()).setApiKey("fake-mapzen-api-key");

    // Act
    mapInitializer.init(mapView, new BubbleWrapStyle(), null);

    // Assert
    ArrayList<SceneUpdate> expected = new ArrayList<>();
    expected.add(new SceneUpdate(STYLE_GLOBAL_VAR_API_KEY, "fake-mapzen-api-key"));
    expected.add(new SceneUpdate(STYLE_GLOBAL_VAR_LANGUAGE, Locale.getDefault().getLanguage()));
    expected.add(new SceneUpdate(STYLE_GLOBAL_VAR_TRANSIT_OVERLAY, "false"));
    verify(tangramMapView).getMapAsync(any(com.mapzen.tangram.MapView.OnMapReadyCallback.class),
        anyString(), argThat(new SceneUpdatesMatcher(expected)));
  }
}
