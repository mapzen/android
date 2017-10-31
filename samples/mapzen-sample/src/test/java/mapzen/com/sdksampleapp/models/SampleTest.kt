package mapzen.com.sdksampleapp.models

import com.mapzen.android.graphics.MapzenMap
import com.mapzen.android.routing.MapzenRouter
import com.mapzen.android.search.MapzenSearch
import mapzen.com.sdksampleapp.fragments.BaseFragment
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class SampleTest {

  val sample = Sample("sample", BaseFragment::class)

  @Test fun titleIsCorrect() {
    assertThat(sample.title).isEqualTo("sample")
  }

  @Test fun framentClassIsCorrect() {
    assertThat(sample.fragmentClass).isEqualTo(BaseFragment::class)
  }
}
