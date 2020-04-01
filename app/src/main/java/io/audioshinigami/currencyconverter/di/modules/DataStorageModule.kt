package io.audioshinigami.currencyconverter.di.modules

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import dagger.Module
import dagger.Provides
import io.audioshinigami.currencyconverter.data.AppRepository
import io.audioshinigami.currencyconverter.data.DatabaseSource
import io.audioshinigami.currencyconverter.data.DefaultRepository
import io.audioshinigami.currencyconverter.data.SharedPreferenceSource
import io.audioshinigami.currencyconverter.data.source.local.*
import io.audioshinigami.currencyconverter.utils.APP_DB_FILENAME
import io.audioshinigami.currencyconverter.utils.SETTINGS_PREF_NAME
import javax.inject.Named
import javax.inject.Singleton


@Module
class DataStorageModule {

    @Provides
    @Named(SETTINGS_PREF_NAME)
    fun provideSharedPreferenceName(): String = SETTINGS_PREF_NAME

    @Provides
    @Singleton
    fun provideSharedPreference(@Named(SETTINGS_PREF_NAME) name: String , context: Context): SharedPreferences =
        context.getSharedPreferences( name, 0)

    @Provides
    @Singleton
    fun provideSharedPreferenceSource(sharedPreferences: SharedPreferences): SharedPreferenceSource =
        LocalPreferenceSource(sharedPreferences)

    @Provides
    @Singleton
    fun provideExchangeDatabase(context: Context): ExchangeDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            ExchangeDatabase::class.java, APP_DB_FILENAME
        ).build()

    @Provides
    @Singleton
    fun providePaperDao(db: ExchangeDatabase): PaperDao = db.paperDao()

    @Provides
    @Singleton
    fun provideRateDao(db: ExchangeDatabase): RateDao = db.rateDao()

    @Provides
    @Singleton
    fun provideDatabaseSource(paperDao: PaperDao, rateDao: RateDao): DatabaseSource =
        LocalDatabaseSource(paperDao,rateDao)

    @Provides
    @Singleton
    fun provideDefaultRepository( databaseSource: DatabaseSource,
                                  sharedPreferenceSource: SharedPreferenceSource): AppRepository =
        DefaultRepository(
            databaseSource, sharedPreferenceSource
        )
}