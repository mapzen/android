package mapzen.com.sdksampleapp.models

import mapzen.com.sdksampleapp.R
import mapzen.com.sdksampleapp.models.MapSampleList.Companion.MAP_SAMPLES
import mapzen.com.sdksampleapp.models.MoreSampleList.Companion.MORE_SAMPLES
import mapzen.com.sdksampleapp.models.RouteSampleList.Companion.ROUTE_SAMPLES
import mapzen.com.sdksampleapp.models.SampleMap.Companion.NAV_ID_TO_SECTIONS
import mapzen.com.sdksampleapp.models.SearchSampleList.Companion.SEARCH_SAMPLES
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class SampleMapTest {

  @Test fun navIdMapHasCorrectNumEntries() {
    assertThat(NAV_ID_TO_SECTIONS.size()).isEqualTo(4)
  }

  @Test fun mapSectionHasCorrectSamples() {
    assertThat(NAV_ID_TO_SECTIONS.get(R.id.navigation_map)).isEqualTo(MAP_SAMPLES)
  }

  @Test fun routeSectionHasCorrectSamples() {
    assertThat(NAV_ID_TO_SECTIONS.get(R.id.navigation_route)).isEqualTo(ROUTE_SAMPLES)
  }

  @Test fun searchSectionHasCorrectSamples() {
    assertThat(NAV_ID_TO_SECTIONS.get(R.id.navigation_search)).isEqualTo(SEARCH_SAMPLES)
  }

  @Test fun moreSectionHasCorrectSamples() {
    assertThat(NAV_ID_TO_SECTIONS.get(R.id.navigation_more)).isEqualTo(MORE_SAMPLES)
  }
}
