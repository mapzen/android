package mapzen.com.sdksampleapp.controllers

import mapzen.com.sdksampleapp.models.Sample

/**
 * Interface for abstracting {@link MainActivity} behavior.
 */
interface MainController {
  fun selectSampleView(sample: Sample)
  fun setupNavigationItemSelectedListener()
  fun cleanupNavigationItemSelectedListener()
  fun clearScrollViewSamples()
  fun setScrollViewSamples(samples: Array<Sample>?)
  fun cleanupSampleFragment()
  fun setupSampleFragment(sample: Sample)
  fun cleanupScrollItemClickListeners()
  fun openSettings()
}
