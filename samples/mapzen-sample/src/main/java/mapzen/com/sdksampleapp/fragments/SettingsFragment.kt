package mapzen.com.sdksampleapp.fragments

import android.os.Bundle
import android.preference.PreferenceFragment
import mapzen.com.sdksampleapp.R

/**
 * Settings fragment for display within [SettingsActivity].
 */
class SettingsFragment : PreferenceFragment() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    addPreferencesFromResource(R.xml.preferences)
  }
}