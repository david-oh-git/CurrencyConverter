package io.audioshinigami.currencyconverter.selectcurrency.di

import dagger.Module
import dagger.Provides
import io.audioshinigami.currencyconverter.data.source.local.ExchangeDatabase
import io.audioshinigami.currencyconverter.data.source.local.PaperDao

@Module
object PaperModule {

    @Provides
    @JvmStatic
    @SelectPaperFragScope
    fun providePaperDao(db: ExchangeDatabase): PaperDao = db.paperDao()
}