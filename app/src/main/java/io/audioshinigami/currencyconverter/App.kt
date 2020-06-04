package io.audioshinigami.currencyconverter

import android.app.Application
import io.audioshinigami.currencyconverter.di.components.AppComponent
import io.audioshinigami.currencyconverter.di.components.DaggerAppComponent
import timber.log.Timber

class App: Application() {

    val appComponent: AppComponent by lazy {
        initDagger()
    }

    override fun onCreate() {
        super.onCreate()

        if( BuildConfig.DEBUG )
            Timber.plant( Timber.DebugTree() )

        synchronized(this){
            instance = this
        }
    } /*end onCreate*/

    private fun initDagger(): AppComponent =
        DaggerAppComponent.factory().create(applicationContext)

    companion object{
        lateinit var instance: App
        private set
    }
}