package mapzen.com.sdksampleapp.presenters

import android.graphics.PointF
import com.mapzen.android.graphics.model.Marker
import com.mapzen.android.graphics.model.Polyline
import com.mapzen.android.routing.MapzenRouter
import com.mapzen.tangram.LngLat
import com.mapzen.tangram.TouchInput
import com.mapzen.valhalla.Route
import com.mapzen.valhalla.RouteCallback
import mapzen.com.sdksampleapp.controllers.RoutingController
import mapzen.com.sdksampleapp.fragments.RoutingFragment

/**
 * Presenter for [RoutingFragment].
 */
class RoutingPresenterImpl(val router: MapzenRouter) : RoutingPresenter, RouteCallback {
  override var controller: RoutingController? = null
  override var selectedMode: Int? = null

  private var count = 0

  companion object {
    @JvmStatic val TAB_POSITION_AUTO = 0
    @JvmStatic val TAB_POSITION_TRANSIT = 1
    @JvmStatic val TAB_POSITION_BIKE = 2
    @JvmStatic val TAB_POSITION_WALK = 3
  }

  override fun onCreate() {
    // TODO: move to resume/pause when have ability to set callback to null & cancel requests
    router.setCallback(this)
  }

  override fun failure(statusCode: Int) { }

  override fun success(route: Route) {
    displayRoute(route)
  }

  override fun setupMap() {
    val map = controller?.getMapzenMap()
    map?.setPersistMapData(true)
    map?.isMyLocationEnabled = true
    map?.tapResponder = object : TouchInput.TapResponder {
      override fun onSingleTapUp(x: Float, y: Float): Boolean {
        addPin(x, y)
        return false
      }

      override fun onSingleTapConfirmed(x: Float, y: Float): Boolean {
        return false
      }
    }
  }

  override fun onTabPositionSelected(position: Int) {
    selectedMode = position
    when (position) {
      TAB_POSITION_AUTO -> router.setDriving()
      TAB_POSITION_TRANSIT -> router.setMultimodal()
      TAB_POSITION_BIKE -> router.setBiking()
      TAB_POSITION_WALK -> router.setWalking()
    }
    getRoute()
  }

  override fun addPin(x: Float, y: Float) {
    count++
    val lngLat = controller?.getMapzenMap()?.screenPositionToLngLat(PointF(x, y))
    val marker = lngLat?.let { Marker(it.longitude, it.latitude) }
    controller?.getMapzenMap()?.addMarker(marker)
    lngLat?.let { addToRouter(doubleArrayOf(it.latitude, it.longitude)) }
    getRoute()
  }

  private fun addToRouter(point: DoubleArray) {
    router.setLocation(point)
  }

  private fun getRoute() {
    if (count < 2) {
      return
    }
    router.fetch()
  }

  override fun displayRoute(route: Route) {
    val coordinates = route.getGeometry().map { LngLat(it.longitude, it.latitude) }
    val polyline = Polyline(coordinates)
    controller?.getMapzenMap()?.removePolyline()
    controller?.getMapzenMap()?.addPolyline(polyline)
  }

  override fun onDestroyView() {
    controller?.getMapzenMap()?.isMyLocationEnabled = false
  }
}
