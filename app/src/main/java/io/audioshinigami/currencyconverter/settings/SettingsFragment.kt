package io.audioshinigami.currencyconverter.settings

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import io.audioshinigami.currencyconverter.BuildConfig
import io.audioshinigami.currencyconverter.R

class SettingsFragment :
    PreferenceFragmentCompat() {

    private val preferenceListener by lazy {

        SharedPreferences.OnSharedPreferenceChangeListener {
                sharedPreferences, key ->

            when(key){
                getString(R.string.theme_key) -> {

                    sharedPreferences?.run {
                        setTheme( getString(key, "0") ?: "0" )
                    }
                }
            }


        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val versionPreference: Preference? = findPreference( getString(R.string.pref_version_key) )
        versionPreference?.summary = BuildConfig.VERSION_NAME
    }

    override fun onStart() {
        super.onStart()

        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(preferenceListener)
    }

    override fun onStop() {
        super.onStop()

        preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(preferenceListener)
    }

    private fun setTheme(value: String) {

        when(value){
            "0" -> {
                AppCompatDelegate.setDefaultNightMode( AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM )

                activity?.recreate()
            }
            "1" -> {
                AppCompatDelegate.setDefaultNightMode( AppCompatDelegate.MODE_NIGHT_YES )
                activity?.recreate()
            }
            "2" -> {
                AppCompatDelegate.setDefaultNightMode( AppCompatDelegate.MODE_NIGHT_NO )
                activity?.recreate()
            }
        }
    }
}
