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

package io.audioshinigami.currencyconverter

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import io.audioshinigami.currencyconverter.getapikey.GetKeyActivity
import io.audioshinigami.currencyconverter.home.HomeActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initTheme()

        when( checkForApiKey() ){
            true -> launchActivity(HomeActivity::class.java)
            false -> launchActivity(GetKeyActivity::class.java)
        }

    }

    private fun checkForApiKey(): Boolean =
        PreferenceManager.getDefaultSharedPreferences(this).getBoolean( getString(R.string.preference_has_api_key), false)

    private fun <T> launchActivity(activity: Class<T>){
        startActivity( Intent(this, activity))
        finish()
    }

    private fun initTheme(){
        PreferenceManager.getDefaultSharedPreferences(this).getString( getString(
            R.string.preference_theme_key
        ), "0")
            .apply {
                setTheme(this ?: "0")
            }
    }

    private fun setTheme(value: String) {

        when(value){
            "0" -> {
                AppCompatDelegate.setDefaultNightMode( AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM )

            }
            "1" -> {
                AppCompatDelegate.setDefaultNightMode( AppCompatDelegate.MODE_NIGHT_YES )

            }
            "2" -> {
                AppCompatDelegate.setDefaultNightMode( AppCompatDelegate.MODE_NIGHT_NO )
            }

        }
    } //END setTheme
}