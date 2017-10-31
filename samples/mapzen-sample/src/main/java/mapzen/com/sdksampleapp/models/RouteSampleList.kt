package mapzen.com.sdksampleapp.models

import com.mapzen.android.graphics.MapzenMap
import com.mapzen.android.routing.MapzenRouter
import com.mapzen.android.search.MapzenSearch
import mapzen.com.sdksampleapp.fragments.RoutePinsFragment

/**
 * List of [Sample]s for the route section to display.
 */
class RouteSampleList {
  companion object {
    val pinsSample = object: Sample("add pins", RoutePinsFragment::class) {
      override fun cleanup(map: MapzenMap?, router: MapzenRouter?, search: MapzenSearch?) {
      }

      override fun setup(map: MapzenMap?, router: MapzenRouter?, search: MapzenSearch?) {
      }

    }

    @JvmStatic val ROUTE_SAMPLES = arrayOf(pinsSample)
  }
}
