package mapzen.com.sdksampleapp.fragments

import mapzen.com.sdksampleapp.R

/**
 * Basic map sample which shows currently selected map theme
 */
class BasicMapFragment : BaseFragment() {

  override fun getLayoutId(): Int {
    return R.layout.fragment_map
  }

  override fun onMapSetup() {
    map?.isMyLocationEnabled = true
  }

  override fun cleanup() {
    // all cleanup happens in onDestroyView
  }

  override fun onDestroyView() {
    super.onDestroyView()
    map?.isMyLocationEnabled = false
  }
}
