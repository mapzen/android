package mapzen.com.sdksampleapp

import android.app.Application
import com.mapzen.android.core.MapzenManager
import mapzen.com.sdksampleapp.di.AppComponent
import mapzen.com.sdksampleapp.di.DaggerAppComponent
import mapzen.com.sdksampleapp.di.AppModule

/**
 * Application subclass for the sample app.
 */
class MainApplication : Application() {

  val appComponent: AppComponent by lazy {
    DaggerAppComponent
        .builder()
        .appModule(AppModule(this.applicationContext))
        .build()
  }

  override fun onCreate() {
    super.onCreate()
    MapzenManager.instance(this).apiKey = BuildConfig.MAPZEN_API_KEY
  }

}
