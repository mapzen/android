package mapzen.com.sdksampleapp.presenters

import com.mapzen.valhalla.Route
import mapzen.com.sdksampleapp.controllers.RoutingController

/**
 * Presenter for [RoutingFragment].
 */
interface RoutingPresenter {
  var controller: RoutingController?
  var selectedMode: Int?

  fun onCreate()
  fun setupMap()
  fun onTabPositionSelected(position: Int)
  fun addPin(x: Float, y: Float)
  fun displayRoute(route: Route)
  fun onDestroyView()
}
