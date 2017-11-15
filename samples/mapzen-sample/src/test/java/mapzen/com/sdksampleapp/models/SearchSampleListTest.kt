package mapzen.com.sdksampleapp.models

import mapzen.com.sdksampleapp.models.SearchSampleList.Companion.SEARCH_SAMPLES
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class SearchSampleListTest {

  @Test
  fun searchSamplesContainsCorrectSamples() {
    assertThat(SEARCH_SAMPLES).isEmpty()
  }
}
