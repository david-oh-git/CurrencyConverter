package io.audioshinigami.currencyconverter.di.components

import dagger.Component
import io.audioshinigami.currencyconverter.HomeActivity
import io.audioshinigami.currencyconverter.di.modules.AppModule
import io.audioshinigami.currencyconverter.di.modules.DataStorageModule
import io.audioshinigami.currencyconverter.di.modules.RetrofitModule
import io.audioshinigami.currencyconverter.exchange.ExchangeFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataStorageModule::class, RetrofitModule::class])
interface AppComponent {

    fun inject(target: HomeActivity)

    fun inject(target: ExchangeFragment)
}