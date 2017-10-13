package mapzen.com.sdksampleapp.di

import dagger.Module
import dagger.Provides
import mapzen.com.sdksampleapp.presenters.MainPresenter
import mapzen.com.sdksampleapp.presenters.MainPresenterImpl
import javax.inject.Singleton

/**
 * Dagger module for providing objects to [AppComponent]
 */
@Module
class AppModule {

  @Provides @Singleton fun provideMainPresenter() : MainPresenter {
    return MainPresenterImpl()
  }
}
