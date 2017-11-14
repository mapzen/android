package mapzen.com.sdksampleapp.presenters

import mapzen.com.sdksampleapp.R
import mapzen.com.sdksampleapp.controllers.MainController
import mapzen.com.sdksampleapp.models.Sample
import mapzen.com.sdksampleapp.models.SampleVendor

/**
 * Base class which implements [MainPresenter] interface
 */
class MainPresenterImpl(val sampleVendor: SampleVendor) : MainPresenter {

  override var controller: MainController? = null
  override var navItemId: Int? = null
  override var sample: Sample? = null

  // in
  override fun onCreate() {
    controller?.setupNavigationItemSelectedListener()
    if (navItemId == null) {
      onNavBarItemSelected(R.id.navigation_map)
    } else {
      navItemId?.let {
        val samples = sampleVendor.samplesForNavId(it)
        controller?.setScrollViewSamples(samples)
        updateControllerViews()
      }
    }
  }

  override fun onNavBarItemSelected(navItemId: Int) {
    this.navItemId = navItemId
    val samples = sampleVendor.samplesForNavId(navItemId)
    controller?.clearScrollViewSamples()
    controller?.setScrollViewSamples(samples)
    sample = null
    if (samples?.isNotEmpty() == true) {
      val next = samples[0]
      sample = next
      updateControllerViews(true)
    } else {
      controller?.cleanupSampleFragment()
      controller?.removeSampleFragment()
    }
  }

  override fun onSampleSelected(selected: Sample) {
    sample = selected
    updateControllerViews(true)
  }

  override fun onOptionsItemSelected(itemId: Int?) {
    when (itemId) {
      R.id.action_settings -> controller?.openSettings()
    }
  }

  override fun onDestroy() {
    controller?.cleanupNavigationItemSelectedListener()
    controller?.cleanupScrollItemClickListeners()
  }

  // out
  override fun getTitleText(sample: Sample): String {
    return sample.title
  }

  override fun getTag(sample: Sample): Sample {
    return sample
  }

  private fun updateControllerViews() {
    updateControllerViews(false)
  }

  private fun updateControllerViews(uiSelected: Boolean) {
    sample?.let {
      controller?.selectSampleView(it)
      if (uiSelected) {
        controller?.cleanupSampleFragment()
      }
      controller?.removeSampleFragment()
      controller?.setupSampleFragment(it)
    }
  }
}
