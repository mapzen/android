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
  var viewSample: Sample? = null
  var fragmentSample: Sample? = null
  var fragmentCleanedUp = false
  var fragmentRemoved = false

  override fun setupNavigationItemSelectedListener() {
    navigationItemSelectedListenerSetup = true
  }

  override fun cleanupNavigationItemSelectedListener() {
    navigationItemSelectedListenerCleanedUp = true
  }

  override fun clearScrollViewSamples() {
    scrollViewSamplesCleared = true
  }

  override fun cleanupSampleFragment() {
    fragmentCleanedUp = true
  }

  override fun removeSampleFragment() {
    fragmentRemoved = true
  }

  override fun selectSampleView(sample: Sample) {
    viewSample = sample
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
