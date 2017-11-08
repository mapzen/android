package mapzen.com.sdksampleapp.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mapzen.android.graphics.MapView
import com.mapzen.android.graphics.MapzenMap
import com.mapzen.android.routing.MapzenRouter
import com.mapzen.android.search.MapzenSearch
import kotterknife.bindView
import mapzen.com.sdksampleapp.MainApplication
import mapzen.com.sdksampleapp.R
import mapzen.com.sdksampleapp.activities.BaseActivity
import mapzen.com.sdksampleapp.models.Settings
import javax.inject.Inject

/**
 * Base fragment from which all sample fragments should extend.
 */
open abstract class BaseFragment : Fragment(), SharedPreferences.OnSharedPreferenceChangeListener {

  abstract fun getLayoutId(): Int
  abstract fun onMapSetup()

  @Inject lateinit var settings: Settings
  @Inject lateinit var search: MapzenSearch
  @Inject lateinit var router: MapzenRouter

  var prefsChanged = false
  val mapView: MapView by bindView(R.id.map)
  var map: MapzenMap? = null

  val baseActivity: BaseActivity by lazy {
    activity as BaseActivity
  }

  val mainApplication: MainApplication by lazy {
    baseActivity.mainApplication
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mainApplication.appComponent.inject(this)
    val prefs = PreferenceManager.getDefaultSharedPreferences(this.context.applicationContext)
    prefs.registerOnSharedPreferenceChangeListener(this)
  }

  override fun onResume() {
    super.onResume()
    map?.let { refreshMap() }
  }

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    return inflater?.inflate(getLayoutId(), null)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupMap()
  }

  override fun onDestroy() {
    super.onDestroy()
    val prefs = PreferenceManager.getDefaultSharedPreferences(this.context.applicationContext)
    prefs.unregisterOnSharedPreferenceChangeListener(this)
  }

  private fun setupMap() {
    mapView.getMapAsync(settings.mapStyle, { mapzenMap ->
      map = mapzenMap
      onMapSetup()
    })
  }

  private fun refreshMap() {
    if (!prefsChanged) {
      return
    }
    prefsChanged = false
    map?.setStyleLabelLevelLodThemeColorAsync(settings.mapStyle, settings.labelLevel, settings.lod,
        settings.color, { //TODO refresh finished, ensure no map updates in subclasses
    })
  }

  override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
    prefsChanged = true
  }
}
