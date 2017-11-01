package mapzen.com.sdksampleapp.models

import mapzen.com.sdksampleapp.fragments.RoutePinsFragment

/**
 * List of [Sample]s for the route section to display.
 */
class RouteSampleList {
  companion object {
    private val pinsSample = Sample("tap map to add pins", RoutePinsFragment::class)
    @JvmStatic val ROUTE_SAMPLES = arrayOf(pinsSample)
  }
}
