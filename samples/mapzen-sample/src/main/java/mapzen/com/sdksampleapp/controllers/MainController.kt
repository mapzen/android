package mapzen.com.sdksampleapp.controllers

import mapzen.com.sdksampleapp.models.Sample

/**
 * Interface for abstracting {@link MainActivity} behavior.
 */
interface MainController {
  fun setupNavigationItemSelectedListener()
  fun cleanupNavigationItemSelectedListener()
  fun clearScrollViewSamples()
  fun setScrollViewSamples(samples: Array<Sample>?)
  fun setupSampleFragment(sample: Sample)
  fun cleanupScrollItemClickListeners()
  fun openSettings()
}
