package mapzen.com.sdksampleapp.models

import android.support.v4.util.SparseArrayCompat

import mapzen.com.sdksampleapp.R
import mapzen.com.sdksampleapp.models.MapSampleList.Companion.MAP_SAMPLES

/**
 * Maps navigation ids to their list of [Sample]s.
 */
class SampleMap : SampleVendor {

  companion object {
    @JvmStatic val NAV_ID_TO_SECTIONS: SparseArrayCompat<Array<Sample>?> by lazy {
      val sparseArray = SparseArrayCompat<Array<Sample>?>(4)
      sparseArray.append(R.id.navigation_map, MAP_SAMPLES)
      sparseArray.append(R.id.navigation_route, null)
      sparseArray.append(R.id.navigation_search, null)
      sparseArray.append(R.id.navigation_more, null)
      sparseArray
    }
  }

  override fun samplesForNavId(navId: Int): Array<Sample>? {
    return NAV_ID_TO_SECTIONS.get(navId)
  }
}
