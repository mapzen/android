package mapzen.com.sdksampleapp.models

import com.mapzen.android.graphics.MapzenMap
import com.mapzen.android.routing.MapzenRouter
import com.mapzen.android.search.MapzenSearch

/**
 * Represents a feature of the SDK to demonstrate from within a navigation tab.
 */
abstract class Sample(val title: String) {

  /**
   * Called when the [Sample] has been selected. Use this method to setup state.
   * @param map
   * @param router
   * @param search
   */
  abstract fun setup(map: MapzenMap?, router: MapzenRouter?, search: MapzenSearch?)

  /**
   * Called when a new [Sample] has been selected. Use this method to cleanup state that
   * should not persist between samples.
   * @param map
   * @param router
   * @param search
   */
  abstract fun cleanup(map: MapzenMap?, router: MapzenRouter?, search: MapzenSearch?)
}
