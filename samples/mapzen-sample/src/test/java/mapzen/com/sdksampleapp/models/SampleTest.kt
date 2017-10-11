package mapzen.com.sdksampleapp.models

import com.mapzen.android.graphics.MapzenMap
import com.mapzen.android.routing.MapzenRouter
import com.mapzen.android.search.MapzenSearch
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class SampleTest {

  val setup = false
  val takedown = false

  val sample = object: Sample("sample") {
    override fun setup(map: MapzenMap?, router: MapzenRouter?, search: MapzenSearch?) {

    }

    override fun cleanup(map: MapzenMap?, router: MapzenRouter?, search: MapzenSearch?) {

    }
  }

  @Test fun titleIsCorrect() {
    assertThat(sample.title).isEqualTo("sample")
  }

  @Test fun setupInvokesMethod() {
    sample.setup(null, null, null)
    assertThat(setup).isTrue()
  }

  @Test fun takedownInvokesMethod() {
    sample.cleanup(null, null, null)
    assertThat(takedown).isTrue()
  }
}
