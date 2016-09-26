package com.mapzen.android.graphics;

import com.mapzen.android.R;
import com.mapzen.android.lost.api.LocationServices;
import com.mapzen.android.lost.api.LostApiClient;
import com.mapzen.android.lost.internal.FusedLocationProviderApiImpl;
import com.mapzen.android.graphics.model.Marker;
import com.mapzen.android.graphics.model.Polygon;
import com.mapzen.android.graphics.model.Polyline;
import com.mapzen.tangram.LngLat;
import com.mapzen.tangram.MapController;
import com.mapzen.tangram.MapData;
import com.mapzen.tangram.TestMapData;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import android.content.Context;
import android.location.Location;
import android.view.View;
import android.widget.ImageButton;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mock;

@RunWith(PowerMockRunner.class) @SuppressStaticInitializationFor("com.mapzen.tangram.MapController")
@PrepareForTest(OverlayManager.class) public class OverlayManagerTest {

  private MapController mapController;
  private MapStateManager mapStateManager;
  private OverlayManager overlayManager;
  private MapView mapView;
  private LostApiClient lostApiClient;
  private TestButton findMeButton;
  private TestMapData mapData;

  @Before public void setup() throws Exception {
    mapController = mock(TestMapController.class);
    lostApiClient = mock(LostApiClient.class);
    mapView = mock(MapView.class);
    mapData = new TestMapData("test");
    setFinalStatic(LocationServices.class.getDeclaredField("FusedLocationApi"),
        Mockito.mock(FusedLocationProviderApiImpl.class));
    MapDataManager mapDataManager = new MapDataManager();
    mapStateManager = mock(MapStateManager.class);
    overlayManager = new OverlayManager(mapView, mapController, mapDataManager, mapStateManager,
        lostApiClient);
    when(LocationServices.FusedLocationApi.getLastLocation(lostApiClient))
        .thenReturn(new Location("test"));
    findMeButton = new TestButton(null);
    when(mapController.addDataLayer(any(String.class))).thenReturn(mapData);
    when(mapView.showFindMe()).thenReturn(findMeButton);
    when(mapView.findViewById(R.id.mz_find_me)).thenReturn(findMeButton);
  }

  @Test public void setMyLocationEnabled_shouldCenterMapIfFindMeIsActive() throws Exception {
    doCallRealMethod().when(mapController).setPositionEased(any(LngLat.class), anyInt());
    when(mapController.getPosition()).thenCallRealMethod();

    overlayManager.setMyLocationEnabled(true);
    findMeButton.performClick();
    Location location = mock(Location.class);
    when(location.getLatitude()).thenReturn(-70.0);
    when(location.getLongitude()).thenReturn(-40.0);
    overlayManager.locationListener.onLocationChanged(location);
    assertThat(mapController.getPosition().latitude).isEqualTo(-70.0);
    assertThat(mapController.getPosition().longitude).isEqualTo(-40.0);
  }

  @Test public void setMyLocationEnabled_shouldNotCenterMapIfFindMeIsInactive() throws Exception {
    doCallRealMethod().when(mapController).setPositionEased(any(LngLat.class), anyInt());
    when(mapController.getPosition()).thenCallRealMethod();

    overlayManager.setMyLocationEnabled(true);
    Location location = mock(Location.class);
    when(location.getLatitude()).thenReturn(-70.0);
    when(location.getLongitude()).thenReturn(-40.0);
    overlayManager.locationListener.onLocationChanged(location);
    assertThat(mapController.getPosition().latitude).isEqualTo(0.0);
    assertThat(mapController.getPosition().longitude).isEqualTo(0.0);
  }

  @Test public void setMyLocationEnabled_shouldNotChangeZoomLevel() throws Exception {
    doCallRealMethod().when(mapController).setPositionEased(any(LngLat.class), anyInt());
    doCallRealMethod().when(mapController).setZoom(anyFloat());
    when(mapController.getZoom()).thenCallRealMethod();

    mapController.setZoom(17);
    overlayManager.setMyLocationEnabled(true);
    Location location = new Location("test");
    Whitebox.invokeMethod(overlayManager.locationListener, "onLocationChanged", location);
    assertThat(mapController.getZoom()).isEqualTo(17);
  }

  @Test public void setMyLocationEnabled_shouldNotChangeTilt() throws Exception {
    doCallRealMethod().when(mapController).setPositionEased(any(LngLat.class), anyInt());
    doCallRealMethod().when(mapController).setTilt(anyFloat());
    when(mapController.getTilt()).thenCallRealMethod();

    mapController.setTilt(8);
    overlayManager.setMyLocationEnabled(true);
    Location location = new Location("test");
    Whitebox.invokeMethod(overlayManager.locationListener, "onLocationChanged", location);
    assertThat(mapController.getTilt()).isEqualTo(8);
  }

  @Test public void setMyLocationEnabled_shouldNotChangeRotation() throws Exception {
    doCallRealMethod().when(mapController).setPositionEased(any(LngLat.class), anyInt());
    doCallRealMethod().when(mapController).setRotation(anyFloat());
    when(mapController.getRotation()).thenCallRealMethod();

    mapController.setRotation(8);
    overlayManager.setMyLocationEnabled(true);
    Location location = new Location("test");
    Whitebox.invokeMethod(overlayManager.locationListener, "onLocationChanged", location);
    assertThat(mapController.getRotation()).isEqualTo(8);
  }

  @Test public void setMyLocationEnabled_shouldUpdateMapStateManager() {
    overlayManager.setMyLocationEnabled(true);
    findMeButton.performClick();
    verify(mapStateManager).setZoom(16f);
    Location loc = LocationServices.FusedLocationApi.getLastLocation(lostApiClient);
    LngLat lngLat = new LngLat(loc.getLongitude(), loc.getLatitude());
    verify(mapStateManager).setPosition(lngLat);
  }

  @Test public void isMyLocationEnabled_shouldReturnTrue() throws Exception {
    overlayManager.setMyLocationEnabled(true);
    assertThat(overlayManager.isMyLocationEnabled()).isTrue();
  }

  @Test public void isMyLocationEnabled_shouldReturnFalse() throws Exception {
    overlayManager.setMyLocationEnabled(false);
    assertThat(overlayManager.isMyLocationEnabled()).isFalse();
  }

  @Test public void addPolyline_shouldReturnMapData() throws Exception {
    Polyline polyline = new Polyline.Builder().add(new LngLat(-73.9903, 40.74433))
        .add(new LngLat(-73.984770, 40.734807))
        .add(new LngLat(-73.998674, 40.732172))
        .add(new LngLat(-73.996142, 40.741050))
        .build();
    MapData polylineData = overlayManager.addPolyline(polyline);
    assertThat(polylineData).isNotNull();
  }

  @Test(expected = IllegalArgumentException.class)
  public void addPolyline_shouldRequireTwoPoints() {
    Polyline polyline = new Polyline.Builder().add(new LngLat(-73.9903, 40.74433)).build();
    overlayManager.addPolyline(null);
    overlayManager.addPolyline(polyline);
  }

  @Test public void addPolygon_shouldReturnMapData() throws Exception {
    Polygon polygon = new Polygon.Builder().add(new LngLat(-73.9903, 40.74433))
        .add(new LngLat(-73.984770, 40.734807))
        .add(new LngLat(-73.998674, 40.732172))
        .add(new LngLat(-73.996142, 40.741050))
        .build();
    MapData polygonData = overlayManager.addPolygon(polygon);
    assertThat(polygonData).isNotNull();
  }

  @Test(expected = IllegalArgumentException.class) public void addPolygon_shouldRequireTwoPoints() {
    Polygon polygon = new Polygon.Builder().add(new LngLat(-73.9903, 40.74433)).build();
    overlayManager.addPolygon(null);
    overlayManager.addPolygon(polygon);
  }

  @Test public void addMarker_shouldReturnMapData() throws Exception {
    Marker marker = new Marker(-73.9903, 40.74433);
    MapData markerMapData = overlayManager.addMarker(marker);
    assertThat(markerMapData).isNotNull();
  }

  @Test(expected = IllegalArgumentException.class) public void addMarker_shouldRequirePoint() {
    MapData markerMapData = overlayManager.addMarker(null);
    assertThat(markerMapData).isNotNull();
  }

  @Test public void drawRoutePins_shouldAddStartPin() {
    when(mapController.addDataLayer(anyString())).thenCallRealMethod();

    LngLat start = new LngLat(-123, -70);
    LngLat end = new LngLat(-123.1, -70.1);
    overlayManager.drawRoutePins(start, end);
    verify(mapController).addDataLayer("mz_route_start");
  }

  @Test public void drawRoutePins_shouldNotAddStartPin() {
    when(mapController.addDataLayer(anyString())).thenCallRealMethod();

    LngLat start = new LngLat(-123, -70);
    LngLat end = new LngLat(-123.1, -70.1);
    overlayManager.drawRoutePins(start, end);
    overlayManager.drawRoutePins(start, end);
    verify(mapController, times(1)).addDataLayer("mz_route_start");
  }

  @Test public void drawRoutePins_shouldAddEndPin() {
    when(mapController.addDataLayer(anyString())).thenCallRealMethod();

    LngLat start = new LngLat(-123, -70);
    LngLat end = new LngLat(-123.1, -70.1);
    overlayManager.drawRoutePins(start, end);
    verify(mapController).addDataLayer("mz_route_destination");
  }

  @Test public void drawRoutePins_shouldNotAddEndPin() {
    when(mapController.addDataLayer(anyString())).thenCallRealMethod();

    LngLat start = new LngLat(-123, -70);
    LngLat end = new LngLat(-123.1, -70.1);
    overlayManager.drawRoutePins(start, end);
    overlayManager.drawRoutePins(start, end);
    verify(mapController).addDataLayer("mz_route_destination");
  }

  @Test public void drawRoutePins_shouldRender() {
    PowerMockito.doCallRealMethod().when(mapController).requestRender();
    when(mapController.addDataLayer(anyString())).thenCallRealMethod();

    LngLat start = new LngLat(-123, -70);
    LngLat end = new LngLat(-123.1, -70.1);
    overlayManager.drawRoutePins(start, end);
    verify(mapController).requestRender();
  }

  @Test public void drawDroppedPin_shouldAddDataLayer() {
    PowerMockito.doCallRealMethod().when(mapController).addDataLayer(anyString());

    LngLat point = new LngLat(-123, -70);
    overlayManager.drawDroppedPin(point);
    verify(mapController).addDataLayer("mz_dropped_pin");
  }

  @Test public void drawDroppedPin_shouldRender() {
    PowerMockito.doCallRealMethod().when(mapController).addDataLayer(anyString());
    PowerMockito.doCallRealMethod().when(mapController).requestRender();

    LngLat point = new LngLat(-123, -70);
    overlayManager.drawDroppedPin(point);
    verify(mapController).requestRender();
  }

  @Test public void drawSearchResult_shouldAddDataLayer() {
    PowerMockito.doCallRealMethod().when(mapController).addDataLayer(anyString());

    LngLat point = new LngLat(-123, -70);
    overlayManager.drawSearchResult(point, true, 0);
    verify(mapController).addDataLayer("mz_search_result");
  }

  @Test public void drawSearchResult_shouldRequestRender() {
    PowerMockito.doCallRealMethod().when(mapController).addDataLayer(anyString());
    PowerMockito.doCallRealMethod().when(mapController).requestRender();

    LngLat point = new LngLat(-123, -70);
    overlayManager.drawSearchResult(point, true, 0);
    verify(mapController).requestRender();
  }

  @Test public void drawRoutePin_shouldAddDataLayer() {
    PowerMockito.doCallRealMethod().when(mapController).addDataLayer(anyString());

    LngLat point = new LngLat(-123, -70);
    overlayManager.drawRouteLocationMarker(point);
    verify(mapController).addDataLayer("mz_route_location");
  }

  @Test public void drawRoutePin_shouldAddDataLayerOnce() {
    PowerMockito.doCallRealMethod().when(mapController).addDataLayer(anyString());

    LngLat point = new LngLat(-123, -70);
    overlayManager.drawRouteLocationMarker(point);
    overlayManager.drawRouteLocationMarker(point);
    verify(mapController, times(1)).addDataLayer("mz_route_location");
  }

  @Test public void drawRoutePin_shouldRequestRender() {
    PowerMockito.doCallRealMethod().when(mapController).addDataLayer(anyString());
    PowerMockito.doCallRealMethod().when(mapController).requestRender();

    LngLat point = new LngLat(-123, -70);
    overlayManager.drawRouteLocationMarker(point);
    verify(mapController).requestRender();
  }

  @Test public void drawRouteLine_shouldAddDataLayer() {
    PowerMockito.doCallRealMethod().when(mapController).addDataLayer(anyString());

    LngLat point1 = new LngLat(-123, -70.0);
    LngLat point2 = new LngLat(-123, -70.1);
    LngLat point3 = new LngLat(-123, -70.2);
    List<LngLat> points = new ArrayList<>();
    points.add(point1);
    points.add(point2);
    points.add(point3);
    overlayManager.drawRouteLine(points);
    verify(mapController).addDataLayer("mz_route_line");
  }

  @Test public void drawRouteLine_shouldAddDataLayerOnce() {
    PowerMockito.doCallRealMethod().when(mapController).addDataLayer(anyString());

    LngLat point1 = new LngLat(-123, -70.0);
    LngLat point2 = new LngLat(-123, -70.1);
    LngLat point3 = new LngLat(-123, -70.2);
    List<LngLat> points = new ArrayList<>();
    points.add(point1);
    points.add(point2);
    points.add(point3);
    overlayManager.drawRouteLine(points);
    overlayManager.drawRouteLine(points);
    verify(mapController, times(1)).addDataLayer("mz_route_line");
  }

  @Test public void drawRouteLine_shouldRequestRender() {
    PowerMockito.doCallRealMethod().when(mapController).addDataLayer(anyString());
    PowerMockito.doCallRealMethod().when(mapController).requestRender();

    LngLat point1 = new LngLat(-123, -70.0);
    LngLat point2 = new LngLat(-123, -70.1);
    LngLat point3 = new LngLat(-123, -70.2);
    List<LngLat> points = new ArrayList<>();
    points.add(point1);
    points.add(point2);
    points.add(point3);
    overlayManager.drawRouteLine(points);
    verify(mapController).requestRender();
  }

  @Test public void drawTransitRouteLine_shouldAddDataSourceOnce() {
    PowerMockito.doCallRealMethod().when(mapController).addDataLayer(anyString());

    ArrayList<LngLat> points = new ArrayList();
    points.add(new LngLat(-122.39353246246766, 37.78662344689961));
    points.add(new LngLat(-122.39309926415683, 37.791273135641994));
    String hex = "#ff0000";
    overlayManager.drawTransitRouteLine(points, points, hex);
    overlayManager.drawTransitRouteLine(points, points, hex);
    verify(mapController, times(1)).addDataLayer("mz_route_line_transit");
  }

  @Test public void drawTransitRouteLine_shouldAddStationDataSourceOnce() {
    PowerMockito.doCallRealMethod().when(mapController).addDataLayer(anyString());

    ArrayList<LngLat> points = new ArrayList();
    points.add(new LngLat(-122.39353246246766, 37.78662344689961));
    points.add(new LngLat(-122.39309926415683, 37.791273135641994));
    String hex = "#ff0000";
    overlayManager.drawTransitRouteLine(points, points, hex);
    overlayManager.drawTransitRouteLine(points, points, hex);
    verify(mapController, times(1)).addDataLayer("mz_route_transit_stop");
  }

  @Test public void drawTransitRouteLine_shouldRequestRender() {
    PowerMockito.doCallRealMethod().when(mapController).addDataLayer(anyString());
    PowerMockito.doCallRealMethod().when(mapController).requestRender();

    ArrayList<LngLat> points = new ArrayList();
    points.add(new LngLat(-122.39353246246766, 37.78662344689961));
    points.add(new LngLat(-122.39309926415683, 37.791273135641994));
    String hex = "#ff0000";
    overlayManager.drawTransitRouteLine(points, points, hex);
    verify(mapController).requestRender();
  }

  @Test public void setFindMeOnClickListener_shouldInvokeListenerOnButtonClick() throws Exception {
    TestOnClickListener listener = new TestOnClickListener();
    when(mapView.showFindMe()).thenReturn(findMeButton);
    overlayManager.setFindMeOnClickListener(listener);
    overlayManager.setMyLocationEnabled(true);
    findMeButton.performClick();
    assertThat(listener.click).isTrue();
  }

  @Test public void onClickFindMe_shouldUpdateCurrentLocationMarker() throws Exception {
    overlayManager.setMyLocationEnabled(true);
    mapData.clear();
    findMeButton.performClick();
    assertThat(mapData.point).isNotNull();
  }

  @Test public void onClickFindMe_shouldUpdateMapPosition() throws Exception {
    overlayManager.setMyLocationEnabled(true);
    findMeButton.performClick();
    verify(mapController, times(1)).setPositionEased(any(LngLat.class), anyInt());
    verify(mapController, times(1)).requestRender();
  }

  @Test public void onClickFindMe_shouldZoomMap() throws Exception {
    overlayManager.setMyLocationEnabled(true);
    findMeButton.performClick();
    verify(mapController, times(1)).setZoomEased(anyFloat(), anyInt());
    verify(mapController, times(1)).requestRender();
  }

  @Test public void onClickFindMe_shouldToggleViewStateActivated() throws Exception {
    overlayManager.setMyLocationEnabled(true);

    findMeButton.performClick();
    assertThat(findMeButton.isActivated()).isTrue();

    findMeButton.performClick();
    assertThat(findMeButton.isActivated()).isFalse();
  }

  @Test public void onPan_shouldDeactivateFindMe() throws Exception {
    findMeButton.setActivated(true);
    overlayManager.onPan(1f, 2f, 3f, 4f);
    assertThat(findMeButton.isActivated()).isFalse();
  }

  @Test public void onFling_shouldDeactivateFindMe() throws Exception {
    findMeButton.setActivated(true);
    overlayManager.onFling(1f, 2f, 3f, 4f);
    assertThat(findMeButton.isActivated()).isFalse();
  }

  private class TestOnClickListener implements View.OnClickListener {
    private boolean click = false;

    @Override public void onClick(View v) {
      click = true;
    }
  }

  private class TestButton extends ImageButton {
    private OnClickListener listener;
    private boolean activated;

    public TestButton(Context context) {
      super(context);
    }

    @Override public void setOnClickListener(OnClickListener l) {
      listener = l;
    }

    @Override public boolean performClick() {
      if (listener != null) {
        listener.onClick(this);
      }

      return true;
    }

    @Override public void setActivated(boolean activated) {
      this.activated = activated;
    }

    @Override public boolean isActivated() {
      return activated;
    }
  }

  static void setFinalStatic(Field field, Object newValue) throws Exception {
    field.setAccessible(true);
    // remove final modifier from field
    Field modifiersField = Field.class.getDeclaredField("modifiers");
    modifiersField.setAccessible(true);
    modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
    field.set(null, newValue);
  }
}
