package io.audioshinigami.currencyconverter

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import io.audioshinigami.currencyconverter.di.components.AppComponent
import io.audioshinigami.currencyconverter.di.components.DaggerAppComponent
import io.audioshinigami.currencyconverter.di.modules.AppModule
import timber.log.Timber

class App: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        if( BuildConfig.DEBUG )
            Timber.plant( Timber.DebugTree() )

        appComponent = initDagger(this)

        synchronized(this){
            instance = this
        }
    } /*end onCreate*/

    fun setTheme(value: String) {

        when(value){
            "0" -> {
                Timber.d("set to system default")
                AppCompatDelegate.setDefaultNightMode( AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM )
            }

            "1" -> {
                Timber.d("Setting dark theme")
                AppCompatDelegate.setDefaultNightMode( AppCompatDelegate.MODE_NIGHT_YES)
            }
            "2" -> {
                Timber.d("Setting light theme")
                AppCompatDelegate.setDefaultNightMode( AppCompatDelegate.MODE_NIGHT_NO )
            }
        }
    }

    private fun initDagger(app: App ): AppComponent =
        DaggerAppComponent.builder()
            .appModule( AppModule(app))
            .build()

    companion object{
        lateinit var instance: App
        private set
    }
}