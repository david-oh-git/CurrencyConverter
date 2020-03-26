package io.audioshinigami.currencyconverter

import android.app.Application
import timber.log.Timber

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        if( BuildConfig.DEBUG )
            Timber.plant( Timber.DebugTree() )

        synchronized(this){
            instance = this
        }
    } /*end onCreate*/

    companion object{
        lateinit var instance: App
        private set
    }
}