package fergaral.autoshorten

import android.app.Activity
import android.app.Application
import android.app.Service
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasServiceInjector
import fergaral.autoshorten.di.AppModule
import fergaral.autoshorten.di.DaggerAppComponent
import fergaral.autoshorten.util.ThemeUtils
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
        instance = this

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)

        DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
                .inject(this)

        readTheme()
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    override fun serviceInjector(): AndroidInjector<Service> = serviceInjector

    private fun readTheme() {
        val themeValue = PreferenceManager.getDefaultSharedPreferences(this)
                .getString(getString(R.string.pref_theme_key), getString(R.string.pref_theme_default)) as String
        ThemeUtils.changeTheme(themeValue)
    }

    companion object {
        lateinit var instance: AutoShorten
            private set
    }
}