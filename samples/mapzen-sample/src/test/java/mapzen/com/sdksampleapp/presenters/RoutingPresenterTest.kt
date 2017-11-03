package mapzen.com.sdksampleapp.presenters

import android.graphics.PointF
import com.mapzen.android.graphics.model.Marker
import com.mapzen.android.graphics.model.Polyline
import com.mapzen.android.routing.MapzenRouter
import com.mapzen.tangram.LngLat
import com.mapzen.tangram.MapData
import com.mapzen.tangram.TouchInput
import com.mapzen.valhalla.Route
import com.mapzen.valhalla.RouteCallback
import mapzen.com.sdksampleapp.TestRoutingController
import mapzen.com.sdksampleapp.presenters.RoutingPresenterImpl.Companion.TAB_POSITION_AUTO
import mapzen.com.sdksampleapp.presenters.RoutingPresenterImpl.Companion.TAB_POSITION_BIKE
import mapzen.com.sdksampleapp.presenters.RoutingPresenterImpl.Companion.TAB_POSITION_TRANSIT
import mapzen.com.sdksampleapp.presenters.RoutingPresenterImpl.Companion.TAB_POSITION_WALK
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

class RoutingPresenterTest {

  lateinit var router: MapzenRouter
  lateinit var presenter: RoutingPresenterImpl
  val controller = TestRoutingController()

  @Before fun setup() {
    router = mock(MapzenRouter::class.java)
    presenter = RoutingPresenterImpl(router)
    presenter.controller = controller
  }

  @Test fun onCreate_shouldSetRouterCallback() {
    presenter.onCreate()
    verify(router).setCallback(ArgumentMatchers.any(RouteCallback::class.java))
  }

  @Test fun routeCallbackSuccess_shouldDisplayRoute() {
    presenter.success(mock(Route::class.java))
    verify(controller.getMapzenMap())?.addPolyline(any(Polyline::class.java))
  }

  @Test fun setupMap_shouldPersistMapData() {
    presenter.setupMap()
    verify(controller.getMapzenMap())?.setPersistMapData(true)
  }

  @Test fun setupMap_shouldEnableMyLocation() {
    presenter.setupMap()
    verify(controller.getMapzenMap())?.isMyLocationEnabled = true
  }

  @Test fun setupMap_shouldSetTapResponder() {
    presenter.setupMap()
    verify(controller.getMapzenMap())?.tapResponder = any(TouchInput.TapResponder::class.java)
  }

  @Test fun onTabPositionSelected_shouldUpdateSelectedMode() {
    presenter.selectedMode = TAB_POSITION_TRANSIT
    presenter.onTabPositionSelected(TAB_POSITION_AUTO)
    assertThat(presenter.selectedMode as Int).isEqualTo(TAB_POSITION_AUTO)
  }

  @Test fun onTabPositionSelected_auto_shouldSetDriving() {
    presenter.onTabPositionSelected(TAB_POSITION_AUTO)
    verify(router).setDriving()
  }

  @Test fun onTabPositionSelected_transit_shouldSetMultimodal() {
    presenter.onTabPositionSelected(TAB_POSITION_TRANSIT)
    verify(router).setMultimodal()
  }

  @Test fun onTabPositionSelected_bike_shouldSetBiking() {
    presenter.onTabPositionSelected(TAB_POSITION_BIKE)
    verify(router).setBiking()
  }

  @Test fun onTabPositionSelected_walk_shouldSetWalking() {
    presenter.onTabPositionSelected(TAB_POSITION_WALK)
    verify(router).setWalking()
  }

  @Test fun onTabPositionSelected_shouldGetRouteWhenLocationsSet() {
    presenter.addPin(0f, 0f)
    presenter.addPin(0f, 0f)
    presenter.onTabPositionSelected(TAB_POSITION_AUTO)
    presenter.onTabPositionSelected(TAB_POSITION_TRANSIT)
    presenter.onTabPositionSelected(TAB_POSITION_BIKE)
    presenter.onTabPositionSelected(TAB_POSITION_WALK)
    verify(router, times(5)).fetch()
  }

  @Test fun onTabPositionSelected_shouldNotGetRouteWhenNoLocations() {
    presenter.onTabPositionSelected(TAB_POSITION_AUTO)
    presenter.onTabPositionSelected(TAB_POSITION_TRANSIT)
    presenter.onTabPositionSelected(TAB_POSITION_BIKE)
    presenter.onTabPositionSelected(TAB_POSITION_WALK)
    verify(router, times(0)).fetch()
  }

  @Test fun addPin_shouldAddMarkerToMap() {
    Mockito.`when`(controller.getMapzenMap()?.screenPositionToLngLat(
        any(PointF::class.java))).thenReturn(mock(LngLat::class.java))
    presenter.addPin(0f, 1f)
    verify(controller.getMapzenMap())?.addMarker(any(Marker::class.java))
  }

  @Test fun addPin_shouldAddLocationToRouter() {
    Mockito.`when`(controller.getMapzenMap()?.screenPositionToLngLat(
        any(PointF::class.java))).thenReturn(LngLat(1.0, 0.0))
    presenter.addPin(0f, 1f)
    verify(router).setLocation(doubleArrayOf(0.0, 1.0))
  }

  @Test fun addPin_shouldGetRouteIfAtLeastTwoPoints() {
    presenter.addPin(0f, 1f)
    presenter.addPin(0f, 1f)
    presenter.addPin(0f, 1f)
    verify(router, times(2)).fetch()
  }

  @Test fun displayRoute_shouldClearPreviousLine() {
    `when`(controller.getMapzenMap()?.addPolyline(any(Polyline::class.java))).thenReturn(mock(
        MapData::class.java))
    presenter.displayRoute(mock(Route::class.java))
    verify(controller.getMapzenMap())?.removePolyline()

  }

  @Test fun displayRoute_shouldAddPolyline() {
    presenter.displayRoute(mock(Route::class.java))
    verify(controller.getMapzenMap())?.addPolyline(any(Polyline::class.java))
  }

  @Test fun onDestroy_shouldDisableLocation() {
    presenter.onDestroy()
    verify(controller.getMapzenMap())?.isMyLocationEnabled = false
  }

}
