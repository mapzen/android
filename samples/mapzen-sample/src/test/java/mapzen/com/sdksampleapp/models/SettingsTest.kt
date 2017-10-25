package mapzen.com.sdksampleapp.models

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import com.mapzen.android.graphics.model.BubbleWrapStyle
import com.mapzen.android.graphics.model.CinnabarStyle
import com.mapzen.android.graphics.model.RefillStyle
import com.mapzen.android.graphics.model.ThemeColor
import com.mapzen.android.graphics.model.ThemedMapStyle
import com.mapzen.android.graphics.model.WalkaboutStyle
import mapzen.com.sdksampleapp.R
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Matchers.eq
import org.mockito.Matchers.isNull
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class SettingsTest {

  val context = Mockito.mock(Context::class.java)
  val sharedPrefs = Mockito.mock(SharedPreferences::class.java)
  lateinit var settings: Settings

  @Before fun setup() {
    val resources = Mockito.mock(Resources::class.java)
    `when`(context.resources).thenReturn(resources)
    val mapStyles = arrayOf("Bubble Wrap", "Cinnabar", "Refill", "Walkabout")
    `when`(resources.getStringArray(R.array.pref_map_style_entries)).thenReturn(mapStyles)
    val themeColors = arrayOf("black", "blue", "blue-gray", "brown-orange", "gray", "gray-gold",
        "high-contrast", "introverted", "introverted-blue", "pink", "pink-yellow", "purple-green",
        "sepia", "zinc")
    `when`(resources.getStringArray(R.array.pref_refill_theme_color_values)).thenReturn(themeColors)
    val cinnabarThemeColors = arrayOf("cinnabar")
    `when`(resources.getStringArray(R.array.pref_cinnabar_theme_color_values)).thenReturn(cinnabarThemeColors)
    settings = Settings(context, sharedPrefs)
  }

  @Test fun mapStyle_refill_shouldReturnCorrectValue() {
    `when`(sharedPrefs.getString(eq(Settings.PREF_MAP_STYLE), isNull())).thenReturn("Refill")
    assertThat(settings.mapStyle.javaClass).isEqualTo(RefillStyle::class.java)
  }

  @Test fun mapStyle_bubbleWrap_shouldReturnCorrectValue() {
    `when`(sharedPrefs.getString(eq(Settings.PREF_MAP_STYLE), isNull())).thenReturn("Bubble Wrap")
    assertThat(settings.mapStyle.javaClass).isEqualTo(BubbleWrapStyle::class.java)
  }

  @Test fun mapStyle_walkabout_shouldReturnCorrectValue() {
    `when`(sharedPrefs.getString(eq(Settings.PREF_MAP_STYLE), isNull())).thenReturn("Walkabout")
    assertThat(settings.mapStyle.javaClass).isEqualTo(WalkaboutStyle::class.java)
  }

  @Test fun mapStyle_cinnabar_shouldReturnCorrectValue() {
    `when`(sharedPrefs.getString(eq(Settings.PREF_MAP_STYLE), isNull())).thenReturn("Cinnabar")
    assertThat(settings.mapStyle.javaClass).isEqualTo(CinnabarStyle::class.java)
  }

  @Test fun color_black_shouldReturnCorrectValue() {
    `when`(sharedPrefs.getString(eq(Settings.PREF_MAP_STYLE), isNull())).thenReturn("Refill")
    `when`(sharedPrefs.getString(eq(Settings.PREF_COLOR), isNull())).thenReturn("black")
    assertThat(settings.color).isEqualTo(ThemeColor.BLACK)
  }

  @Test fun color_blue_shouldReturnCorrectValue() {
    `when`(sharedPrefs.getString(eq(Settings.PREF_MAP_STYLE), isNull())).thenReturn("Refill")
    `when`(sharedPrefs.getString(eq(Settings.PREF_COLOR), isNull())).thenReturn("blue")
    assertThat(settings.color).isEqualTo(ThemeColor.BLUE)
  }

  @Test fun color_bluegray_shouldReturnCorrectValue() {
    `when`(sharedPrefs.getString(eq(Settings.PREF_MAP_STYLE), isNull())).thenReturn("Refill")
    `when`(sharedPrefs.getString(eq(Settings.PREF_COLOR), isNull())).thenReturn("blue-gray")
    assertThat(settings.color).isEqualTo(ThemeColor.BLUEGRAY)
  }

  @Test fun color_brownorange_shouldReturnCorrectValue() {
    `when`(sharedPrefs.getString(eq(Settings.PREF_MAP_STYLE), isNull())).thenReturn("Refill")
    `when`(sharedPrefs.getString(eq(Settings.PREF_COLOR), isNull())).thenReturn("brown-orange")
    assertThat(settings.color).isEqualTo(ThemeColor.BROWNORANGE)
  }

  @Test fun color_gray_shouldReturnCorrectValue() {
    `when`(sharedPrefs.getString(eq(Settings.PREF_MAP_STYLE), isNull())).thenReturn("Refill")
    `when`(sharedPrefs.getString(eq(Settings.PREF_COLOR), isNull())).thenReturn("gray")
    assertThat(settings.color).isEqualTo(ThemeColor.GRAY)
  }

  @Test fun color_graygold_shouldReturnCorrectValue() {
    `when`(sharedPrefs.getString(eq(Settings.PREF_MAP_STYLE), isNull())).thenReturn("Refill")
    `when`(sharedPrefs.getString(eq(Settings.PREF_COLOR), isNull())).thenReturn("gray-gold")
    assertThat(settings.color).isEqualTo(ThemeColor.GRAYGOLD)
  }

  @Test fun color_highcontrast_shouldReturnCorrectValue() {
    `when`(sharedPrefs.getString(eq(Settings.PREF_MAP_STYLE), isNull())).thenReturn("Refill")
    `when`(sharedPrefs.getString(eq(Settings.PREF_COLOR), isNull())).thenReturn("high-contrast")
    assertThat(settings.color).isEqualTo(ThemeColor.HIGHCONTRAST)
  }

  @Test fun color_introverted_shouldReturnCorrectValue() {
    `when`(sharedPrefs.getString(eq(Settings.PREF_MAP_STYLE), isNull())).thenReturn("Refill")
    `when`(sharedPrefs.getString(eq(Settings.PREF_COLOR), isNull())).thenReturn("introverted")
    assertThat(settings.color).isEqualTo(ThemeColor.INTROVERTED)
  }

  @Test fun color_introvertedblue_shouldReturnCorrectValue() {
    `when`(sharedPrefs.getString(eq(Settings.PREF_MAP_STYLE), isNull())).thenReturn("Refill")
    `when`(sharedPrefs.getString(eq(Settings.PREF_COLOR), isNull())).thenReturn("introverted-blue")
    assertThat(settings.color).isEqualTo(ThemeColor.INTROVERTEDBLUE)
  }

  @Test fun color_pink_shouldReturnCorrectValue() {
    `when`(sharedPrefs.getString(eq(Settings.PREF_MAP_STYLE), isNull())).thenReturn("Refill")
    `when`(sharedPrefs.getString(eq(Settings.PREF_COLOR), isNull())).thenReturn("pink")
    assertThat(settings.color).isEqualTo(ThemeColor.PINK)
  }

  @Test fun color_pinkyellow_shouldReturnCorrectValue() {
    `when`(sharedPrefs.getString(eq(Settings.PREF_MAP_STYLE), isNull())).thenReturn("Refill")
    `when`(sharedPrefs.getString(eq(Settings.PREF_COLOR), isNull())).thenReturn("pink-yellow")
    assertThat(settings.color).isEqualTo(ThemeColor.PINKYELLOW)
  }

  @Test fun color_purplegreen_shouldReturnCorrectValue() {
    `when`(sharedPrefs.getString(eq(Settings.PREF_MAP_STYLE), isNull())).thenReturn("Refill")
    `when`(sharedPrefs.getString(eq(Settings.PREF_COLOR), isNull())).thenReturn("purple-green")
    assertThat(settings.color).isEqualTo(ThemeColor.PURPLEGREEN)
  }

  @Test fun color_sepia_shouldReturnCorrectValue() {
    `when`(sharedPrefs.getString(eq(Settings.PREF_MAP_STYLE), isNull())).thenReturn("Refill")
    `when`(sharedPrefs.getString(eq(Settings.PREF_COLOR), isNull())).thenReturn("sepia")
    assertThat(settings.color).isEqualTo(ThemeColor.SEPIA)
  }

  @Test fun color_zinc_shouldReturnCorrectValue() {
    `when`(sharedPrefs.getString(eq(Settings.PREF_MAP_STYLE), isNull())).thenReturn("Refill")
    `when`(sharedPrefs.getString(eq(Settings.PREF_COLOR), isNull())).thenReturn("zinc")
    assertThat(settings.color).isEqualTo(ThemeColor.ZINC)
  }

  @Test fun color_cinnabar_shouldReturnCorrectValue() {
    `when`(sharedPrefs.getString(eq(Settings.PREF_MAP_STYLE), isNull())).thenReturn("Cinnabar")
    `when`(sharedPrefs.getString(eq(Settings.PREF_COLOR), isNull())).thenReturn("cinnabar")
    assertThat(settings.color).isEqualTo(ThemeColor.CINNABAR)
  }

  @Test fun lod_shouldReturnCorrectValue() {
    `when`(sharedPrefs.getString(eq(Settings.PREF_LOD), eq(ThemedMapStyle.NONE.toString()))).thenReturn("3")
    assertThat(settings.lod).isEqualTo(3)
  }

  @Test fun labelLevel_shouldReturnCorrectValue() {
    `when`(sharedPrefs.getString(eq(Settings.PREF_LABEL_LEVEL), eq(ThemedMapStyle.NONE.toString()))).thenReturn("6")
    assertThat(settings.labelLevel).isEqualTo(6)
  }
}