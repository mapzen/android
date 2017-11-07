package mapzen.com.sdksampleapp.presenters

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import mapzen.com.sdksampleapp.R
import mapzen.com.sdksampleapp.TestSettingsController
import mapzen.com.sdksampleapp.models.Settings
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.eq
import org.mockito.Mockito.isNull
import org.mockito.Mockito.mock

class SettingsPresenterTest {

  val context = mock(Context::class.java)
  val sharedPrefs = mock(SharedPreferences::class.java)
  lateinit var settings: Settings
  lateinit var presenter: SettingsPresenter
  val controller = TestSettingsController()

  @Before fun setup() {
    val resources = mock(Resources::class.java)
    `when`(context.resources).thenReturn(resources)
    val mapStyles = arrayOf("Bubble Wrap", "Cinnabar", "Refill", "Tron", "Walkabout")
    `when`(resources.getStringArray(R.array.pref_map_style_entries)).thenReturn(mapStyles)
    val themeColors = arrayOf("black", "blue", "blue-gray", "brown-orange", "gray", "gray-gold",
        "high-contrast", "introverted", "introverted-blue", "pink", "pink-yellow", "purple-green",
        "sephia", "zinc")
    `when`(resources.getStringArray(R.array.pref_refill_theme_color_values)).thenReturn(themeColors)
    val cinnabarThemeColors = arrayOf("cinnabar")
    `when`(resources.getStringArray(R.array.pref_cinnabar_theme_color_values)).thenReturn(cinnabarThemeColors)
    settings = Settings(context, sharedPrefs)
    presenter = SettingsPresenterImpl(settings)
    presenter.controller = controller
  }

  @After fun cleanup() {
    controller.prefIds.clear()
    controller.entriesResIds.clear()
    controller.entryValuesResIds.clear()
    controller.values.clear()
  }

  @Test fun onSharedPreferenceChanged_shouldDoNothingIfNotMapStyle() {
    presenter.onSharedPreferenceChanged(Settings.PREF_LOD)
    presenter.onSharedPreferenceChanged(Settings.PREF_LABEL_LEVEL)
    presenter.onSharedPreferenceChanged(Settings.PREF_COLOR)
    assertThat(controller.prefIds).isEmpty()
  }

  @Test fun onSharedPreferenceChanged_refillStyle_shouldConfigureLodPref() {
    `when`(sharedPrefs.getString(eq(Settings.PREF_MAP_STYLE), isNull())).thenReturn("Refill")
    presenter.onSharedPreferenceChanged(Settings.PREF_MAP_STYLE)
    assertThat(controller.prefIds[0]).isEqualTo(Settings.PREF_LOD)
    assertThat(controller.entriesResIds[0]).isEqualTo(R.array.pref_lod_label_entries)
    assertThat(controller.entryValuesResIds[0]).isEqualTo(R.array.pref_lod_label_entries)
    assertThat(controller.values[0]).isEqualTo("10")
  }

  @Test fun onSharedPreferenceChanged_refillStyle_shouldConfigureLabelLevelPref() {
    `when`(sharedPrefs.getString(eq(Settings.PREF_MAP_STYLE), isNull())).thenReturn("Refill")
    presenter.onSharedPreferenceChanged(Settings.PREF_MAP_STYLE)
    assertThat(controller.prefIds[1]).isEqualTo(Settings.PREF_LABEL_LEVEL)
    assertThat(controller.entriesResIds[1]).isEqualTo(R.array.pref_lod_label_entries)
    assertThat(controller.entryValuesResIds[1]).isEqualTo(R.array.pref_lod_label_entries)
    assertThat(controller.values[1]).isEqualTo("5")
  }

  @Test fun onSharedPreferenceChanged_refillStyle_shouldConfigureColorPref() {
    `when`(sharedPrefs.getString(eq(Settings.PREF_MAP_STYLE), isNull())).thenReturn("Refill")
    presenter.onSharedPreferenceChanged(Settings.PREF_MAP_STYLE)
    assertThat(controller.prefIds[2]).isEqualTo(Settings.PREF_COLOR)
    assertThat(controller.entriesResIds[2]).isEqualTo(R.array.pref_refill_theme_color_entries)
    assertThat(controller.entryValuesResIds[2]).isEqualTo(R.array.pref_refill_theme_color_values)
    assertThat(controller.values[2]).isEqualTo("black")
  }

  @Test fun onSharedPreferenceChanged_cinnabarStyle_shouldConfigureLodPref() {
    `when`(sharedPrefs.getString(eq(Settings.PREF_MAP_STYLE), isNull())).thenReturn("Cinnabar")
    presenter.onSharedPreferenceChanged(Settings.PREF_MAP_STYLE)
    assertThat(controller.prefIds[0]).isEqualTo(Settings.PREF_LOD)
    assertThat(controller.entriesResIds[0]).isEqualTo(R.array.pref_empty)
    assertThat(controller.entryValuesResIds[0]).isEqualTo(R.array.pref_empty)
    assertThat(controller.values[0]).isEqualTo("-1")
  }

  @Test fun onSharedPreferenceChanged_cinnabarStyle_shouldConfigureLabelLevelPref() {
    `when`(sharedPrefs.getString(eq(Settings.PREF_MAP_STYLE), isNull())).thenReturn("Cinnabar")
    presenter.onSharedPreferenceChanged(Settings.PREF_MAP_STYLE)
    assertThat(controller.prefIds[1]).isEqualTo(Settings.PREF_LABEL_LEVEL)
    assertThat(controller.entriesResIds[1]).isEqualTo(R.array.pref_lod_label_entries)
    assertThat(controller.entryValuesResIds[1]).isEqualTo(R.array.pref_lod_label_entries)
    assertThat(controller.values[1]).isEqualTo("5")
  }

  @Test fun onSharedPreferenceChanged_cinnabarStyle_shouldConfigureColorPref() {
    `when`(sharedPrefs.getString(eq(Settings.PREF_MAP_STYLE), isNull())).thenReturn("Cinnabar")
    presenter.onSharedPreferenceChanged(Settings.PREF_MAP_STYLE)
    assertThat(controller.prefIds[2]).isEqualTo(Settings.PREF_COLOR)
    assertThat(controller.entriesResIds[2]).isEqualTo(R.array.pref_cinnabar_theme_color_entries)
    assertThat(controller.entryValuesResIds[2]).isEqualTo(R.array.pref_cinnabar_theme_color_values)
    assertThat(controller.values[2]).isEqualTo("cinnabar")
  }

  @Test fun onSharedPreferenceChanged_bubbleWrapStyle_shouldConfigureLodPref() {
    `when`(sharedPrefs.getString(eq(Settings.PREF_MAP_STYLE), isNull())).thenReturn("Bubble Wrap")
    presenter.onSharedPreferenceChanged(Settings.PREF_MAP_STYLE)
    assertThat(controller.prefIds[0]).isEqualTo(Settings.PREF_LOD)
    assertThat(controller.entriesResIds[0]).isEqualTo(R.array.pref_empty)
    assertThat(controller.entryValuesResIds[0]).isEqualTo(R.array.pref_empty)
    assertThat(controller.values[0]).isEqualTo("-1")
  }

  @Test fun onSharedPreferenceChanged_bubbleWrapStyle_shouldConfigureLabelLevelPref() {
    `when`(sharedPrefs.getString(eq(Settings.PREF_MAP_STYLE), isNull())).thenReturn("Bubble Wrap")
    presenter.onSharedPreferenceChanged(Settings.PREF_MAP_STYLE)
    assertThat(controller.prefIds[1]).isEqualTo(Settings.PREF_LABEL_LEVEL)
    assertThat(controller.entriesResIds[1]).isEqualTo(R.array.pref_lod_label_entries)
    assertThat(controller.entryValuesResIds[1]).isEqualTo(R.array.pref_lod_label_entries)
    assertThat(controller.values[1]).isEqualTo("5")
  }

  @Test fun onSharedPreferenceChanged_bubbleWrapStyle_shouldConfigureColorPref() {
    `when`(sharedPrefs.getString(eq(Settings.PREF_MAP_STYLE), isNull())).thenReturn("Bubble Wrap")
    presenter.onSharedPreferenceChanged(Settings.PREF_MAP_STYLE)
    assertThat(controller.prefIds[2]).isEqualTo(Settings.PREF_COLOR)
    assertThat(controller.entriesResIds[2]).isEqualTo(R.array.pref_empty)
    assertThat(controller.entryValuesResIds[2]).isEqualTo(R.array.pref_empty)
    assertThat(controller.values.size).isEqualTo(2)
  }

  @Test fun onSharedPreferenceChanged_walkaboutStyle_shouldConfigureLodPref() {
    `when`(sharedPrefs.getString(eq(Settings.PREF_MAP_STYLE), isNull())).thenReturn("Walkabout")
    presenter.onSharedPreferenceChanged(Settings.PREF_MAP_STYLE)
    assertThat(controller.prefIds[0]).isEqualTo(Settings.PREF_LOD)
    assertThat(controller.entriesResIds[0]).isEqualTo(R.array.pref_empty)
    assertThat(controller.entryValuesResIds[0]).isEqualTo(R.array.pref_empty)
    assertThat(controller.values[0]).isEqualTo("-1")
  }

  @Test fun onSharedPreferenceChanged_walkaboutStyle_shouldConfigureLabelLevelPref() {
    `when`(sharedPrefs.getString(eq(Settings.PREF_MAP_STYLE), isNull())).thenReturn("Walkabout")
    presenter.onSharedPreferenceChanged(Settings.PREF_MAP_STYLE)
    assertThat(controller.prefIds[1]).isEqualTo(Settings.PREF_LABEL_LEVEL)
    assertThat(controller.entriesResIds[1]).isEqualTo(R.array.pref_lod_label_entries)
    assertThat(controller.entryValuesResIds[1]).isEqualTo(R.array.pref_lod_label_entries)
    assertThat(controller.values[1]).isEqualTo("5")
  }

  @Test fun onSharedPreferenceChanged_walkaboutStyle_shouldConfigureColorPref() {
    `when`(sharedPrefs.getString(eq(Settings.PREF_MAP_STYLE), isNull())).thenReturn("Walkabout")
    presenter.onSharedPreferenceChanged(Settings.PREF_MAP_STYLE)
    assertThat(controller.prefIds[2]).isEqualTo(Settings.PREF_COLOR)
    assertThat(controller.entriesResIds[2]).isEqualTo(R.array.pref_empty)
    assertThat(controller.entryValuesResIds[2]).isEqualTo(R.array.pref_empty)
    assertThat(controller.values.size).isEqualTo(2)
  }
}
