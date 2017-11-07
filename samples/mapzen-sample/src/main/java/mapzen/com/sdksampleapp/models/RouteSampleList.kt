package mapzen.com.sdksampleapp.models

import mapzen.com.sdksampleapp.fragments.RoutePinsFragment
import mapzen.com.sdksampleapp.fragments.RoutingFragment

/**
 * List of [Sample]s for the route section to display.
 */
class RouteSampleList {
  companion object {
    private val pinsSample = Sample("tap map to add pins", RoutePinsFragment::class)
    private val routingSample = Sample("multimodal routing", RoutingFragment::class)
    @JvmStatic val ROUTE_SAMPLES = arrayOf(pinsSample, routingSample)
  }
}
