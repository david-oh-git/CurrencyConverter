package io.audioshinigami.currencyconverter.di.modules

import dagger.Module
import dagger.Provides
import io.audioshinigami.currencyconverter.network.ApiFactory
import io.audioshinigami.currencyconverter.network.ConverterApi
import javax.inject.Singleton

@Module
class RetrofitModule {

    @Provides
    @Singleton
    fun providesRetrofitApi(): ConverterApi = ApiFactory.rateApi
}