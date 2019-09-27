package fergaral.autoshorten.util

import androidx.appcompat.app.AppCompatDelegate
import fergaral.autoshorten.AutoShorten
import fergaral.autoshorten.R

object ThemeUtils {
    fun changeTheme(themeValue: String) {
        AppCompatDelegate.setDefaultNightMode(when (themeValue) {
            AutoShorten.instance.getString(R.string.dark_value) -> AppCompatDelegate.MODE_NIGHT_YES
            AutoShorten.instance.getString(R.string.set_by_battery_saver_value) -> AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
            AutoShorten.instance.getString(R.string.system_default_value) -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            else -> AppCompatDelegate.MODE_NIGHT_NO
        })
    }
}