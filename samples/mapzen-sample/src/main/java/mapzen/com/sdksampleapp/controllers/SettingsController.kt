package mapzen.com.sdksampleapp.controllers

/**
 * Interface implemented by [SettingsFragment].
 */
interface SettingsController {
  fun configureListPreference(prefId: String, entriesResId: Int, entryValuesResId: Int,
      value: String?)
}
