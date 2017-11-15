package mapzen.com.sdksampleapp.models

import mapzen.com.sdksampleapp.fragments.BasicMapFragment

/**
 * List of [Sample]s for the map section to display.
 */
class MapSampleList {

  companion object {
    private val basicMap = Sample("basic map", BasicMapFragment::class)

    @JvmStatic val MAP_SAMPLES = arrayOf(basicMap)
  }
}
