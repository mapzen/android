package mapzen.com.sdksampleapp.di

import android.content.Context
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import mapzen.com.sdksampleapp.models.SampleMap
import mapzen.com.sdksampleapp.models.Settings
import mapzen.com.sdksampleapp.presenters.MainPresenter
import mapzen.com.sdksampleapp.presenters.MainPresenterImpl
import mapzen.com.sdksampleapp.presenters.SettingsPresenter
import mapzen.com.sdksampleapp.presenters.SettingsPresenterImpl
import javax.inject.Singleton

/**
 * Dagger module for providing objects to [AppComponent]
 */
@Module
class AppModule(private val context: Context) {

  @Provides @Singleton fun provideMainPresenter() : MainPresenter {
    return MainPresenterImpl(SampleMap())
  }

  @Provides @Singleton fun provideSettings() : Settings {
    val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
    return Settings(context, sharedPrefs)
  }

  @Provides @Singleton fun provideSettingsPresenter(settings: Settings) : SettingsPresenter {
    return SettingsPresenterImpl(settings)
  }
}
