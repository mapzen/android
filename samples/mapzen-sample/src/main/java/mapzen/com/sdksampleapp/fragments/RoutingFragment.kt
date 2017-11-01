package mapzen.com.sdksampleapp.fragments

import android.graphics.PointF
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.view.View
import com.mapzen.android.graphics.model.Marker
import com.mapzen.android.graphics.model.Polyline
import com.mapzen.tangram.LngLat
import com.mapzen.tangram.MapData
import com.mapzen.tangram.TouchInput
import com.mapzen.valhalla.Route
import com.mapzen.valhalla.RouteCallback
import kotterknife.bindView
import mapzen.com.sdksampleapp.R
import java.util.ArrayList

/**
 * Routing sample which demonstrates making route requests and displaying result on map.
 */
class RoutingFragment : BaseFragment() {

  companion object {
    @JvmStatic val TAB_POSITION_AUTO = 0
    @JvmStatic val TAB_POSITION_TRANSIT = 1
    @JvmStatic val TAB_POSITION_BIKE = 2
    @JvmStatic val TAB_POSITION_WALK = 3
  }
  var count = 0
  val tabLayout: TabLayout by bindView(R.id.tabLayout)
  var routeLine: MapData? = null

  override fun getLayoutId(): Int {
    return R.layout.fragment_routing
  }

  override fun onMapSetup() {
    map?.isMyLocationEnabled = true
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

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    // TODO: move to resume/pause when have ability to set callback to null & cancel requests
    router.setCallback(object: RouteCallback {
      override fun failure(statusCode: Int) {

      }

      override fun success(route: Route) {
        displayRoute(route)
      }

    })
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
      override fun onTabReselected(tab: TabLayout.Tab?) {
      }

      override fun onTabUnselected(tab: TabLayout.Tab?) {
      }

      override fun onTabSelected(tab: TabLayout.Tab?) {
        when (tab?.position) {
          TAB_POSITION_AUTO -> router.setDriving()
          TAB_POSITION_TRANSIT -> router.setMultimodal()
          TAB_POSITION_BIKE -> router.setBiking()
          TAB_POSITION_WALK -> router.setWalking()
        }
        getRoute()
      }

    })
  }

  override fun onDestroy() {
    super.onDestroy()
    map?.isMyLocationEnabled = false
    router.clearLocations()
  }

  private fun addPin(x: Float, y: Float) {
    count++
    val lngLat = map?.screenPositionToLngLat(PointF(x, y))
    val marker = lngLat?.let { Marker(it.longitude, it.latitude) }
    map?.addMarker(marker)
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

  private fun displayRoute(route: Route) {
    val coordinates = ArrayList<LngLat>()
    for (location in route.getGeometry()) {
      coordinates.add(LngLat(location.longitude, location.latitude))
    }
    val polyline = Polyline(coordinates)
    routeLine?.clear()
    routeLine = map?.addPolyline(polyline)
  }

}