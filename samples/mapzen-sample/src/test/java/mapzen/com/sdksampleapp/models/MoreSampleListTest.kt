package mapzen.com.sdksampleapp.models

import mapzen.com.sdksampleapp.models.MoreSampleList.Companion.MORE_SAMPLES
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class MoreSampleListTest {

  @Test
  fun moreSamplesContainsCorrectSamples() {
    assertThat(MORE_SAMPLES).isEmpty()
  }
}
