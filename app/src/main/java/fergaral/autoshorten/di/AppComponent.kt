package fergaral.autoshorten.di

import dagger.Component
import dagger.android.AndroidInjectionModule
import fergaral.autoshorten.AutoShorten
import javax.inject.Singleton

/**
 * Created by Fer on 10/02/2018.
 */
@Singleton
@Component(modules = arrayOf(AndroidInjectionModule::class, BuildersModule::class, ViewModelModule::class, AppModule::class ))
interface AppComponent {
    fun inject(app: AutoShorten)
}