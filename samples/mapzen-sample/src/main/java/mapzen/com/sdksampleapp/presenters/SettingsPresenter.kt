package mapzen.com.sdksampleapp.presenters

import mapzen.com.sdksampleapp.controllers.SettingsController

/**
 * Interface for presenter used by [SettingsFragment].
 */
interface SettingsPresenter {
  var controller: SettingsController

  fun onSharedPreferenceChanged(pref: String?)
}
