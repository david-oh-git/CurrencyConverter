/*
 * Copyright 2020 Audio Shinigami Studio.
 *  All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.audioshinigami.currencyconverter.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.preference.PreferenceManager
import com.google.android.material.snackbar.Snackbar
import io.audioshinigami.currencyconverter.R
import io.audioshinigami.currencyconverter.databinding.ActivityHomeBinding
import kotlinx.android.synthetic.main.activity_home.*
import timber.log.Timber

class HomeActivity : AppCompatActivity() {

    private lateinit var navigationController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityHomeBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_home
        )

//        navigationController = findNavController(R.id.nav_host_fragment_container)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navigationController = navHostFragment.navController
        setupViews(binding)
    }

    fun sendSnackBar(message: String){
        Snackbar.make(parent_layout, message, Snackbar.LENGTH_LONG)
            .apply {
                anchorView = bottom_navigation
                show()
            }
        Timber.d("msg : $message")
    }

    private fun setupViews(binding: ActivityHomeBinding) {
        NavigationUI.setupWithNavController(binding.bottomNavigation, navigationController)
        initTheme()
    }

    // Handles Fragments back stack & performs back or Up
    override fun onSupportNavigateUp() = navigationController.navigateUp()

    private fun initTheme(){
        PreferenceManager.getDefaultSharedPreferences(this@HomeActivity).getString( getString(
            R.string.theme_key
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

} /*end END*/
