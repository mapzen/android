package mapzen.com.sdksampleapp

import com.mapzen.android.graphics.MapzenMap
import com.mapzen.android.routing.MapzenRouter
import com.mapzen.android.search.MapzenSearch
import mapzen.com.sdksampleapp.fragments.BaseFragment
import mapzen.com.sdksampleapp.models.Sample

class TestSample(title: String) : Sample(title, BaseFragment::class) {

  var setup = false
  var cleanedup = false

  override fun setup(map: MapzenMap?, router: MapzenRouter?, search: MapzenSearch?) {
    setup = true
  }

  override fun cleanup(map: MapzenMap?, router: MapzenRouter?, search: MapzenSearch?) {
    cleanedup = true
  }

}
