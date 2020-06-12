/*
 * MIT License
 *
 * Copyright (c) 2020  David Osemwota
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
