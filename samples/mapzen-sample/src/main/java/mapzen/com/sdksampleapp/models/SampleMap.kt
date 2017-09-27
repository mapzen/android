package mapzen.com.sdksampleapp.models

import android.util.SparseArray

import mapzen.com.sdksampleapp.R
import mapzen.com.sdksampleapp.models.MapSampleList.MAP_SAMPLES

/**
 * Maps navigation ids to their list of [Sample]s.
 */
object SampleMap {

  val NAV_ID_TO_SECTIONS: SparseArray<Array<Sample>?> = SparseArray(4)
  init {
    NAV_ID_TO_SECTIONS.append(R.id.navigation_map, MAP_SAMPLES)
    NAV_ID_TO_SECTIONS.append(R.id.navigation_route, null)
    NAV_ID_TO_SECTIONS.append(R.id.navigation_search, null)
    NAV_ID_TO_SECTIONS.append(R.id.navigation_more, null)
  }

}
