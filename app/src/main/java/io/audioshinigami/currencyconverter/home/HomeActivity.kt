package io.audioshinigami.currencyconverter.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.preference.PreferenceManager
import com.google.android.material.snackbar.Snackbar
import io.audioshinigami.currencyconverter.App
import io.audioshinigami.currencyconverter.R
import io.audioshinigami.currencyconverter.data.SharedPreferenceSource
import io.audioshinigami.currencyconverter.databinding.ActivityHomeBinding
import io.audioshinigami.currencyconverter.viewmodels.CurrencyViewModel
import kotlinx.android.synthetic.main.activity_home.*
import timber.log.Timber
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProviders.of(this).get(CurrencyViewModel::class.java) }
    private lateinit var navigationController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityHomeBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_home
        )

        navigationController = findNavController(R.id.nav_host_fragment_container)

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
                AppCompatDelegate.setDefaultNightMode( MODE_NIGHT_FOLLOW_SYSTEM )

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
