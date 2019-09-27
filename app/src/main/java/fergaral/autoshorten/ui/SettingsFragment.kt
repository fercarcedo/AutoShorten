package fergaral.autoshorten.ui

import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import fergaral.autoshorten.R
import fergaral.autoshorten.util.ThemeUtils

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.prefs_general, rootKey)
        handleThemeChange()
    }

    private fun handleThemeChange() {
        val themePreference = findPreference<ListPreference>(getString(R.string.pref_theme_key))
        themePreference?.setOnPreferenceChangeListener { preference, newValue ->
            ThemeUtils.changeTheme(newValue as String)
            true
        }
    }
}