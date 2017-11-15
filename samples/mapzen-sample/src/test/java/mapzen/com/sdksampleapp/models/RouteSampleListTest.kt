package mapzen.com.sdksampleapp.models

import mapzen.com.sdksampleapp.fragments.RoutePinsFragment
import mapzen.com.sdksampleapp.fragments.RoutingFragment
import mapzen.com.sdksampleapp.models.RouteSampleList.Companion.ROUTE_SAMPLES
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class RouteSampleListTest {

  @Test
  fun routeSamplesContainsCorrectSamples() {
    assertThat(ROUTE_SAMPLES.size).isEqualTo(2)
    assertThat(ROUTE_SAMPLES[0].title).isEqualTo("tap map to add pins")
    assertThat(ROUTE_SAMPLES[0].fragmentClass).isEqualTo(RoutePinsFragment::class)
    assertThat(ROUTE_SAMPLES[1].title).isEqualTo("multimodal routing")
    assertThat(ROUTE_SAMPLES[1].fragmentClass).isEqualTo(RoutingFragment::class)
  }
}
