package io.audioshinigami.currencyconverter.di.modules

import dagger.Module
import dagger.Provides
import io.audioshinigami.currencyconverter.di.ApplicationScope
import io.audioshinigami.currencyconverter.network.ApiFactory
import io.audioshinigami.currencyconverter.network.ConverterApi
import javax.inject.Singleton

@Module
object RetrofitModule {

    @Provides
    @JvmStatic
    @ApplicationScope
    fun providesRetrofitApi(): ConverterApi = ApiFactory.rateApi
}