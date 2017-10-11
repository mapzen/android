package mapzen.com.sdksampleapp.presenters

import mapzen.com.sdksampleapp.R
import mapzen.com.sdksampleapp.controllers.MainController
import mapzen.com.sdksampleapp.models.Sample
import mapzen.com.sdksampleapp.models.SampleMap

/**
 * Base class which implements [MainPresenter] interface
 */
class MainPresenterImpl : MainPresenter {

  override var controller: MainController? = null
  override var sample: Sample? = null

  // in
  override fun onCreate() {
    controller?.setupNavigationItemSelectedListener()
    onNavBarItemSelected(R.id.navigation_map)
  }

  override fun onNavBarItemSelected(navItemId: Int) {
    val samples = SampleMap.NAV_ID_TO_SECTIONS.get(navItemId)
    controller?.setScrollViewSamples(samples)
    sample?.cleanup(null, null, null) //TODO
    sample = null
    if (samples?.isEmpty() != true) {
      sample = samples?.get(0)
    }
    sample?.setup(null, null, null) //TODO
  }

  override fun onSampleSelected(selected: Sample) {
    sample?.cleanup(null, null, null)
    sample = selected
    sample?.setup(null, null, null)
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
