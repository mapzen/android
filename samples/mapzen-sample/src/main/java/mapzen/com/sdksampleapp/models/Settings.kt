package mapzen.com.sdksampleapp.models

import android.content.Context
import android.content.SharedPreferences
import com.mapzen.android.graphics.model.BubbleWrapStyle
import com.mapzen.android.graphics.model.CinnabarStyle
import com.mapzen.android.graphics.model.RefillStyle
import com.mapzen.android.graphics.model.ThemeColor
import com.mapzen.android.graphics.model.ThemedMapStyle
import com.mapzen.android.graphics.model.TronStyle
import com.mapzen.android.graphics.model.WalkaboutStyle
import mapzen.com.sdksampleapp.R

/**
 * Represents settings displayed in [SettingsFragment].
 */
class Settings(private val context: Context, private val sharedPrefs: SharedPreferences) {

  private val mapStringToMapStyle: Map<String, ThemedMapStyle> = hashMapOf(
      Pair(getMapStyleValue(0), BubbleWrapStyle()),
      Pair(getMapStyleValue(1), CinnabarStyle()),
      Pair(getMapStyleValue(2), RefillStyle()),
      Pair(getMapStyleValue(3), TronStyle()),
      Pair(getMapStyleValue(4), WalkaboutStyle())
  )

  private val refillThemeStringToThemeColor: Map<String, ThemeColor> = hashMapOf(
      Pair(getRefillThemeColorValue(0), ThemeColor.BLACK),
      Pair(getRefillThemeColorValue(1), ThemeColor.BLUE),
      Pair(getRefillThemeColorValue(2), ThemeColor.BLUEGRAY),
      Pair(getRefillThemeColorValue(3), ThemeColor.BROWNORANGE),
      Pair(getRefillThemeColorValue(4), ThemeColor.GRAY),
      Pair(getRefillThemeColorValue(5), ThemeColor.GRAYGOLD),
      Pair(getRefillThemeColorValue(6), ThemeColor.HIGHCONTRAST),
      Pair(getRefillThemeColorValue(7), ThemeColor.INTROVERTED),
      Pair(getRefillThemeColorValue(8), ThemeColor.INTROVERTEDBLUE),
      Pair(getRefillThemeColorValue(9), ThemeColor.PINK),
      Pair(getRefillThemeColorValue(10), ThemeColor.PINKYELLOW),
      Pair(getRefillThemeColorValue(11), ThemeColor.PURPLEGREEN),
      Pair(getRefillThemeColorValue(12), ThemeColor.SEPIA),
      Pair(getRefillThemeColorValue(13), ThemeColor.ZINC)
  )

  private val cinnabarThemeStringToThemeColor: Map<String, ThemeColor> = hashMapOf(
      Pair(getCinnabarThemeColorValue(0), ThemeColor.CINNABAR)
  )

  val mapStyle: ThemedMapStyle
    get() {
      val default = context.getString(R.string.pref_map_style_default)
      val mapStyleString = sharedPrefs.getString(PREF_MAP_STYLE, default)
      return mapStringToMapStyle[mapStyleString] ?: BubbleWrapStyle()
    }

  val lod: Int
    get() {
      return sharedPrefs.getString(PREF_LOD, ThemedMapStyle.NONE.toString()).toInt()
    }

  val labelLevel: Int
    get() {
      return sharedPrefs.getString(PREF_LABEL_LEVEL, ThemedMapStyle.NONE.toString()).toInt()
    }

  val color: ThemeColor
    get() {
      val default = context.getString(R.string.pref_color_default)
      val themeString = sharedPrefs.getString(PREF_COLOR, default)
      return if (mapStyle is RefillStyle) {
        refillThemeStringToThemeColor[themeString] ?: ThemeColor.NONE
      } else if (mapStyle is CinnabarStyle) {
        cinnabarThemeStringToThemeColor[themeString] ?: ThemeColor.NONE
      } else {
        ThemeColor.NONE
      }
    }

  companion object {
    @JvmStatic val PREF_MAP_STYLE = "pref_map_style"
    @JvmStatic val PREF_LOD = "pref_lod"
    @JvmStatic val PREF_LABEL_LEVEL = "pref_label_level"
    @JvmStatic val PREF_COLOR = "pref_color"
  }

  private fun getMapStyleValue(index: Int): String {
    return context.resources.getStringArray(R.array.pref_map_style_entries)[index]
  }

  private fun getRefillThemeColorValue(index: Int): String {
    return context.resources.getStringArray(R.array.pref_refill_theme_color_values)[index]
  }

  private fun getCinnabarThemeColorValue(index: Int): String {
    return context.resources.getStringArray(R.array.pref_cinnabar_theme_color_values)[index]
  }
}