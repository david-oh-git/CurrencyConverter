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

package io.audioshinigami.currencyconverter.getapikey

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.preference.PreferenceManager
import io.audioshinigami.currencyconverter.R
import io.audioshinigami.currencyconverter.databinding.ActivityGetKeyBinding
import io.audioshinigami.currencyconverter.home.HomeActivity
import io.audioshinigami.currencyconverter.workers.PopulateDbRunnable

class GetKeyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityGetKeyBinding = DataBindingUtil.setContentView(this , R.layout.activity_get_key)

        PreferenceManager.getDefaultSharedPreferences(this).getBoolean( getString(R.string.preference_first_time), false).apply {
            if (!this)
                initPaperDbFromJson()
        }
    }

//    populates the [Paper] Db when app is launched for the 1st time
    private fun initPaperDbFromJson() = Thread( PopulateDbRunnable(this) ).apply { start() }

    fun navigateHome(){
        startActivity( Intent(this, HomeActivity::class.java))
        finish()
    }
}