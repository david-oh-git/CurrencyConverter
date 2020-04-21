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
import io.audioshinigami.currencyconverter.data.source.remote.RemoteDataSource
import io.audioshinigami.currencyconverter.network.ConverterApi
import io.audioshinigami.currencyconverter.utils.APP_DB_FILENAME
import io.audioshinigami.currencyconverter.utils.DATASOURCE_LOCAL
import io.audioshinigami.currencyconverter.utils.DATASOURCE_REMOTE
import io.audioshinigami.currencyconverter.utils.SETTINGS_PREF_NAME
import javax.inject.Named
import javax.inject.Singleton


@Module
open class DataStorageModule {

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
    @Named(DATASOURCE_LOCAL)
    fun provideLocalDatabaseSource(paperDao: PaperDao, rateDao: RateDao): DatabaseSource =
        LocalDatabaseSource(paperDao,rateDao)

    @Provides
    @Singleton
    @Named(DATASOURCE_REMOTE)
    fun provideRemoteDataSource(api: ConverterApi): DatabaseSource =
        RemoteDataSource(api)

    @Provides
    @Singleton
    fun provideDefaultRepository(@Named(DATASOURCE_LOCAL) databaseSource: DatabaseSource,
                                 @Named(DATASOURCE_REMOTE) remoteDataSource: DatabaseSource
    ): AppRepository =
        DefaultRepository(
            databaseSource, remoteDataSource
        )
}