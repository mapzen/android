package mapzen.com.sdksampleapp.models

import com.mapzen.android.graphics.MapzenMap
import com.mapzen.android.routing.MapzenRouter
import com.mapzen.android.search.MapzenSearch
import mapzen.com.sdksampleapp.fragments.BaseFragment
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class SampleTest {

  var setup = false
  var cleanedup = false

  val sample = object: Sample("sample", BaseFragment::class) {
    override fun setup(map: MapzenMap?, router: MapzenRouter?, search: MapzenSearch?) {
      setup = true
    }

    override fun cleanup(map: MapzenMap?, router: MapzenRouter?, search: MapzenSearch?) {
      cleanedup = true
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
    assertThat(cleanedup).isTrue()
  }
}
