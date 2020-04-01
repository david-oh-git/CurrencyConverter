package io.audioshinigami.currencyconverter

import android.app.Application
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

    private fun initDagger(app: App ): AppComponent =
        DaggerAppComponent.builder()
            .appModule( AppModule(app))
            .build()

    companion object{
        lateinit var instance: App
        private set
    }
}