package fergaral.autoshorten

import android.app.Activity
import android.app.Application
import android.app.Service
import androidx.appcompat.app.AppCompatDelegate
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasServiceInjector
import fergaral.autoshorten.di.AppModule
import fergaral.autoshorten.di.DaggerAppComponent
import javax.inject.Inject

/**
 * Created by Fer on 10/02/2018.
 */
class AutoShorten: Application(), HasActivityInjector, HasServiceInjector {
    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var serviceInjector: DispatchingAndroidInjector<Service>

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)

        DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
                .inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    override fun serviceInjector(): AndroidInjector<Service> = serviceInjector
}