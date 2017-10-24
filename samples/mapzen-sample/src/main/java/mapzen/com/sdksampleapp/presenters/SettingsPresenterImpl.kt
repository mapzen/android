package mapzen.com.sdksampleapp.presenters

import com.mapzen.android.graphics.model.RefillStyle
import com.mapzen.android.graphics.model.ThemeColor
import com.mapzen.android.graphics.model.ThemedMapStyle
import mapzen.com.sdksampleapp.R
import mapzen.com.sdksampleapp.controllers.SettingsController
import mapzen.com.sdksampleapp.models.Settings

/**
 * Implementation of [SettingsPresenter].
 */
class SettingsPresenterImpl(private val settings: Settings) : SettingsPresenter {

  override lateinit var controller: SettingsController

  override fun onSharedPreferenceChanged(pref: String?) {
    if (!pref.equals(Settings.PREF_MAP_STYLE)) {
      return
    }
    val mapStyle = settings.mapStyle
    configureLodPref(mapStyle)
    configureLabelLebelPref(mapStyle)
    configureColorPref(mapStyle)
  }

  private fun configureLodPref(mapStyle: ThemedMapStyle) {
    val lodResourceId = if (mapStyle?.defaultLod == ThemedMapStyle.NONE) {
      R.array.pref_empty
    } else {
      R.array.pref_lod_label_entries
    }
    controller.configureListPreference(Settings.PREF_LOD, lodResourceId, lodResourceId,
        mapStyle?.defaultLod.toString())
  }

  private fun configureLabelLebelPref(mapStyle: ThemedMapStyle) {
    val labelLevelResourceId = if (mapStyle?.defaultLabelLevel == ThemedMapStyle.NONE) {
      R.array.pref_empty
    } else {
      R.array.pref_lod_label_entries
    }
    controller.configureListPreference(Settings.PREF_LABEL_LEVEL, labelLevelResourceId,
        labelLevelResourceId, mapStyle?.defaultLabelLevel.toString())
  }

  private fun configureColorPref(mapStyle: ThemedMapStyle) {
    val colorResourceEntriesId: Int
    val colorResourceValuesId: Int
    if (mapStyle?.defaultColor == ThemeColor.NONE) {
      colorResourceEntriesId = R.array.pref_empty
      colorResourceValuesId = R.array.pref_empty
    } else {
      if (mapStyle is RefillStyle) {
        colorResourceEntriesId = R.array.pref_refill_theme_color_entries
        colorResourceValuesId = R.array.pref_refill_theme_color_values
      } else {
        colorResourceEntriesId = R.array.pref_cinnabar_theme_color_entries
        colorResourceValuesId = R.array.pref_cinnabar_theme_color_values
      }
    }
    controller.configureListPreference(Settings.PREF_COLOR, colorResourceEntriesId,
        colorResourceValuesId, mapStyle?.defaultColor.toString())
  }
}