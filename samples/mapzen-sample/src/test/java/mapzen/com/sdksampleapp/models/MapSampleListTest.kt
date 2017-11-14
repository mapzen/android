package mapzen.com.sdksampleapp.models

import mapzen.com.sdksampleapp.fragments.BasicMapFragment
import mapzen.com.sdksampleapp.models.MapSampleList.Companion.MAP_SAMPLES
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class MapSampleListTest {

  @Test fun mapSamplesContainsCorrectSamples() {
    assertThat(MAP_SAMPLES.size).isEqualTo(1)
    assertThat(MAP_SAMPLES[0].title).isEqualTo("basic map")
    assertThat(MAP_SAMPLES[0].fragmentClass).isEqualTo(BasicMapFragment::class)
  }
}
