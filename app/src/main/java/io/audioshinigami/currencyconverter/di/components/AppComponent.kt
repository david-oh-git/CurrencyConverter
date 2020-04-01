package io.audioshinigami.currencyconverter.di.components

import dagger.Component
import io.audioshinigami.currencyconverter.HomeActivity
import io.audioshinigami.currencyconverter.di.modules.AppModule
import io.audioshinigami.currencyconverter.di.modules.DataStorageModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataStorageModule::class])
interface AppComponent {

    fun inject(target: HomeActivity)
}