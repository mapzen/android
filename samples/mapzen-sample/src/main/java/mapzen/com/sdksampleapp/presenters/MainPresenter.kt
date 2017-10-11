package mapzen.com.sdksampleapp.presenters

import mapzen.com.sdksampleapp.controllers.MainController
import mapzen.com.sdksampleapp.models.Sample

/**
 * Interface for main presenter logic.
 */
interface MainPresenter {

  var controller: MainController?
  var sample: Sample?

  // IN

  // Call when the activity is created
  fun onCreate()
  // Call when an item in the bottom navigation bar has been selected
  fun onNavBarItemSelected(navItemId: Int)
  // Call when a sample has been selected from the scroll view
  fun onSampleSelected(sample: Sample)
  // Call when the activity is destroyed
  fun onDestroy()


  // OUT

  // Call to get the sample view's title
  fun getTitleText(sample: Sample) : String
  // Call to get sample view's tag
  fun getTag(sample: Sample) : Sample
}
