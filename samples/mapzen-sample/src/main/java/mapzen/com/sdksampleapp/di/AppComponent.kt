package mapzen.com.sdksampleapp.di

import dagger.Component
import mapzen.com.sdksampleapp.activities.MainActivity
import mapzen.com.sdksampleapp.fragments.BaseFragment
import mapzen.com.sdksampleapp.fragments.SettingsFragment
import javax.inject.Singleton

/**
 * Component for sample app
 */
@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
  fun inject(activity: MainActivity)
  fun inject(fragment: SettingsFragment)
  fun inject(fragment: BaseFragment)
}
