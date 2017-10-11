package mapzen.com.sdksampleapp.models

import mapzen.com.sdksampleapp.models.MapSampleList.MAP_SAMPLES
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class MapSampleListTest {

  @Test fun mapSamplesContainsCorrectSamples() {
    assertThat(MAP_SAMPLES).isEmpty()
  }
}
