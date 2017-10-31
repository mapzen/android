package mapzen.com.sdksampleapp.fragments

import android.graphics.PointF
import com.mapzen.android.graphics.model.Marker
import com.mapzen.tangram.TouchInput
import mapzen.com.sdksampleapp.R

/**
 * Fragment to demonstrate dropping pins on the map and routing between them.
 */
class RoutePinsFragment : BaseFragment() {

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

  private fun addPin(x: Float, y: Float) {
    val lngLat = map?.screenPositionToLngLat(PointF(x, y))
    val marker = lngLat?.let { Marker(it.longitude, it.latitude) }
    map?.addMarker(marker)
  }

}