package mapzen.com.sdksampleapp

import mapzen.com.sdksampleapp.controllers.SettingsController

class TestSettingsController : SettingsController {

  var prefIds = mutableListOf<String>()
  var entriesResIds = mutableListOf<Int>()
  var entryValuesResIds = mutableListOf<Int>()
  var values = mutableListOf<String>()

  override fun configureListPreference(prefId: String, entriesResId: Int, entryValuesResId: Int,
      value: String?) {
    prefIds.add(prefId)
    entriesResIds.add(entriesResId)
    entryValuesResIds.add(entryValuesResId)
    value?.let { values.add(it) }
  }

}