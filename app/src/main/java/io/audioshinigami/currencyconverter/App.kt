package io.audioshinigami.currencyconverter

import android.app.Application

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        synchronized(this){
            instance = this
        }
    } /*end onCreate*/

    companion object{
        lateinit var instance: App
        private set
    }
}