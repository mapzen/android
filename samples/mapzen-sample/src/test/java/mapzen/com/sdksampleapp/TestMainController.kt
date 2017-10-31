package mapzen.com.sdksampleapp

import mapzen.com.sdksampleapp.controllers.MainController
import mapzen.com.sdksampleapp.models.Sample

class TestMainController : MainController {

  var navigationItemSelectedListenerSetup = false
  var navigationItemSelectedListenerCleanedUp = false
  var scrollViewSamplesCleared = false
  var scrolViewSamples: Array<Sample>? = null
  var scrollItemClickListenersCleanedUp = false
  var openSettings = false
  var fragmentSample: Sample? = null

  override fun setupNavigationItemSelectedListener() {
    navigationItemSelectedListenerSetup = true
  }

  override fun cleanupNavigationItemSelectedListener() {
    navigationItemSelectedListenerCleanedUp = true
  }

  override fun clearScrollViewSamples() {
    scrollViewSamplesCleared = true
  }

  override fun setupSampleFragment(sample: Sample) {
    fragmentSample = sample
  }

  override fun setScrollViewSamples(samples: Array<Sample>?) {
    scrolViewSamples = samples
  }

  override fun cleanupScrollItemClickListeners() {
    scrollItemClickListenersCleanedUp = true
  }

  override fun openSettings() {
    openSettings = true
  }

}
