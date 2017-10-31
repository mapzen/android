package mapzen.com.sdksampleapp.models

import android.support.v4.util.SparseArrayCompat

import mapzen.com.sdksampleapp.R
import mapzen.com.sdksampleapp.models.MapSampleList.Companion.MAP_SAMPLES
import mapzen.com.sdksampleapp.models.MoreSampleList.Companion.MORE_SAMPLES
import mapzen.com.sdksampleapp.models.RouteSampleList.Companion.ROUTE_SAMPLES
import mapzen.com.sdksampleapp.models.SearchSampleList.Companion.SEARCH_SAMPLES

/**
 * Maps navigation ids to their list of [Sample]s.
 */
class SampleMap : SampleVendor {

  companion object {
    @JvmStatic val NAV_ID_TO_SECTIONS: SparseArrayCompat<Array<Sample>?> by lazy {
      val sparseArray = SparseArrayCompat<Array<Sample>?>(4)
      sparseArray.append(R.id.navigation_map, MAP_SAMPLES)
      sparseArray.append(R.id.navigation_route, ROUTE_SAMPLES)
      sparseArray.append(R.id.navigation_search, SEARCH_SAMPLES)
      sparseArray.append(R.id.navigation_more, MORE_SAMPLES)
      sparseArray
    }
  }

  override fun samplesForNavId(navId: Int): Array<Sample>? {
    return NAV_ID_TO_SECTIONS.get(navId)
  }
}
