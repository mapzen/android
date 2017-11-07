package mapzen.com.sdksampleapp.controllers

import com.mapzen.android.graphics.MapzenMap

/**
 * Implemented by [RoutingFragment].
 */
interface RoutingController {
  fun getMapzenMap(): MapzenMap?
}
