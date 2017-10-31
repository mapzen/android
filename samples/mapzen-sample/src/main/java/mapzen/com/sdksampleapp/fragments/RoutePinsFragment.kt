package mapzen.com.sdksampleapp.fragments

import android.graphics.PointF
import com.mapzen.android.graphics.model.Marker
import com.mapzen.android.graphics.model.Polyline
import com.mapzen.tangram.LngLat
import com.mapzen.tangram.TouchInput
import com.mapzen.valhalla.Route
import com.mapzen.valhalla.RouteCallback
import mapzen.com.sdksampleapp.R
import java.util.ArrayList

/**
 * Fragment to demonstrate dropping pins on the map and routing between them.
 */
class RoutePinsFragment : BaseFragment() {

  var count = 0
  var locations: ArrayList<DoubleArray> = ArrayList()

  override fun getLayoutId(): Int {
    return R.layout.fragment_map
  }

  override fun onMapSetup() {
    map?.tapResponder = object: TouchInput.TapResponder {
      override fun onSingleTapUp(x: Float, y: Float): Boolean {
        addPin(x, y)
        return false
      }

      override fun onSingleTapConfirmed(x: Float, y: Float): Boolean {
        return false
      }

    }
  }

  override fun onResume() {
    super.onResume()
    router.setDriving().setCallback(object: RouteCallback {
      override fun failure(statusCode: Int) {

      }

      override fun success(route: Route) {
        displayRoute(route)
      }

    })
  }

  private fun addPin(x: Float, y: Float) {
    count++
    val lngLat = map?.screenPositionToLngLat(PointF(x, y))
    val marker = lngLat?.let { Marker(it.longitude, it.latitude) }
    map?.addMarker(marker)
    lngLat?.let { addToRouter(doubleArrayOf(it.latitude, it.longitude)) }
    if (count >= 2) {
      getRoute()
    }
  }

  private fun addToRouter(point: DoubleArray) {
    router.setLocation(point)
  }

  private fun getRoute() {
    router.fetch()
  }

  private fun displayRoute(route: Route) {
    val coordinates = ArrayList<LngLat>()
    for (location in route.getGeometry()) {
      coordinates.add(LngLat(location.longitude, location.latitude))
    }
    val polyline = Polyline(coordinates)
    map?.addPolyline(polyline)
  }
}