package mapzen.com.sdksampleapp.presenters

import mapzen.com.sdksampleapp.R
import mapzen.com.sdksampleapp.TestMainController
import mapzen.com.sdksampleapp.TestSample
import mapzen.com.sdksampleapp.models.MapSampleList.MAP_SAMPLES
import mapzen.com.sdksampleapp.models.MapSampleList.MORE_SAMPLES
import mapzen.com.sdksampleapp.models.Sample
import mapzen.com.sdksampleapp.models.SampleMap
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.powermock.api.mockito.PowerMockito.`when`

class MainPresenterTest {

  val presenter : MainPresenter = MainPresenterImpl()
  val controller = TestMainController()

  @Before fun setup() {
    presenter.controller = controller
  }

  @Test fun onCreate_shouldSetupNavigationItemSelectedListener() {
    presenter.onCreate()
    assertThat(controller.navigationItemSelectedListenerSetup).isTrue()
  }

  @Test fun onCreate_shouldSelectMapTab() {
    presenter.onCreate()
    assertThat(controller.scrolViewSamples).isEqualTo(MAP_SAMPLES)
  }

  @Test fun onNavBarItemSelected_shouldClearPreviousScrollSamples() {
    presenter.onNavBarItemSelected(R.id.navigation_map)
    assertThat(controller.scrollViewSamplesCleared).isTrue()
  }

  @Test fun onNavBarItemSelected_shouldSetScrollSamples() {
    presenter.onNavBarItemSelected(R.id.navigation_map)
    assertThat(controller.scrolViewSamples).isEqualTo(MAP_SAMPLES)
    presenter.onNavBarItemSelected(R.id.navigation_more)
    assertThat(controller.scrolViewSamples).isEqualTo(MORE_SAMPLES)
  }

  @Test fun onNavBarItemSelected_shouldTakedownCurrentSample() {
    val sample = TestSample("test")
    presenter.sample = sample
    presenter.onNavBarItemSelected(R.id.navigation_map)
    assertThat(sample.cleanedup).isTrue()
  }

  @Test fun onNavBarItemSelected_shouldClearCurrentSample() {
    val sample = TestSample("test")
    presenter.sample = sample
    presenter.onNavBarItemSelected(R.id.navigation_map)
    assertThat(presenter.sample).isNotEqualTo(sample)
  }

  @Test fun onNavBarItemSelected_shouldSetSelectedSample() {
    val sample = TestSample("test")
    `when`(MAP_SAMPLES).thenReturn(arrayOf(sample))
    presenter.onNavBarItemSelected(R.id.navigation_map)
    assertThat(presenter.sample).isEqualTo(sample)
  }

  @Test fun onNavBarItemSelected_shouldSetupSelectedSample() {
    val sample = TestSample("test")
    `when`(MAP_SAMPLES).thenReturn(arrayOf(sample))
    presenter.onNavBarItemSelected(R.id.navigation_map)
    assertThat(sample.setup).isTrue()
  }

  @Test fun onSampleSelected_shouldCleanupCurrentSample() {
    val sample = TestSample("test")
    presenter.sample = sample
    presenter.onSampleSelected(TestSample("another"))
    assertThat(sample.cleanedup).isTrue()
  }

  @Test fun onSampleSelected_shouldSetCurrentSample() {
    val sample = TestSample("test")
    presenter.sample = sample
    val selected = TestSample("another")
    presenter.onSampleSelected(selected)
    assertThat(presenter.sample).isEqualTo(selected)
  }

  @Test fun onSampleSelected_shouldSetupSelectedSample() {
    val sample = TestSample("test")
    presenter.sample = sample
    val selected = TestSample("another")
    presenter.onSampleSelected(selected)
    assertThat(selected.setup).isTrue()
  }

  @Test fun onDestroy_shouldCleanupNavigationItemSelectedListener() {
    presenter.onDestroy()
    assertThat(controller.navigationItemSelectedListenerCleanedUp).isTrue()
  }

  @Test fun onDestroy_shouldCleanupScrollItemClickListeners() {
    presenter.onDestroy()
    assertThat(controller.scrollItemClickListenersCleanedUp).isTrue()
  }

  @Test fun getTitleText_shouldReturnTitle() {
    val sample = TestSample("sample")
    val titleText = presenter.getTitleText(sample)
    assertThat(titleText).isEqualTo("sample")
  }

  @Test fun getTag_shouldReturnSample() {
    val sample = TestSample("sample")
    val tag = presenter.getTag(sample)
    assertThat(tag).isEqualTo(sample)
  }

}
