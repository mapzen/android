package mapzen.com.sdksampleapp.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.ListPreference
import android.preference.PreferenceFragment
import android.preference.PreferenceManager
import mapzen.com.sdksampleapp.R
import mapzen.com.sdksampleapp.activities.BaseActivity
import mapzen.com.sdksampleapp.activities.SettingsActivity
import mapzen.com.sdksampleapp.controllers.SettingsController
import mapzen.com.sdksampleapp.presenters.SettingsPresenter
import javax.inject.Inject


/**
 * Settings fragment for display within [SettingsActivity].
 */
class SettingsFragment : PreferenceFragment(), SharedPreferences.OnSharedPreferenceChangeListener,
    SettingsController {

  private lateinit var sharedPref: SharedPreferences

  @Inject lateinit var presenter: SettingsPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    addPreferencesFromResource(R.xml.preferences)
    sharedPref = PreferenceManager.getDefaultSharedPreferences(
        this.activity.applicationContext)
    val baseActivity = activity as BaseActivity
    baseActivity.mainApplication.appComponent.inject(this)
    presenter.controller = this
  }

  override fun onResume() {
    super.onResume()
    sharedPref.registerOnSharedPreferenceChangeListener(this)
  }

  override fun onPause() {
    super.onPause()
    sharedPref.unregisterOnSharedPreferenceChangeListener(this)
  }

  override fun onSharedPreferenceChanged(sharedPrefs: SharedPreferences?, pref: String?) {
    presenter.onSharedPreferenceChanged(pref)
  }

  override fun configureListPreference(prefId: String, entriesResId: Int, entryValuesResId: Int,
      value: String?) {
    val lodPref = findPreference(prefId) as ListPreference
    lodPref.entries = resources.getStringArray(entriesResId)
    lodPref.entryValues = resources.getStringArray(entryValuesResId)
    lodPref.value = value
  }

}