package mapzen.com.sdksampleapp.models

import mapzen.com.sdksampleapp.R
import mapzen.com.sdksampleapp.models.MapSampleList.Companion.MAP_SAMPLES
import mapzen.com.sdksampleapp.models.SampleMap.Companion.NAV_ID_TO_SECTIONS
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
    assertThat(NAV_ID_TO_SECTIONS.get(R.id.navigation_route)).isNull()
  }

  @Test fun searchSectionHasCorrectSamples() {
    assertThat(NAV_ID_TO_SECTIONS.get(R.id.navigation_search)).isNull()
  }

  @Test fun moreSectionHasCorrectSamples() {
    assertThat(NAV_ID_TO_SECTIONS.get(R.id.navigation_more)).isNull()
  }
}
