package mapzen.com.sdksampleapp.presenters

import mapzen.com.sdksampleapp.R
import mapzen.com.sdksampleapp.controllers.MainController
import mapzen.com.sdksampleapp.models.Sample
import mapzen.com.sdksampleapp.models.SampleMap
import mapzen.com.sdksampleapp.models.SampleVendor

/**
 * Base class which implements [MainPresenter] interface
 */
class MainPresenterImpl(val sampleVendor: SampleVendor) : MainPresenter {

  override var controller: MainController? = null
  override var sample: Sample? = null

  // in
  override fun onCreate() {
    controller?.setupNavigationItemSelectedListener()
    onNavBarItemSelected(R.id.navigation_map)
  }

  override fun onNavBarItemSelected(navItemId: Int) {
    val samples = sampleVendor.samplesForNavId(navItemId)
    controller?.clearScrollViewSamples()
    controller?.setScrollViewSamples(samples)
    sample = null
    if (samples?.isNotEmpty() == true) {
      val next = samples[0]
      sample = next
      next?.let { controller?.setupSampleFragment(it) }
    } else {
      controller?.cleanupSampleFragment()
    }
  }

  override fun onSampleSelected(selected: Sample) {
    sample = selected
    controller?.setupSampleFragment(selected)
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
  override fun getTitleText(sample: Sample) : String {
    return sample.title
  }

  override fun getTag(sample: Sample) : Sample {
    return sample
  }
}
